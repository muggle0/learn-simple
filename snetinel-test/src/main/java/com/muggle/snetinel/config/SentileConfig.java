package com.muggle.snetinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
        rule1.setCount(1);   // 每秒调用最大次数为 1 次

        List<FlowRule> rules = new ArrayList<>();
        rules.add(rule1);

        // 将控制规则载入到 Sentinel
        com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager.loadRules(rules);
    }
}
