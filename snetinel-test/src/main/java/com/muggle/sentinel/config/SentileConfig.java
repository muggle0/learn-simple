package com.muggle.sentinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RedisConnectionConfig redisConnectionConfig;


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

        ReadableDataSource<String, List<FlowRule>> redisDataSource = new RedisDataSource<List<FlowRule>>(redisConnectionConfig, ruleKey, channel, flowConfigParser);
        FlowRuleManager.register2Property(redisDataSource.getProperty());
    }

    public <T> void pushRules(List<T> rules, Converter<List<T>, String> encoder) {

        StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
        RedisPubSubCommands<String, String> subCommands = connection.sync();
        String value = encoder.convert(rules);
        subCommands.multi();
        subCommands.set(ruleKey, value);
        subCommands.publish(ruleChannel, value);
        subCommands.exec();
    }
}
