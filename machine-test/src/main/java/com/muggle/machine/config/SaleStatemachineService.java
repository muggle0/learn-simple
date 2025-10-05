package com.muggle.machine.config;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.AbstractStateMachineFactory;
import org.springframework.statemachine.config.ObjectStateMachineFactory;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.stereotype.Service;

@Service
public class SaleStatemachineService extends DefaultStateMachineService<SaleStateEnum,SaleEventEnum> {


    public SaleStatemachineService(StateMachineFactory<SaleStateEnum, SaleEventEnum> stateMachineFactory,
                                   StateMachinePersist<SaleStateEnum, SaleEventEnum, String> stateMachinePersist) {
        super(stateMachineFactory, stateMachinePersist);
        ObjectStateMachineFactory objfactory = (ObjectStateMachineFactory) stateMachineFactory;
        // 此处一定要重新设置名称，状态机框架的一个bug
        objfactory.setBeanName("SaleStatemachineFactory");
    }

    public void test(){
        StateMachine<SaleStateEnum, SaleEventEnum> stateMachine = this.acquireStateMachine("销售单id");
        stateMachine.sendEvent(SaleEventEnum.APPROVE);
        this.releaseStateMachine("销售单id");
    }
}
