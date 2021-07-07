package kludge.router

import com.google.auto.service.AutoService
import kludge.api.closure.BaseClosureDelegate

/**
 * Author: Zhupf
 * Description: kludge-router 相关模块gradle接入。
 *
 * Composer.compose {
 *     router {}
 * }
 */
@AutoService(BaseClosureDelegate::class)
class RouterClosureDelegate : BaseClosureDelegate() {

    override val name: String = "router"
}