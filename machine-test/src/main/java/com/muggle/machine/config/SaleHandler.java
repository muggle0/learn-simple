package com.muggle.machine.config;


import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

// 这里名称一定要对上，不然无法捕获事件
@WithStateMachine(name = "SaleStatemachineFactory")
public class SaleHandler {


    // 表示监听 销售单从创建到审批的变换
    @OnTransition(source = "CREATED",target = "APPROVED")
    public void test(StateContext<SaleStateEnum,SaleEventEnum> context){
        // 销售单的id
        String id = context.getStateMachine().getId();
        // todo 创建出库单
    }
    @OnTransition(source = "CREATED",target = "APPROVED")
    public void test1(StateContext<SaleStateEnum,SaleEventEnum> context){
        // 销售单的id
        String id = context.getStateMachine().getId();
        // todo 通知出库审批人
    }
}
