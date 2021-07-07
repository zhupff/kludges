package kludge.api.apt

import kludge.api.common.Logln
import kludge.api.common.i
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

/**
 * Author: Zhupf
 * Description: Annotation Processor 基类。
 */
abstract class BaseAnnotationProcessor : AbstractProcessor() {

    protected val name: String = this.javaClass.simpleName

    override fun init(processingEnv: ProcessingEnvironment?) {
        Logln.min("${i(name)} init.")
        super.init(processingEnv)
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean =
        !annotations.isNullOrEmpty() && roundEnv != null
}