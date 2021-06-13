package kludge.common

import com.google.auto.service.AutoService
import kludge.api.ComposeContext
import kludge.api.closure.BaseClosureDelegate

/**
 * Author: Zhupf
 * Description: kludge-common 相关模块gradle接入
 *
 * Composer.compose {
 *     common {}
 * }
 */
@AutoService(BaseClosureDelegate::class)
class CommonClosureDelegate : BaseClosureDelegate() {

    override val name: String = "common"

    override fun selfCheck(context: ComposeContext) {
        context.project.dependencies.add("implementation", "${GROUP}:kludge-common-module:${VERSION}")
    }
}