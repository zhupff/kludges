package kludge.router

/**
 * Author: Zhupf
 * Description: 页面协议路由解析器接口。支持的语法：<scheme>://<route>?k1=v1&k2=v2...
 *     通过<scheme>和<route>来匹配对应的[SchemeParser]。
 */
@Scheme
abstract class SchemeParser {

    /**
     * 获取匹配的协议。
     */
    abstract val scheme: String

    /**
     * 获取匹配的路径。
     */
    abstract val route: String

    /**
     * 解析。
     */
    abstract fun parse()
}
