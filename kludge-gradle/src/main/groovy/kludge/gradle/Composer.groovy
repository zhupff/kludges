package kludge.gradle

import kludge.api.ComposeContext
import kludge.api.closure.BaseClosureDelegate
import kludge.api.common.CSIKt
import kludge.api.common.Logln
import kludge.api.plugin.KludgePlugin
import org.gradle.api.Project

/**
 * Author: Zhupf
 * Description: kludge gradle 组装工具。
 */
final class Composer {
    private Composer() {}

    def static compose(@DelegatesTo(ComposeDelegate.class) Closure closure) {
        def closureDelegates = getClosureDelegates()
        def realComposeDelegateClass = getRealComposeDelegateClass(closureDelegates)
        def realComposeDelegate = realComposeDelegateClass.newInstance(closure, closureDelegates)
        closure.delegate = realComposeDelegate
        closure.call()

        realComposeDelegate.apply()
    }

    def private static getClosureDelegates() {
        HashMap<String, BaseClosureDelegate> closureDelegates = new HashMap<>()
        ServiceLoader<BaseClosureDelegate> serviceLoader = ServiceLoader.load(BaseClosureDelegate.class)
        serviceLoader.each { closureDelegates.put(it.name, it)}
        return closureDelegates
    }

    def private static getRealComposeDelegateClass(Map<String, BaseClosureDelegate> closureDelegates) {
        StringBuilder sb = new StringBuilder()
            .append("package kludge.gradle\n")
            .append("class RealComposeDelegate extends Composer.ComposeDelegate {\n")
            .append("    RealComposeDelegate(closure, closureDelegates) { super(closure, closureDelegates) }\n")
        closureDelegates.each { name, _ -> sb
            .append("    def ${name}(Closure closure) { dispatchClosure(\"${name}\", closure) }\n")
        }
        sb.append("}\n")
//        Logln.v(CSIKt.d(sb.toString()))
        return new GroovyClassLoader().parseClass(sb.toString())
    }


    protected static class ComposeDelegate {
        @Delegate Project project
        HashMap<String, BaseClosureDelegate> closureDelegates
        ComposeContext context

        ComposeDelegate(script, closureDelegates) {
            this.project = script.project
            this.closureDelegates = closureDelegates
            this.context = new ComposeContext(this.project)
        }

        def final protected dispatchClosure(String name, Closure closure) {
            closureDelegates.get(name).delegate(closure, context)
        }

        def final apply() {
            KludgePlugin.contexts.put(project.name, context)
            apply plugin: KludgePlugin
        }
    }
}