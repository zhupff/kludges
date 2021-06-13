package kludge.api.common

import java.io.File
import java.util.*

/**
 * Author: Zhupf
 */

fun CharSequence?.upperCase(): String = this.toString().toUpperCase(Locale.getDefault())

fun CharSequence?.lowerCase(): String = this.toString().toLowerCase(Locale.getDefault())

fun File?.isExists(): Boolean = this != null && this.exists()

fun File?.isNotExists(): Boolean = this != null && !this.exists()

fun File?.isClassFile(): Boolean = this != null && this.isFile && this.name.endsWith(".class")

fun File?.deleteIfExists() { if (this != null && this.exists()) this.delete() }