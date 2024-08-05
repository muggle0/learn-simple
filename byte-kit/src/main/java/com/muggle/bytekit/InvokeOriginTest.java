package com.muggle.bytekit;

import com.alibaba.bytekit.asm.inst.impl.InstrumentImpl;
import com.alibaba.bytekit.utils.AgentUtils;
import com.alibaba.bytekit.utils.AsmUtils;
import com.alibaba.bytekit.utils.Decompiler;
import com.alibaba.deps.org.objectweb.asm.Type;
import com.alibaba.deps.org.objectweb.asm.tree.ClassNode;
import com.alibaba.deps.org.objectweb.asm.tree.MethodNode;

/**
 *
 * @author hengyunabc 2019-03-18
 *
 */
public class InvokeOriginTest {

    public static void main(String[] args) throws Exception {
        ClassNode apmClassNode = AsmUtils.loadClass(InvokeOriginDemo_APM.class);
        ClassNode originClassNode = AsmUtils.loadClass(InvokeOriginDemo.class);
        byte[] renameClass = AsmUtils.renameClass(AsmUtils.toBytes(apmClassNode),
                Type.getObjectType(originClassNode.name).getClassName());
         apmClassNode = AsmUtils.toClassNode(renameClass);
        ClassNode targetClassNode = AsmUtils.copy(originClassNode);
        System.out.println(Decompiler.decompile(renameClass));
        replace("returnVoid",apmClassNode,originClassNode,targetClassNode,InvokeOriginDemo.class);
        System.out.println(new InvokeOriginDemo().returnVoid("ssss"));
    }


    private static void replace(String methodName, ClassNode apmClassNode, ClassNode originClassNode, ClassNode targetClassNode, Class<InvokeOriginDemo> invokeOriginDemoClass) throws Exception {
        System.err.println(methodName);
        for (MethodNode methodNode : apmClassNode.methods) {
            if (methodNode.name.equals(methodName)) {
                methodNode = AsmUtils.removeLineNumbers(methodNode);
                // 从原来的类里查找对应的函数
                MethodNode findMethod = AsmUtils.findMethod(originClassNode.methods, methodNode);
                if (findMethod != null) {
                    MethodNode methodNode2 = InstrumentImpl.replaceInvokeOrigin(originClassNode.name, findMethod,
                            methodNode);

                    System.err.println(Decompiler.toString(methodNode2));

                    AsmUtils.replaceMethod(targetClassNode, methodNode2);

                } else {

                }
            }
        }

        byte[] resutlBytes = AsmUtils.toBytes(targetClassNode);

        System.err.println("=================");

        System.err.println(Decompiler.decompile(resutlBytes));

        // System.err.println(AsmUtils.toASMCode(resutlBytes));
        AgentUtils.reTransform(invokeOriginDemoClass, resutlBytes);
    }


}
