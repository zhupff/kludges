package kludge.api.closure

import groovy.lang.Closure
import kludge.api.ComposeContext

/**
 * Author: Zhupf
 * Description: 闭包代理基类。
 */
abstract class BaseClosureDelegate {

    /** 代理的闭包名。 **/
    abstract val name: String

    /**
     * 代理闭包。
     */
    fun delegate(closure: Closure<*>?, context: ComposeContext) {
        closure?.let {
            it.delegate = this
            it.call()
            selfCheck(context)
        }
    }

    /**
     * 自检。
     */
    protected open fun selfCheck(context: ComposeContext) {}
}