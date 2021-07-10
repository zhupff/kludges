package kludge.auto

import com.google.auto.service.AutoService
import kludge.api.apt.BaseAnnotationProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * Author: Zhupf
 * Description: [Auto] 注解处理器。
 */
@AutoService(Processor::class)
class AutoAnnotationProcessor : BaseAnnotationProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(Auto::class.java.name)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        if (annotations.isNullOrEmpty() || roundEnv == null) {
            return false
        }
        return true
    }
}