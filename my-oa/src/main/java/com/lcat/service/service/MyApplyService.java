package com.lcat.service.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyApplyService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Transactional
    public void startProcess(){
        runtimeService.startProcessInstanceByKey("apply");
    }

    @Transactional
    public List<Task> getTask(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }
}
