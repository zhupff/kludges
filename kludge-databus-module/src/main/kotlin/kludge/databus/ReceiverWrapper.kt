package kludge.databus

/**
 * @Author:Zhupf
 * @Description: 数据接收器包装体。
 */
internal open class ReceiverWrapper<T>(
    /** 数据源。 **/
    protected open val dataWrapper: DataWrapper<T>,
    /** 数据接收器。 **/
    protected open val receiver: Receiver<T>
) {
    /** 当前数据版本。 **/
    open var version: Int = dataWrapper.version
        protected set
    /** 当前该接收器状态。 **/
    open var state: State = State.INIT
        protected set

    /**
     * 检查并更新该接收器状态。
     * @return true/false：当前是否处于[State.ACTIVE]状态。
     */
    internal open fun checkIfActive(): Boolean = state == State.ACTIVE

    /**
     * 将该接收器切换到活跃状态。
     */
    internal open fun switchActive() {
        if (isReleased()) return
        if (state != State.ACTIVE) {
            val tempState = state
            state = State.ACTIVE
            if (tempState == State.INIT || dataWrapper.version > version) {
                dataWrapper.dispatchData(this)
            }
        }
    }

    /**
     * 将该接收器切换到不活跃状态。
     */
    internal open fun switchInactive() {
        if (isReleased()) return
        if (state != State.INACTIVE) {
            state = State.INACTIVE
        }
    }

    /**
     * 接收到数据，通知接收器更新数据。
     * @return true/false：是否消费该数据。
     */
    internal open fun onReceive(data: T?): Boolean {
        if (isReleased()) return false
        if (version >= dataWrapper.version && !receiver.sticky()) {
            // 不接收粘性数据。
            return false
        }
        if (version < dataWrapper.version) {
            version = dataWrapper.version
        }
        return receiver.onReceive(data)
    }

    /**
     * 该接收器是否已经释放。
     * @return true/false：是否已释放。
     */
    internal open fun isReleased(): Boolean = state == State.RELEASE

    /**
     * 释放该接收器。
     */
    internal open fun release() {
        if (isReleased()) return
        state = State.RELEASE
    }

    /**
     * 注销该接收器。
     */
    internal open fun unregister() {
        if (isReleased()) return
        dataWrapper.unregister(receiver)
    }
}