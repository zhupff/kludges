package kludge.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import java.io.Serializable
import java.util.*

/**
 * Author: Zhupf
 * Description: 路由跳转任务。
 */
class RouteTask(private val context: Context, private val intent: Intent) : Serializable {

    constructor(context: Context): this(context, Intent())

    /** 将调用[Activity.startActivityForResult]跳转。 **/
    private var requestCode: Int = Int.MIN_VALUE
    /** 跳转后是否调用[Activity.finish]。 **/
    private var finish: Boolean = false
    /** 路由拦截器。 **/
    private var interceptors: TreeSet<RouteInterceptor>? = null

    /**
     * 跳转目的地。
     */
    fun to(route: String) = apply { intent.setClassName(context, RouteTable.getRouteProvider(route)?.getPath()!!) }

    /**
     * 跳转目的地。
     */
    fun to(cls: Class<*>) = apply { intent.setClass(context, cls) }

    /**
     * 使用[Activity.startActivityForResult]。
     */
    fun withRequest(code: Int) = apply {
        if (context !is Activity)
            throw IllegalArgumentException("Can't invoke this method cause Context(${context}) isn't Activity.")
        requestCode = code
    }

    /**
     * 不使用[Activity.startActivityForResult]。
     */
    fun withoutRequest() = apply {
        if (context !is Activity)
            throw IllegalArgumentException("Can't invoke this method cause Context(${context}) isn't Activity.")
        requestCode = Int.MIN_VALUE
    }

    /**
     * 跳转后使用[Activity.finish]。
     */
    fun withFinish() = apply {
        if (context !is Activity)
            throw IllegalArgumentException("Can't invoke this method cause Context(${context}) isn't Activity.")
        finish = true
    }

    /**
     * 跳转后不使用[Activity.finish]。
     */
    fun withoutFinish() = apply {
        if (context !is Activity)
            throw IllegalArgumentException("Can't invoke this method cause Context(${context}) isn't Activity.")
        finish = false
    }

    /**
     * 添加拦截器。
     */
    fun withInterceptor(interceptor: RouteInterceptor) = apply {
        if (interceptors == null) {
            interceptors = TreeSet()
        }
        interceptors?.add(interceptor)
    }

    /**
     * 移除拦截器。
     */
    fun withoutInterceptor(interceptor: RouteInterceptor) = apply {
        interceptors?.remove(interceptor)
    }

    /**
     * 清空拦截器。
     */
    fun clearInterceptors() = apply {
        interceptors?.clear()
        interceptors = null
    }

    /**
     * 开始跳转。
     */
    fun jump() {
        while (!interceptors.isNullOrEmpty()) {
            val interceptor = interceptors?.first() ?: break
            interceptors?.remove(interceptor)
            if (!interceptor.intercept(this)) {
                Log.d("RouteTask", "jump() is intercepted by ${interceptor}.")
                return
            }
        }

        if (context is Activity && requestCode != Int.MIN_VALUE) {
            context.startActivityForResult(intent, requestCode)
        } else {
            context.startActivity(intent)
        }
        if (context is Activity && finish) {
            context.finish()
        }
    }



    fun withExtra(k: String, v: Byte) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Short) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Int) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Long) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Float) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Double) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Char) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Boolean) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: String?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: CharSequence?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Parcelable?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Serializable?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: ByteArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: ShortArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: IntArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: LongArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: FloatArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: DoubleArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: CharArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: BooleanArray?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Array<String>?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Array<CharSequence>?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Array<Parcelable>?) = apply { intent.putExtra(k, v) }
    fun withExtra(k: String, v: Bundle?) = apply { intent.putExtra(k, v) }
    fun withExtra(v: Bundle) = apply { intent.putExtras(v) }
    fun withExtra(v: Intent) = apply { intent.putExtras(v) }
    fun withFlags(flags: Int) = apply { intent.flags = flags }
    fun addFlags(flags: Int) = apply { intent.addFlags(flags) }
    fun clearFlags() = apply { intent.flags = 0 }
}