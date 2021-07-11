package kludge.router

/**
 * Author: Zhupf
 * Description: 页面路由注解。
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class Route(val route: String)