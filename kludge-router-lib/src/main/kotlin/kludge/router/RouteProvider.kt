package kludge.router

/**
 * Author: Zhupf
 * Description: 页面路由路径接口。
 */
interface RouteProvider {

    /**
     * 获取页面被[Route]注解的路由。
     */
    fun getRoute(): String

    /**
     * 获取路由映射的类路径。
     */
    fun getPath(): String

    /**
     * 获取一个实例对象
     */
    fun newInstance(): Any
}