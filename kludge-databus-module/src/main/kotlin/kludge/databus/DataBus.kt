package kludge.databus

import androidx.annotation.NonNull
import java.util.concurrent.ConcurrentHashMap

/**
 * Author: Zhupf
 * Description: 数据总线。
 */
@Suppress("UNCHECKED_CAST")
object DataBus {

    /** 数据。 **/
    private val datas: ConcurrentHashMap<Pair<Class<*>, String>, Data<*>> = ConcurrentHashMap()

    /**
     * 订阅数据。
     * @param cls 数据类型。
     * @return 数据包装体。
     */
    @JvmStatic
    fun <T> subscribe(@NonNull cls: Class<T>): Data<T> = subscribe(cls, "")

    /**
     * 订阅数据。
     * @param cls 数据类型。
     * @param tag 数据标签，用于区分同类型数据。
     * @return 数据包装体。
     */
    @JvmStatic
    fun <T> subscribe(@NonNull cls: Class<T>, tag: String): Data<T> {
        val channel: Pair<Class<*>, String> = Pair(cls, tag)
        synchronized(datas) {
            if (datas[channel] == null) {
                datas[channel] = DataWrapper<T>()
            }
        }
        return datas[channel] as Data<T>
    }
}