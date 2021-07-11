package kludge.api.apt

import kludge.api.common.Logln
import kludge.api.common.i
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * Author: Zhupf
 * Description: Annotation Processor 基类。
 */
abstract class BaseAnnotationProcessor : AbstractProcessor() {

    protected val name: String = this.javaClass.simpleName

    /**
     * 声明该[Processor]支持的注解Class。然后在[getSupportedAnnotationTypes]中转为String。
     */
    abstract fun getSupportAnnotation(): Set<Class<*>>

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return getSupportAnnotation().map { it.name }.toMutableSet()
    }

    override fun init(processingEnv: ProcessingEnvironment?) {
        Logln.min("${i(name)} init.")
        super.init(processingEnv)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean =
        !annotations.isNullOrEmpty() && roundEnv != null
}