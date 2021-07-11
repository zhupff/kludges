package kludge.router

import com.google.auto.common.SuperficialValidation
import com.google.auto.service.AutoService
import com.squareup.javapoet.*
import kludge.api.apt.BaseAnnotationProcessor
import kludge.api.common.Logln
import kludge.api.common.i
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * Author: Zhupf
 * Description: [Scheme]注解处理器。
 */
@AutoService(Processor::class)
class SchemeAnnotationProcessor : BaseAnnotationProcessor() {

    override fun getSupportAnnotation(): Set<Class<*>> = setOf(Scheme::class.java)

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        if (annotations.isNullOrEmpty() || roundEnv == null) {
            return false
        }
        roundEnv.getElementsAnnotatedWith(Scheme::class.java)
            .filter { SuperficialValidation.validateElement(it) }
            .forEach { generateSchemeParser(it) }
        return true
    }

    private fun generateSchemeParser(element: Element) {
        val classPath = processingEnv.elementUtils.getPackageOf(element).qualifiedName.toString()
        val className = element.simpleName
        val spec = TypeSpec.classBuilder("${className}_${SchemeParserProvider::class.java.simpleName}")
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addSuperinterface(SchemeParserProvider::class.java)
            .addAnnotation(AnnotationSpec.builder(AutoService::class.java)
                .addMember("value", "${SchemeParserProvider::class.java.name}.class")
                .build())
            .addMethod(MethodSpec.methodBuilder("getSchemeParser")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override::class.java)
                .returns(SchemeParser::class.java)
                .addStatement("return new ${className}()")
                .build())
            .build()
        try {
            JavaFile.builder(classPath, spec).build().writeTo(processingEnv.filer)
        } catch (e: Exception) {
            Logln.w("${i(name)} generate ${spec.name} exception.")
        }
    }
}