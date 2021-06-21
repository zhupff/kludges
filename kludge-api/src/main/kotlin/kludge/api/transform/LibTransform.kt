package kludge.api.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import kludge.api.common.*
import java.io.File

/**
 * Author: Zhupf
 * Description: 适用于 Android-Library 模块的默认 Transform 处理流程。
 */
open class LibTransform(
    transformers: List<ClassTransformer> = emptyList())
    : BaseTransform(transformers) {

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.PROJECT_ONLY

    override fun handleDirInput(input: DirectoryInput, outputProvider: TransformOutputProvider, context: TransformContext) {
        Logln.v("${i(name)} handleDirInput, input=${i(input.name)}")
        val desDir = outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY)
        val srcDirPath = input.file.absolutePath
        val desDirPath = desDir.absolutePath
        if (context.isIncremental) {
            input.changedFiles.forEach { (file, status) ->
                Logln.v("${i(file.name)}'s status=${i(status)}")
                when (status) {
                    Status.ADDED, Status.CHANGED -> {
                        val desFile = File(file.absolutePath.replace(srcDirPath, desDirPath))
                        if (file.isFile) {
                            if (!desFile.isNotExists()) {
                                desFile.parentFile?.let { desParentFile ->
                                    if (!desParentFile.mkdirs() && !desParentFile.isDirectory) {
                                        throw Exception("Can't create directory(${desParentFile.absolutePath}.")
                                    }
                                } ?: throw Exception("File(${desFile})'s parent file is null.")
                                desFile.createNewFile()
                            }
                            if (file.isClassFile()) {
                                val bytes = handleDirClass(file.absolutePath.removePrefix(srcDirPath).asClassName(), file.readBytes())
                                file.writeBytes(bytes)
                            }
                            FileUtils.copyFile(file, desFile)
                        }
                    }
                    Status.REMOVED -> {
                        file.deleteIfExists()
                    }
                    else -> {} // Status.UNCHANGED or null, do nothing.
                }
            }
        } else { // is not incremental
            input.file.walk()
                .filter {
                    it.isClassFile()
                }
                .forEach {
                    val className = it.absolutePath.removePrefix(srcDirPath).asClassName()
                    val bytes = handleDirClass(className, it.readBytes())
                    it.writeBytes(bytes)
                }
            FileUtils.copyDirectory(input.file, desDir)
        }
    }
}