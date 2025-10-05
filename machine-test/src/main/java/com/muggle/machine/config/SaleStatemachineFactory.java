package com.muggle.machine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.*;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "SaleStatemachineFactory")
public class SaleStatemachineFactory extends EnumStateMachineConfigurerAdapter<SaleStateEnum,SaleEventEnum> {

    @Autowired
    private SaleRuntimeStateMachinePersister persister;

    @Override
    public void configure(StateMachineConfigBuilder<SaleStateEnum, SaleEventEnum> config) throws Exception {
        super.configure(config);
    }

    @Override
    public void configure(StateMachineModelConfigurer<SaleStateEnum, SaleEventEnum> model) throws Exception {
        super.configure(model);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<SaleStateEnum, SaleEventEnum> config) throws Exception {
       config.withPersistence().runtimePersister(persister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<SaleStateEnum, SaleEventEnum> states) throws Exception {
        states.withStates().initial(SaleStateEnum.CREATED).states(EnumSet.allOf(SaleStateEnum.class))
                .end(SaleStateEnum.RECEIVED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<SaleStateEnum, SaleEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(SaleStateEnum.CREATED).target(SaleStateEnum.APPROVED)
                .event(SaleEventEnum.APPROVE)
                .and().withExternal().source(SaleStateEnum.APPROVED).target(SaleStateEnum.OUTBOUND)
                .event(SaleEventEnum.OUT_APPROVE);
    }

}
