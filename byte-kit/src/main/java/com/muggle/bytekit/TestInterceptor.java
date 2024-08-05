package com.muggle.bytekit;

import com.alibaba.bytekit.agent.inst.Instrument;
import com.alibaba.bytekit.agent.inst.InstrumentApi;
import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtEnter;

/**
 * Description
 * Date 2024/8/2
 * Created by muggle
 */
@Instrument(Interface = "com.muggle.bytekit.test.Message")
public class TestInterceptor {

    @AtEnter(inline = true)
    public static void atEnter(@Binding.LocalVarNames String[] varNames,
                               @Binding.Args Object[] args){
        for (Object varName : args) {
            System.out.println(varName);
        }
    }


    public String test()  {
        System.out.println("invoker class: " + this.getClass().getName());
        String result = InstrumentApi.invokeOrigin();
        return result;
    }
}
