package kludge.databus

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @Author:Zhupf
 * @Description: 监听[Lifecycle]的数据接收器包装体。
 */
internal open class LifecycleReceiverWrapper<T>(
    dataWrapper: DataWrapper<T>,
    override val receiver: LifecycleReceiver<T>
): ReceiverWrapper<T>(dataWrapper, receiver), LifecycleEventObserver {

    override fun checkIfActive(): Boolean {
        if (isReleased()) return false
        state = when {
            receiver.keepActive() -> State.ACTIVE
            receiver.owner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) -> State.ACTIVE
            else -> State.INACTIVE
        }
        return state == State.ACTIVE
    }

    override fun switchActive() {
        if (isReleased()) return
        if (state == State.INIT) {
            receiver.owner.lifecycle.addObserver(this)
        } else {
            throw IllegalStateException("Can't switch state cause it's LifecycleReceiver.")
        }
    }

    override fun switchInactive() {
        if (isReleased()) return
        throw IllegalStateException("Can't switch state cause it's LifecycleReceiver.")
    }

    override fun release() {
        super.release()
        receiver.owner.lifecycle.removeObserver(this)
    }

    override fun onStateChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        if (receiver.owner !== owner) {
            throw IllegalStateException(
                "Receiver's owner(${receiver.owner}) and parameter's owner(${owner}) are not the same.")
        }
        if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            unregister()
        } else {
            val tempState = state
            if (checkIfActive()) {
                if (tempState == State.INIT || (tempState != State.ACTIVE && dataWrapper.version > version)) {
                    // 从非活跃状态切换到活跃状态，且数据源的最新数据还未处理过，则向数据源请求最新的数据。
                    dataWrapper.dispatchData(this)
                }
            }
        }
    }
}