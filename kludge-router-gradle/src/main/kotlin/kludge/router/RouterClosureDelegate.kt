package kludge.router

import com.google.auto.service.AutoService
import kludge.api.ComposeContext
import kludge.api.GROUP
import kludge.api.VERSION
import kludge.api.closure.BaseClosureDelegate
import kludge.api.common.lowerCase

/**
 * Author: Zhupf
 * Description: kludge-router 相关模块gradle接入。
 *
 * Composer.compose {
 *     router {
 *         apt = "kapt" // default, or annotationProcessor or apt.
 *     }
 * }
 */
@AutoService(BaseClosureDelegate::class)
class RouterClosureDelegate : BaseClosureDelegate() {

    override val name: String = "router"

    var apt: String? = "kapt"

    override fun selfCheck(context: ComposeContext) {
        apt = when (apt.lowerCase()) {
            "annotationprocessor" -> "annotationProcessor"
            "apt" -> "apt"
            else -> "kapt"
        }
        context.project.dependencies.add(apt!!, "$GROUP:kludge-router-compile:$VERSION")
        context.project.dependencies.add("implementation", "$GROUP:kludge-router-lib:$VERSION")
        context.project.dependencies.add("implementation", "$GROUP:kludge-router-module:$VERSION")
    }
}