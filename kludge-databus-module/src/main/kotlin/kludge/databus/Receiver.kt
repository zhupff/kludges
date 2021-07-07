package kludge.databus

import androidx.annotation.MainThread
import androidx.annotation.Nullable

/**
 * @Author:Zhupf
 * @Description: 数据接收器接口。
 */
abstract class Receiver<T> : Comparable<Receiver<T>> {

    /**
     * 设置该接收器优先级，返回值越大优先值越高。
     * @return 该接收器的优先级。
     */
    open fun priority(): Int = 0

    /**
     * 设置该接收器是否处理粘性数据。
     * @return true：会处理粘性数据；false：不会处理粘性数据。
     */
    open fun sticky(): Boolean = false

    /**
     * 接收到新数据。
     * @param data 接收到的数据，可空。
     * @return true：数据已消费，不再传给后续接收器；false：数据会继续传给后续接收器。
     */
    @MainThread
    abstract fun onReceive(@Nullable data: T?): Boolean

    /**
     * 优先级大的靠前；先添加的靠前。
     */
    final override fun compareTo(other: Receiver<T>): Int {
        return when {
            this === other -> 0
            this.priority() > other.priority() -> -1
            else -> 1
        }
    }
}