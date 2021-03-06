package kludge.router

import com.google.auto.common.SuperficialValidation
import com.google.auto.service.AutoService
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
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
 * Description: [Route] 注解处理器。
 */
@AutoService(Processor::class)
class RouteAnnotationProcessor : BaseAnnotationProcessor() {

    override fun getSupportAnnotation(): Set<Class<*>> = setOf(Route::class.java)

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        if (annotations.isNullOrEmpty() || roundEnv == null) {
            return false
        }
        roundEnv.getElementsAnnotatedWith(Route::class.java)
            .filter { SuperficialValidation.validateElement(it) }
            .forEach { generateRouteRegister(it) }
        return true
    }

    private fun generateRouteRegister(element: Element) {
        val classPath = processingEnv.elementUtils.getPackageOf(element).qualifiedName.toString()
        val className = element.simpleName
        val spec = TypeSpec.classBuilder("${className}_${RouteProvider::class.java.simpleName}")
            .addSuperinterface(RouteProvider::class.java)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addAnnotation(AnnotationSpec.builder(AutoService::class.java)
                .addMember("value", "${RouteProvider::class.java.name}.class")
                .build())
            .addMethod(MethodSpec.methodBuilder("getRoute")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override::class.java)
                .returns(String::class.java)
                .addStatement("return \"${element.getAnnotation(Route::class.java).route}\"")
                .build())
            .addMethod(MethodSpec.methodBuilder("getPath")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override::class.java)
                .returns(String::class.java)
                .addStatement("return ${className}.class.getName()")
                .build())
            .addMethod(MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override::class.java)
                .returns(Any::class.java)
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