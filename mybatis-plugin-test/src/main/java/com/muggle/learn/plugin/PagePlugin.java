package com.muggle.learn.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * @Description:  mybatis插件就是对ParameterHandler、ResultSetHandler、StatementHandler、Executor
 * 这四个接口上的方法进行拦截，利用JDK动态代理机制，为这些接口的实现类创建代理对象
 * @Author: muggle
 * @Date: 2020/4/29
 **/
@Intercepts({@Signature(type= StatementHandler.class,method="parameterize",args=java.sql.Statement.class)})
public class PagePlugin implements Interceptor {
    // 拦截目标对象中目标方法的执行
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    // 包装目标对象,即为目标对象创建一个代理对象
    @Override
    public Object plugin(Object target) {
        // 借助 Plugin 的 wrap(Object target,Interceptor interceptor); 包装我们的目标对象
        // target: 目标对象, interceptor: 拦截器, this 表示使用当前拦截器
        Object proxy = Plugin.wrap(target,this);
        return proxy;
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件的配置信息:"+properties.toString());
    }
}
