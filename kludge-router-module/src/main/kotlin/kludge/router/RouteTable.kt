package kludge.router

import androidx.annotation.Nullable
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.HashMap

/**
 * Author: Zhupf
 * Description: 路由表。
 */
internal object RouteTable {

    private val hasInit: AtomicBoolean = AtomicBoolean(false)

    private val table: HashMap<String, RouteProvider> = HashMap(1)

    fun init() {
        if (!hasInit.getAndSet(true)) {
            ServiceLoader.load(RouteProvider::class.java).forEach {
                table[it.getRoute()] = it
            }
        }
    }

    @Nullable
    fun getRouteProvider(route: String): RouteProvider? = table[route]
}