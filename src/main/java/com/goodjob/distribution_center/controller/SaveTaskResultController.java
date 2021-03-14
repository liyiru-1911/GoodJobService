package com.goodjob.distribution_center.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/saveTaskResult")
public class SaveTaskResultController {

    @PostMapping("/save")
    public void save(Map<String, Object> taskResult) {
        System.out.println("******************TaskResult*********************");
        System.out.println(taskResult);
    }


}
