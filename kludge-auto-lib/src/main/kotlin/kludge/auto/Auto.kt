package kludge.auto

/**
 * Author: Zhupf
 * Description: todo
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD)
annotation class Auto(val value: String = "")
