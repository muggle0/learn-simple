package com.muggle.sentinel.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description : sentinel 配置类
 * Date 2021/4/19
 * Created by muggle
 */


@Configuration
public class SentileConfig {




    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }



    @PostConstruct
    private void initRules() throws Exception {
        FlowRule rule1 = new FlowRule();
        rule1.setResource("test.hello");
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 每秒调用最大次数为 1 次
        rule1.setCount(1);
        List<FlowRule> rules = new ArrayList<>();
//        DegradeRuleManager.loadRules(List< DegradeRule > rules); // 修改降级规则
        rules.add(rule1);
        // 将控制规则载入到 Sentinel
//        AuthorityRuleManager
        FlowRuleManager.loadRules(rules);

       /* ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(remoteAddress, groupId, dataId,
            source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
            }));*/
//        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        DegradeRule rule = new DegradeRule();
        List<DegradeRule> rules1=new ArrayList<>();
        rule.setResource("test.hello");
        rule.setCount(0.01);
        // 秒级RT
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(10);
        rules1.add(rule);
        DegradeRuleManager.loadRules(rules1);

        ParamFlowRule paramFlowRule = new ParamFlowRule("resourceName")
                .setParamIdx(0)
                .setCount(5);
        // 单独设置限流 QPS，设置param 参数限流规则
        ParamFlowItem item = new ParamFlowItem().setObject("param")
                .setClassType(int.class.getName())
                .setCount(10);
        paramFlowRule.setParamFlowItemList(Collections.singletonList(item));
        ParamFlowRuleManager.loadRules(Collections.singletonList(paramFlowRule));

        SystemRule systemRule = new SystemRule();
        systemRule.setHighestCpuUsage(0.8);
        systemRule.setAvgRt(10);
        systemRule.setQps(10);
        systemRule.setMaxThread(10);
        systemRule.setHighestSystemLoad(2.5);
        SystemRuleManager.loadRules(Collections.singletonList(systemRule));

        AuthorityRule authorityRule = new AuthorityRule();
        authorityRule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        authorityRule.setLimitApp("127.0.0.1");
        authorityRule.setResource("test.hello");
        AuthorityRuleManager.loadRules(Collections.singletonList(authorityRule));
//        ContextUtil.enter("xxx", "origin");


    }


    @Bean
    public FilterRegistrationBean sentinelFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sentinelFilter");
        registration.setOrder(1);

        return registration;
    }


}
