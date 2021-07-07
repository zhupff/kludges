package kludge.databus

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.Lifecycle

/**
 * @Author:Zhupf
 * @Description: 数据接口。
 */
abstract class Data<T> {

    /**
     * 同步线程发送数据，如果是UI线程则会立刻将数据发送出去。
     * @param data 新数据。
     */
    abstract fun postSync(@Nullable data: T?)

    /**
     * 异步线程发送数据，会丢失前面尚未刷新的数据，可能会在【后续在UI线程发出的数据】的后面执行。
     * @param data 新数据。
     */
    abstract fun postAsync(@Nullable data: T?)

    /**
     * 注册数据接收器。
     * @param receiver 数据接收器。
     * @return 数据接收器引用。
     */
    @NonNull
    abstract fun register(@NonNull receiver: Receiver<T>): ReceiverRef<T>

    /**
     * 注册带[Lifecycle]监听的数据接收器。
     * @param receiver 带[Lifecycle]监听的数据接收器。
     * @return 数据接收器引用。
     */
    @NonNull
    abstract fun register(@NonNull receiver: LifecycleReceiver<T>): ReceiverRef<T>

    /**
     * 注销数据接收器。
     * @param receiver 已注册的数据接收器。
     */
    abstract fun unregister(@NonNull receiver: Receiver<T>)
}