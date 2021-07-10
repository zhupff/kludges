package kludge.databus

import com.google.auto.service.AutoService
import kludge.api.ComposeContext
import kludge.api.GROUP
import kludge.api.VERSION
import kludge.api.closure.BaseClosureDelegate

/**
 * Author: Zhupf
 * Description: kludge-databus 相关模块gradle接入。
 *
 * Composer.compose {
 *     databus {}
 * }
 */
@AutoService(BaseClosureDelegate::class)
class DataBusClosureDelegate : BaseClosureDelegate() {

    override val name: String = "databus"

    override fun selfCheck(context: ComposeContext) {
        context.project.dependencies.add("implementation", "$GROUP:kludge-databus-module:$VERSION")
    }
}