package com.muggle.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description
 * Date 2023/9/1
 * Created by muggle
 */
@Service
@Aspect
public class LogAround {

    @Resource
    private Map<String,LogHandler> logHandlerMap;

    @Pointcut("@annotation(com.muggle.aop.LogAop)")
    public void modules() {

    }

    @Around(value = "modules() &&@annotation(logAop)")
    public Object around(ProceedingJoinPoint point,LogAop logAop) throws Throwable {
        LogHandler logHandler = logHandlerMap.get(logAop.handler());
        Object result = null;
        for (Object arg : point.getArgs()) {
            if (arg instanceof BaseBean) {
                String trace = ((BaseBean) arg).getTrace();
                logHandler.equals(trace);
                ((BaseBean) arg).setTrace(trace);
                ((BaseBean) arg).setTrace("");
                String s = MDC.get("");

                if (StringUtils.isEmpty(s)){
                    MDC.put("","");
                }else {
                    MDC.put("",s.concat("||").concat(""));
                }
                //
            }

        }
        point.getArgs();
        return result;
    }


}
