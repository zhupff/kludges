package kludge.api.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import kludge.api.common.Logln
import kludge.api.common.deleteIfExists
import kludge.api.common.i
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Author: Zhupf
 * Description: 适用于 Application 模块的默认 Transform 处理流程。
 */
open class AppTransform(
    transformers: List<ClassTransformer> = emptyList())
    : LibTransform(transformers) {

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    override fun handleJarInput(input: JarInput, outputProvider: TransformOutputProvider, context: TransformContext) {
        if (context.isIncremental) {
            Logln.v("${i(name)} handleJarInput, input=${i(input.name)}, status=${i(input.status)}")
            when (input.status) {
                Status.REMOVED -> {
                    input.file.deleteIfExists()
                }
                else -> {} // Status.ADDED, Status.CHANGED, Status.UNCHANGED or null, do nothing.
            }
        } else {
            Logln.v("${i(name)} handleJarInput, input=${i(input.name)}")
        }
        val tempFile = File("${context.tempDir.absolutePath}${File.separator}${input.name}_temp.jar")
        tempFile.deleteIfExists()
        JarFile(input.file).use { jarFile ->
            FileOutputStream(tempFile).use { fos ->
                JarOutputStream(fos).use { jos ->
                    jarFile.entries().toList().forEach { jarEntry ->
                        jarFile.getInputStream(jarEntry).use { ins ->
                            var bytes = ins.readBytes()
                            if (jarEntry.name.endsWith(".class")) {
                                bytes = handleJarClass(jarEntry.name, bytes)
                            }
                            jos.putNextEntry(ZipEntry(jarEntry.name))
                            jos.write(bytes)
                            jos.closeEntry()
                        }
                    }
                }
            }
        }
        FileUtils.copyFile(tempFile, outputProvider
            .getContentLocation(input.name, input.contentTypes, input.scopes, Format.JAR))
        tempFile.deleteIfExists()
    }
}