package com.lingmeng.dao.task;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.timed.Task;


public interface TaskMapper extends BaseMapper<Task> {


    void createTable();

}
