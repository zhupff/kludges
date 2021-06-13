package kludge.api.transform.asm

import kludge.api.common.Logln
import kludge.api.common.i
import kludge.api.transform.ClassTransformer
import kludge.api.transform.TransformContext

/**
 * Author: Zhupf
 * Description:  使用 ASM 框架的 .class 文件处理器。
 */
class AsmClassTransformer : ClassTransformer {
    override fun onTransformStart(context: TransformContext) {
        Logln.min("${i(javaClass.simpleName)} onTransformStart.")
    }

    override fun onTransformFinish(context: TransformContext) {
        Logln.min("${i(javaClass.simpleName)} onTransformFinish.")
    }
}