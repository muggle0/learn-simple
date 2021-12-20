package com.lcat.controller.apply;

import com.lcat.entity.dto.StartProcessRepresentation;
import com.lcat.service.service.MyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
    @Autowired
    private MyTestService myTestService;

    @RequestMapping(value = "/processes", method = RequestMethod.POST)
    public void startProcess(@RequestBody StartProcessRepresentation startProcessRepresentation) {
        myTestService.startProcess(startProcessRepresentation.getAssingee());
    }
}
