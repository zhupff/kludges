package kludge.router

import com.google.auto.service.AutoService
import kludge.api.apt.BaseAnnotationProcessor
import javax.annotation.processing.Processor
import javax.lang.model.SourceVersion

/**
 * Author: Zhupf
 * Description: [RoutePath] 注解处理器。
 */
@AutoService(Processor::class)
class RouterPathAnnotationProcessor : BaseAnnotationProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(RoutePath::class.java.canonicalName)

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()
}