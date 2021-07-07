package kludge.databus

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.Lifecycle
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * @Author:Zhupf
 * @Description: 数据包装体。
 */
@Suppress("UNCHECKED_CAST")
internal class DataWrapper<T> : Data<T>() {

    companion object {
        /** 初始数据版本。 **/
        private const val INIT_VERSION: Int = 0
        /** 初始空白数据。 **/
        private val NONE: Any = Any()
    }

    /** 最新数据版本。 **/
    private val latestVersion: AtomicInteger = AtomicInteger(INIT_VERSION)
    /** 当前数据版本。 **/
    internal var version: Int = latestVersion.get()
        private set
    /** 持有的数据。 **/
    private var holdingData: Any? = NONE
    /** 待处理的数据。 **/
    private var pendingData: Any? = NONE
    /** 数据接收器及其包装体。 **/
    private val receivers: TreeMap<Receiver<T>, ReceiverWrapper<T>> = TreeMap()
    /** UI线程句柄。 **/
    private val handler: Handler = Handler(Looper.getMainLooper())
    /** UI线程刷新数据任务。 **/
    private val handleDataRunnable: Runnable = Runnable {
        val data: Any?
        synchronized(receivers) {
            data = pendingData
            pendingData = NONE
        }
        handleData(data as T?)
    }

    override fun postSync(data: T?) {
        if (isMainThread()) {
            handleData(data)
        } else {
            postAsync(data)
        }
    }

    override fun postAsync(data: T?) {
        val posting: Boolean
        synchronized(receivers) {
            posting = pendingData !== NONE
            pendingData = data
        }
        if (!posting) {
            handler.post(handleDataRunnable)
        }
    }

    @MainThread
    private fun handleData(data: T?) {
        assetMainThread()
        synchronized(receivers) {
            holdingData = data
            version = latestVersion.incrementAndGet()
        }
        dispatchData(null)
    }

    /**
     * 分发数据给（所有）接收器。会在UI线程执行分发逻辑。
     * @param receiverWrapper 数据接收器包装体，如果为空，则会将数据分发给所有接收器；否则仅分发给该接收器。
     */
    internal fun dispatchData(@Nullable receiverWrapper: ReceiverWrapper<T>?) {
        if (holdingData === NONE) {
            return
        }
        if (!isMainThread()) {
            // 如果不是在UI线程，则抛出到UI线程再执行。
            handler.post { dispatchData(receiverWrapper) }
        } else {
            if (receiverWrapper != null) {
                dispatchToReceiver(receiverWrapper)
            } else {
                receivers.forEach {
                    if (dispatchToReceiver(it.value)) {
                        holdingData = NONE
                        return@forEach
                    }
                }
            }
        }
    }

    /**
     * 分发数据给接收器。
     * @param receiverWrapper 数据接收器包装体。
     * @return true/false：数据接收器是否已消费了该数据
     */
    @MainThread
    private fun dispatchToReceiver(receiverWrapper: ReceiverWrapper<T>): Boolean {
        if (receiverWrapper.checkIfActive()) {
            return receiverWrapper.onReceive(holdingData as T?)
        }
        return false
    }

    override fun register(receiver: Receiver<T>): ReceiverRef<T> {
        val receiverWrapper: ReceiverWrapper<T>
        synchronized(receivers) {
            if (receivers[receiver]?.isReleased() == false) {
                throw IllegalArgumentException("Receiver(${receiver}) has already existed.")
            }
            receiverWrapper = ReceiverWrapper(this, receiver)
            receivers[receiver] = receiverWrapper
        }
        receiverWrapper.switchActive()
        return ReceiverRef(receiverWrapper)
    }

    override fun register(receiver: LifecycleReceiver<T>): ReceiverRef<T> {
        if (receiver.owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            throw IllegalStateException("Can't register cause LifecycleOwner(${receiver.owner}) is destroyed.")
        }
        val receiverWrapper: LifecycleReceiverWrapper<T>
        synchronized(receivers) {
            if (receivers[receiver]?.isReleased() == false) {
                throw IllegalArgumentException("Receiver(${receiver}) has already existed.")
            }
            receiverWrapper = LifecycleReceiverWrapper(this, receiver)
            receivers[receiver] = receiverWrapper
        }
        receiverWrapper.switchActive()
        return ReceiverRef(receiverWrapper)
    }

    override fun unregister(receiver: Receiver<T>) {
        receivers.remove(receiver)?.release()
    }
}