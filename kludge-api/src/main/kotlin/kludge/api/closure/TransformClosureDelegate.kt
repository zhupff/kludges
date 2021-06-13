package kludge.api.closure

import com.google.auto.service.AutoService
import kludge.api.ComposeContext
import kludge.api.transform.BaseTransform
import kludge.api.transform.ClassTransformer

/**
 * Author: Zhupf
 *
 * Composer.compose {
 *     transform {
 *         transformers = [
 *             new ClassTransformerA(), new ClassTransformerB(),
 *             ...
 *         ]
 *         transforms = [
 *             new TransformA([new ClassTransformerC(), new ClassTransformerD()]),
 *             new TransformB(),
 *             ...
 *         ]
 *     }
 * }
 */
@AutoService(BaseClosureDelegate::class)
class TransformClosureDelegate : BaseClosureDelegate() {

    override val name: String = "transform"

    var transformers: ArrayList<ClassTransformer>? = null

    var transforms: ArrayList<BaseTransform>? = null

    override fun selfCheck(context: ComposeContext) {
        if (!transformers.isNullOrEmpty()) {
            context.transformers.addAll(transformers!!)
        }
        if (!transforms.isNullOrEmpty()) {
            context.transforms.addAll(transforms!!)
        }
    }
}