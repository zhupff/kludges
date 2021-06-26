package kludge.databus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Lifecycle

/**
 * @Author:Zhupf
 * @Description: 监听[Lifecycle]的数据接收器。
 */
abstract class LifecycleReceiver<T>(val owner: LifecycleOwner) : Receiver<T>() {

    /**
     * 是否在[Lifecycle]期间保持活跃状态。
     * @return true/false：是否保持活跃状态。
     */
    open fun keepActive(): Boolean = false
}