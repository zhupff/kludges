package kludge.databus

import android.os.Looper

/**
 * @Author:Zhupf
 */

/**
 * 检查当前是否处于UI线程，否则抛出错误。
 */
internal fun assetMainThread() {
    if (!isMainThread()) {
        Thread.currentThread().stackTrace[3].let {
            throw IllegalThreadStateException(
                "${it.className}-${it.methodName}(${it.lineNumber}) is invoked on non-main thread.")
        }
    }
}

/**
 * 判断当前是否处于UI线程。
 * @return true/false：当前是否处在UI线程。
 */
internal fun isMainThread(): Boolean {
    return Looper.getMainLooper().thread == Thread.currentThread()
}