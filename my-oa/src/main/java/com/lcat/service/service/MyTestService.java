package com.lcat.service.service;

import cn.hutool.extra.spring.SpringUtil;
import com.lcat.common.repository.PersonRepository;
import com.lcat.entity.dto.FlowableParams;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MyTestService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private PersonRepository personRepository;

    /**
     * 启动流程实例
     * @param assignee
     */
    public void startProcess(String assignee) {
        FlowableParams flowableParamsDTO = personRepository.findByUsername(assignee);
        //设置流程发起人
        Authentication.setAuthenticatedUserId(flowableParamsDTO.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("params", flowableParamsDTO);
        runtimeService.startProcessInstanceByKey("apply", params);
        Authentication.setAuthenticatedUserId(null);
    }

    /**
     *获取任务列表
     * @param assignee
     * @return
     */
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    /**
     * 获取流程发起人
     * @param execution
     * @return
     */
    public String getStartUserId(DelegateExecution execution) {
        ProcessInstance processInstance = SpringUtil.getBean(RuntimeService.class)
                .createProcessInstanceQuery()
                .processInstanceId(execution.getProcessInstanceId())
                .singleResult();
        String startUserId = processInstance.getStartUserId();
        return startUserId;
    }

}
