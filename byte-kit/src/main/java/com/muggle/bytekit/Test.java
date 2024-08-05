package com.muggle.bytekit;

import com.alibaba.bytekit.asm.MethodProcessor;
import com.alibaba.bytekit.asm.interceptor.InterceptorProcessor;
import com.alibaba.bytekit.asm.interceptor.parser.DefaultInterceptorClassParser;
import com.alibaba.bytekit.utils.AgentUtils;
import com.alibaba.bytekit.utils.AsmUtils;
import com.alibaba.bytekit.utils.Decompiler;
import com.alibaba.deps.org.objectweb.asm.tree.ClassNode;
import com.alibaba.deps.org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

/**
 * Description
 * Date 2024/8/2
 * Created by muggle
 */
public class Test {

    public void test(String code){
        System.out.println(">>>>>>>>>>>>>>>");
    }

    public static void main(String[] args) throws Exception {
        AgentUtils.install();
        // 获取增强后的字节码
        byte[] bytes = enhanceClass(Message.class, new String[]{"test"}, TestInterceptor.class);
        // 通过 reTransform 增强类
        AgentUtils.reTransform(Test.class, bytes);
        // 查看反编译结果
        System.out.println(Decompiler.decompile(bytes));
        new Test().test("world");
    }

    protected static byte[] enhanceClass(Class targetClass, String[] targetMethodNames, Class interceptorClass) throws Exception {
        // 解析定义的 Interceptor类 和相关的注解
        DefaultInterceptorClassParser interceptorClassParser = new DefaultInterceptorClassParser();
        List<InterceptorProcessor> processors = interceptorClassParser.parse(interceptorClass);

        // 加载字节码
        ClassNode classNode = AsmUtils.loadClass(targetClass);

        List<String> methodNameList = Arrays.asList(targetMethodNames);

        // 对加载到的字节码做增强处理
        for (MethodNode methodNode : classNode.methods) {
            if (methodNameList.contains(methodNode.name)) {
                MethodProcessor methodProcessor = new MethodProcessor(classNode, methodNode);
                for (InterceptorProcessor interceptor : processors) {
                    interceptor.process(methodProcessor);
                }
            }
        }
        // 获取增强后的字节码
        byte[] bytes = AsmUtils.toBytes(classNode);
        return bytes;
    }

}
