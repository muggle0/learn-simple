package com.lcat.controller.apply;

import com.lcat.entity.dto.FlowableParams;
import com.lcat.service.service.MyApplyService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class MyApplyController {
    @Autowired
    private MyApplyService myApplyService;

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public void startProcessInstance() {
        myApplyService.startProcess();
    }

    @RequestMapping(value = "/getTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FlowableParams> getTask(@RequestParam String assignee) {
        List<FlowableParams> flowableParamsList = new ArrayList<>();
        List<Task> taskList = myApplyService.getTask(assignee);
        for (Task task : taskList) {
            flowableParamsList.add(new FlowableParams(task.getId(), task.getOwner(), task.getAssignee()));
        }
        return flowableParamsList;
    }
}
