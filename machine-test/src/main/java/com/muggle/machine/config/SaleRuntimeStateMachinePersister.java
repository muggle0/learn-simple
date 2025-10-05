package com.muggle.machine.config;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.persist.AbstractPersistingStateMachineInterceptor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateContext;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

@Service
public class SaleRuntimeStateMachinePersister extends AbstractPersistingStateMachineInterceptor<SaleStateEnum, SaleEventEnum,String>
implements StateMachineRuntimePersister<SaleStateEnum,SaleEventEnum,String>{

    @Override
    public void write(StateMachineContext<SaleStateEnum, SaleEventEnum> stateMachineContext, String id) throws Exception {
        // todo 写入 销售单表的订单状态字段
    }

    /**
     * 此处id 是new 状态机时传入的值
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public StateMachineContext<SaleStateEnum, SaleEventEnum> read(String id) throws Exception {
        // todo 根据id 查找对应的销售单，读取状态字段 new 一个状态机上下文
        DefaultStateMachineContext<SaleStateEnum, SaleEventEnum> context = new DefaultStateMachineContext<>(SaleStateEnum.APPROVED, null, null, null, null, id);
        return context;
    }

    @Override
    public StateMachineInterceptor<SaleStateEnum, SaleEventEnum> getInterceptor() {
        return this;
    }
}
