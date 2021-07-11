package kludge.router

/**
 * Author: Zhupf
 * Description: 提供[SchemeParser]。
 */
interface SchemeParserProvider {

    /**
     * 获取[SchemeParser]实例。
     */
    fun getSchemeParser(): SchemeParser
}