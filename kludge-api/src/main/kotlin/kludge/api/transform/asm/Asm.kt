package kludge.api.transform.asm

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Author: Zhupf
 */
abstract class BaseClassVisitor(classVisitor: ClassVisitor)
    : ClassVisitor(Opcodes.ASM6, classVisitor) {

    var classVersion: Int = 0
        protected set
    var classAccess: Int = 0
        protected set
    var className: String = ""
        protected set
    var classSignature: String? = null
        protected set
    var classSuperName: String? = null
        protected set
    var classInterfaces: Array<String>? = null
        protected set

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<String>?) {
        this.classVersion = version
        this.classAccess = access
        this.className = name
        this.classSignature = signature
        this.classSuperName = superName
        this.classInterfaces = interfaces
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(access: Int, name: String, desc: String, signature: String?, exceptions: Array<String>?): MethodVisitor {
        return super.visitMethod(access, name, desc, signature, exceptions)
    }
}

abstract class BaseMethodVisitor(methodVisitor: MethodVisitor)
    : MethodVisitor(Opcodes.ASM6, methodVisitor)

abstract class BaseAdviceAdapter(mv: MethodVisitor, access: Int, name: String, desc: String)
    : AdviceAdapter(Opcodes.ASM6, mv, access, name, desc)