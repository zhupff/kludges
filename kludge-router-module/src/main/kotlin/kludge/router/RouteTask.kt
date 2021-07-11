package kludge.router

import android.content.Context
import android.content.Intent
import java.io.Serializable

/**
 * Author: Zhupf
 * Description: 路由跳转任务。
 */
class RouteTask(private val context: Context, private val intent: Intent) : Serializable {

    constructor(context: Context): this(context, Intent())

    fun to(route: String) = apply {
        intent.setClassName(context, RouteTable.getRouteProvider(route)?.getPath()!!)
    }

    fun to(cls: Class<*>) = apply {
        intent.setClass(context, cls)
    }
}