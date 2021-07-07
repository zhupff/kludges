package kludge.databus

import java.lang.ref.WeakReference

/**
 * @Author:Zhupf
 * @Description: 数据接收器包装体的引用，提供对其基本的控制。
 */
class ReceiverRef<T> internal constructor(receiverWrapper: ReceiverWrapper<T>?) {

    /**
     * 数据接收器的弱引用。
     */
    private val reference: WeakReference<ReceiverWrapper<T>?> = WeakReference(receiverWrapper)

    /**
     * 当前是否处于活跃状态。
     * @return true/false：是否处于活跃状态。
     */
    fun isActive(): Boolean = reference.get()?.state == State.ACTIVE

    /**
     * 将接收器状态切换为活跃状态。
     */
    fun switchActive() {
        reference.get()?.switchActive()
    }

    /**
     * 将接收器状态切换为不活跃状态。
     */
    fun switchInactive() {
        reference.get()?.switchInactive()
    }

    /**
     * 注销接收器。
     */
    fun unregister() {
        reference.get()?.unregister()
    }
}