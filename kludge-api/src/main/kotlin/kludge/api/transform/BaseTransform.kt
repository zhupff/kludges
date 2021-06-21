package kludge.api.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import kludge.api.common.Logln
import kludge.api.common.i

/**
 * Author: Zhupf
 * Description: transform 处理流程基类。
 */
abstract class BaseTransform(
    protected val transformers: List<ClassTransformer> = emptyList())
    : Transform() {

    override fun getName(): String = javaClass.simpleName

    override fun isIncremental(): Boolean = true

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
        transformInvocation?.let { invocation ->
            val context = TransformContext(invocation)
            invocation.outputProvider?.let { outputProvider ->
                if (context.isIncremental) {
                    outputProvider.deleteAll()
                }
                onTransformStart(context)
                handleTransformInvocation(transformInvocation, outputProvider, context)
                onTransformFinish(context)
            }
        }
    }

    private var transformStartTime: Long = 0L
    protected open fun onTransformStart(context: TransformContext) {
        transformStartTime = System.currentTimeMillis()
        Logln.min("${i(name)} onTransformStart, isIncremental=${i(context.isIncremental)}")
        transformers.forEach { it.onTransformStart(context) }
    }

    private var transformFinishTime: Long = 0L
    protected open fun onTransformFinish(context: TransformContext) {
        transformers.forEach { it.onTransformFinish(context) }
        transformFinishTime = System.currentTimeMillis()
        Logln.min("${i(name)} onTransformFinish, duration=${i(transformFinishTime - transformStartTime)}")
    }

    protected open fun handleTransformInvocation(transformInvocation: TransformInvocation, outputProvider: TransformOutputProvider, context: TransformContext) {
        transformInvocation.inputs.forEach { input ->
            input.directoryInputs.forEach { handleDirInput(it, outputProvider, context) }
            input.jarInputs.forEach { handleJarInput(it, outputProvider, context) }
        }
    }

    protected open fun handleDirInput(input: DirectoryInput, outputProvider: TransformOutputProvider, context: TransformContext) {
        FileUtils.copyDirectory(input.file, outputProvider
            .getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY))
    }

    protected open fun handleJarInput(input: JarInput, outputProvider: TransformOutputProvider, context: TransformContext) {
        FileUtils.copyFile(input.file, outputProvider
            .getContentLocation(input.name, input.contentTypes, input.scopes, Format.JAR))
    }

    protected open fun handleDirClass(className: String, classBytes: ByteArray): ByteArray {
        var bytes = classBytes
        transformers.forEach { bytes = it.handleDirClass(className, classBytes) }
        return bytes
    }

    protected open fun handleJarClass(className: String, classBytes: ByteArray): ByteArray {
        var bytes = classBytes
        transformers.forEach { bytes = it.handleJarClass(className, classBytes) }
        return bytes
    }
}