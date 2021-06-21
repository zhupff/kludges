package kludge.api.transform

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput

/**
 * Author: Zhupf
 * Description: transform 流程 .class 文件处理器。
 */
interface ClassTransformer {
    /**
     * 在 transform 开始时回调。
     */
    fun onTransformStart(context: TransformContext)

    /**
     * 在 transform 完成时回调。
     */
    fun onTransformFinish(context: TransformContext)

    /**
     * 根据类名筛选需要处理的 .class 文件，默认[filterDirClass]和[filterJarClass]都使用此方法。
     */
    fun filterClass(className: String): Boolean = false

    /**
     * 根据类名筛选需要过滤的[DirectoryInput]里的 .class 文件。
     */
    fun filterDirClass(className: String): Boolean = filterClass(className)

    /**
     * 根据类名筛选需要过滤的[JarInput]里的 .class 文件。
     */
    fun filterJarClass(className: String): Boolean = filterClass(className)

    /**
     * 处理 .class 文件，默认[transformDirClass]和[transformJarClass]都使用此方法。
     */
    fun transformClass(classBytes: ByteArray): ByteArray = classBytes

    /**
     * 处理来自[DirectoryInput]里的 .class 文件。
     */
    fun transformDirClass(classBytes: ByteArray): ByteArray = transformClass(classBytes)

    /**
     * 处理来自[JarInput]里的 .class 文件。
     */
    fun transformJarClass(classBytes: ByteArray): ByteArray = transformClass(classBytes)

    /**
     * 处理来自[DirectoryInput]里的 .class 文件，满足[filterDirClass]的会进行[transformDirClass]。
     */
    fun handleDirClass(className: String, classBytes: ByteArray): ByteArray =
        if (filterDirClass(className))
            transformDirClass(classBytes)
        else
            classBytes

    /**
     * 处理来自[DirectoryInput]里的.class文件，满足[filterJarClass]的会进行[transformJarClass]。
     */
    fun handleJarClass(className: String, classBytes: ByteArray): ByteArray =
        if (filterJarClass(className))
            transformJarClass(classBytes)
        else
            classBytes
}