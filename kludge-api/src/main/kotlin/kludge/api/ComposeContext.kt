package kludge.api

import kludge.api.transform.BaseTransform
import kludge.api.transform.ClassTransformer
import org.gradle.api.Project

/**
 * Author: Zhupf
 * Description: kludge gradle 组装上下文。
 */
class ComposeContext(val project: Project) {

    /** 目标模块名称。 **/
    val projectName: String = project.name

    /** 是否是 Application模块。 **/
    val isApplicationProject: Boolean = project.plugins.hasPlugin("com.android.application")

    /** 是否是 Android-Library模块。 **/
    val isAndroidLibraryProject: Boolean = project.plugins.hasPlugin("com.android.library")

    /** 添加到默认 Transform 里的 Transformer。 **/
    val transformers: ArrayList<ClassTransformer> = ArrayList()

    /** 自定义的 Transform。 **/
    val transforms: ArrayList<BaseTransform> = ArrayList()
}