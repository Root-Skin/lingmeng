package com.lingmeng.common.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component

public class PackageConfig {

    private String taskJobPackage="com.lingmeng.task.jobs";
    //自动sql执行
    private String sqlScriptPackage="com.lingmeng.sqlScript";
}
