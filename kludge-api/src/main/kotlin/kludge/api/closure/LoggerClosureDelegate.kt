package kludge.api.closure

import kludge.api.ComposeContext
import kludge.api.common.Logger
import kludge.api.common.Logln
import kludge.api.common.i
import java.util.*

/**
 * Author: Zhupf
 * Description: Logger 代理
 *
 * Composer.compose {
 *     logger {
 *         minLevel = "v"
 *         maxLevel = "e"
 *     }
 * }
 */
open class LoggerClosureDelegate : BaseClosureDelegate() {

    override val closureName: String = "logger"

    var minLevel: String? = null

    var maxLevel: String? = null

    override fun selfCheck(context: ComposeContext) {
        super.selfCheck(context)
        minLevel = when (minLevel?.toLowerCase(Locale.getDefault())) {
            "e", "error"   -> "Error"
            "w", "warn"    -> "Warn"
            "i", "info"    -> "Info"
            "d", "debug"   -> "Debug"
            "v", "verbose" -> "Verbose"
            else           -> "Verbose"
        }
        maxLevel = when (maxLevel?.toLowerCase(Locale.getDefault())) {
            "v", "verbose" -> "Verbose"
            "d", "debug"   -> "Debug"
            "i", "info"    -> "Info"
            "w", "warn"    -> "Warn"
            "e", "error"   -> "Error"
            else           -> "Error"
        }
        Logger.init(this)
        Logln.min("Elysium Logger, minLevel:%s maxLevel:%s", i(minLevel!!), i(maxLevel!!))
    }
}