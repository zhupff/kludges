package kludge.router

import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.HashMap

/**
 * Author: Zhupf
 * Description: 路由表。
 */
internal object RouteTable {

    private val hasInit: AtomicBoolean = AtomicBoolean(false)

    private val table: HashMap<String, String> = HashMap(1)

    fun init() {
        if (!hasInit.getAndSet(true)) {
            ServiceLoader.load(RoutePath::class.java).forEach {
                table[it.getRoute()] = it.getPath()
            }
        }
    }

    fun getPathWithRoute(route: String): String = table[route] ?: ""
}