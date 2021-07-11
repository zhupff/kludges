package kludge.router

/**
 * Author: Zhupf
 * Description: 页面协议路由解析器接口。支持的语法：<scheme>://<route>?k1=v1&k2=v2...
 *     通过<scheme>和<route>来匹配对应的[SchemeParser]。
 */
@Scheme
abstract class SchemeParser {
    companion object {
        /**
         * 解析link的scheme部分。
         */
        fun parseScheme(link: String): String {
            return if (link.contains("//"))
                link.substringBefore("://")
            else ""
        }

        /**
         * 解析link的route部分。
         */
        fun parseRoute(link: String): String {
            var route = link
            if (route.contains("://"))
                route = route.substringAfter("//")
            if (route.contains("?"))
                route = route.substringBefore("?")
            return route
        }
        /**
         * 解析link的k=v部分。
         */
        fun parseParameter(link: String): Map<String, String> {
            val map = HashMap<String, String>(0)
            var parameters = link
            if (parameters.contains("?"))
                parameters = link.substringAfter("?")
            parameters.split("&").map{
                val kv = it.split("=")
                kv[0] to kv[1]
            }.forEach {
                map[it.first] = it.second
            }
            return map
        }
    }

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
    abstract fun parse(link: String)
}
