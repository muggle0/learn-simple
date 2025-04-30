package com.muggle.bytekit;

import com.alibaba.bytekit.agent.inst.Instrument;
import com.alibaba.bytekit.agent.inst.InstrumentApi;

/**
 *
 * @author hengyunabc 2019-03-18
 *
 */
@Instrument(Class = "com.muggle.bytekit.test.InvokeOriginDemo")
public abstract class InvokeOriginDemo_APM {

    public String returnVoid(String test) {
        Object o = InstrumentApi.invokeOrigin();
        System.out.println(o);
        System.out.println(">>>>>>>>>>>>>>>>");
        return "vvvvvvvvvvvv";
    }

}
