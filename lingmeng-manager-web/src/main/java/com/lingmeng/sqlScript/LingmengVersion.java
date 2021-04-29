package com.lingmeng.sqlScript;

import com.lingmeng.anotation.Execute;
import com.lingmeng.anotation.Priority;
import com.lingmeng.dao.task.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("script/ht")
public class LingmengVersion {


    @Autowired
    private TaskMapper taskMapper;

    @Execute(timeArray = "2021-01-12")
    @Priority(1)
    @GetMapping("/createTable")
    public void createQyBaseTables() {
        taskMapper.createTable();
    }
}
