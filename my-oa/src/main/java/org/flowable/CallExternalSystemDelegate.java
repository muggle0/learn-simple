package org.flowable;

import com.lcat.service.service.MyTestService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CallExternalSystemDelegate implements JavaDelegate {
    @Autowired
    private MyTestService myTestService;
    @Autowired
    private ProcessEngine processEngine;

    @Override
    public void execute(DelegateExecution delegateExecution) {
//        Object employee = delegateExecution.getVariable("employee");
        String startUserId = myTestService.getStartUserId(delegateExecution);
        Object employee = delegateExecution.getVariable("employee");
        Object manager = delegateExecution.getVariable("manager");
        log.info("申请人{}的申请已由{}、{}审核", startUserId, employee, manager);

        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activitys = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(delegateExecution.getProcessInstanceId())
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        log.info("instanceList:{}", activitys);

        for (HistoricActivityInstance activity : activitys) {
            log.info("{} took {} milliseconds", activity.getActivityId(), activity.getDurationInMillis());
        }
    }
}
