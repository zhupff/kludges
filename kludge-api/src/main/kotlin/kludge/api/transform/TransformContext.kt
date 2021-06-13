package kludge.api.transform

import com.android.build.api.transform.TransformInvocation
import kludge.api.common.lowerCase
import java.io.File

/**
 * Author: Zhupf
 * Description: transform 流程上下文。
 */
open class TransformContext(transformInvocation: TransformInvocation) {

    val variantName: String = transformInvocation.context.variantName

    val isDebug: Boolean = variantName.lowerCase().contains("debug")

    val isRelease: Boolean = variantName.lowerCase().contains("release")

    val isIncremental: Boolean = transformInvocation.isIncremental

    val tempDir: File = transformInvocation.context.temporaryDir
}