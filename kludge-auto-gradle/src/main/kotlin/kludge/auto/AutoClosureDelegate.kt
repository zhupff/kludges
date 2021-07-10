package kludge.auto

import com.google.auto.service.AutoService
import kludge.api.ComposeContext
import kludge.api.GROUP
import kludge.api.VERSION
import kludge.api.closure.BaseClosureDelegate
import kludge.api.common.lowerCase

/**
 * Author: Zhupf
 * Description: kludge-auto 相关模块gradle接入。
 *
 * Composer.compose {
 *     auto {
 *         apt = "kapt" // default, or annotationProcessor or apt.
 *     }
 * }
 */
@AutoService(BaseClosureDelegate::class)
class AutoClosureDelegate : BaseClosureDelegate() {

    override val name: String = "auto"

    var apt: String? = "kapt"

    override fun selfCheck(context: ComposeContext) {
        apt = when (apt.lowerCase()) {
            "annotationprocessor" -> "annotationProcessor"
            "apt" -> "apt"
            else -> "kapt"
        }
        context.project.dependencies.add(apt!!, "$GROUP:kludge-auto-compile:$VERSION")
        context.project.dependencies.add("implementation", "$GROUP:kludge-auto-lib:$VERSION")
    }
}