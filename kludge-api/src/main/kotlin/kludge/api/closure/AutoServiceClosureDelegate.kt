package kludge.api.closure

import com.google.auto.service.AutoService
import kludge.api.AUTO_SERVICE
import kludge.api.AUTO_SERVICE_ANNOTATION
import kludge.api.ComposeContext

/**
 * Author: Zhupf
 * Description: AutoService 依赖注入，默认只添加 auto-service-annotations 模块以满足 kludge 相关功能的使用，
 *     若想使用 AutoServiceProcessor，可以用 apt 字段添加。
 *
 * Composer.compose {
 *     autoservice {
 *         apt = "kapt" // default, or annotationProcessor or apt.
 *     }
 * }
 */
@AutoService(BaseClosureDelegate::class)
class AutoServiceClosureDelegate : BaseClosureDelegate() {

    override val name: String = "autoservice"

    var apt: String? = null

    override fun selfCheck(context: ComposeContext) {
        context.project.dependencies.add("implementation", AUTO_SERVICE_ANNOTATION)
        apt?.let { context.project.dependencies.add(it, AUTO_SERVICE) }
    }
}