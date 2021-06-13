package kludge.api.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import kludge.api.ComposeContext
import kludge.api.common.Logln
import kludge.api.common.i
import kludge.api.transform.AppTransform
import kludge.api.transform.LibTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Author: Zhupf
 * Description: kludge 通用 plugin。
 */
open class KludgePlugin : Plugin<Project> {
    companion object {
        val contexts: HashMap<String, ComposeContext> = HashMap()
    }

    override fun apply(project: Project) {
        Logln.min("${i(project.name)} apply ${i(javaClass.simpleName)}.")
        val context = contexts[project.name] ?: return
        val extension: BaseExtension = when {
            context.isApplicationProject -> project.extensions.findByType(AppExtension::class.java)
            context.isAndroidLibraryProject -> project.extensions.findByType(LibraryExtension::class.java)
            else -> null
        } ?: return

        if (context.transformers.isNotEmpty()) {
            if (extension is AppExtension) {
                extension.registerTransform(AppTransform(context.transformers))
            } else if (extension is LibraryExtension) {
                extension.registerTransform(LibTransform(context.transformers))
            }
        }
        context.transforms.forEach { extension.registerTransform(it) }
    }
}