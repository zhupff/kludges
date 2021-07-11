package kludge.router

/**
 * Author: Zhupf
 * Description: 页面跳转拦截器接口。
 */
abstract class RouteInterceptor : Comparable<RouteInterceptor> {

    /** 拦截器优先级，值越大优先级越高。 **/
    abstract val priority: Int

    /**
     * 进行拦截处理。
     * @return 是否继续流程。true=继续流程；false=中止流程。
     */
    abstract fun intercept(task: RouteTask): Boolean

    /**
     * 优先级大的靠前；先添加的靠前。
     */
    final override fun compareTo(other: RouteInterceptor): Int = when {
        this === other -> 0
        this.priority > other.priority -> -1
        else -> 1
    }
}