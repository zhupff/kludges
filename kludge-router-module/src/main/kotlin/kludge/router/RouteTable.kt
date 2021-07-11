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

    private val routeTable: HashMap<String, RouteProvider> = HashMap(1)

    private val schemeTable: HashMap<Pair<String, String>, SchemeParserProvider> = HashMap(1)

    fun init() {
        if (!hasInit.getAndSet(true)) {
            ServiceLoader.load(RouteProvider::class.java).forEach {
                routeTable[it.getRoute()] = it
            }
            ServiceLoader.load(SchemeParserProvider::class.java).forEach {
                schemeTable[it.getSchemeParser().scheme to it.getSchemeParser().route] = it
            }
        }
    }

    @Nullable
    fun getRouteProvider(route: String): RouteProvider? = routeTable[route]

    @Nullable
    fun getSchemeParserProvider(scheme: String, route: String): SchemeParserProvider? = schemeTable[scheme to route]
}