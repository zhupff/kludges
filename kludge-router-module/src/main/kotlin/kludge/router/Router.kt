package kludge.router

import android.content.Context
import android.content.Intent
import androidx.annotation.Nullable
import java.util.*

/**
 * Author: Zhupf
 * Description: 页面路由器。
 */
object Router {

    init {
        RouteTable.init()
    }

    /**
     * 可以选择主动调用该方法在想要的时间点初始化，也可以在后续使用到[Router]的时候再自动初始化，
     * 不过自动初始化会因为使用[ServiceLoader]进行反射而导致有一定的性能损耗。
     */
    @JvmStatic
    fun init() {}

    @JvmStatic
    fun from(context: Context): RouteTask = RouteTask(context)

    @JvmStatic
    fun from(context: Context, intent: Intent): RouteTask = RouteTask(context, intent)

    /**
     * 获取路由目标的实例，后续请自行转型。
     */
    @Nullable
    @JvmStatic
    fun newInstance(route: String): Any? = RouteTable.getRouteProvider(route)?.newInstance()
}