package kludge.auto

import java.io.*
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.streams.toList

/**
 * Author: Zhupf
 */
object ServicesFiler {
    const val SERVICES_PATH = "META-INF/services"

    fun read(input: InputStream): Set<String> {
        val lines = HashSet<String>()
        InputStreamReader(input, UTF_8).use { isr ->
            BufferedReader(isr).use { br ->
                lines.addAll(br.lines()
                    .filter { !it.isNullOrEmpty() }
                    .map { it.substringBefore("#").trim() }
                    .filter { !it.isNullOrEmpty() }.toList())
            }
        }
        return lines
    }

    fun write(output: OutputStream, lines: Set<String>) {
        OutputStreamWriter(output, UTF_8).use { osw ->
            BufferedWriter(osw).use { bw ->
                lines.forEach { line ->
                    bw.write(line)
                    bw.newLine()
                }
                bw.flush()
            }
        }
    }
}