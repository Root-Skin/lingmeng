package com.lingmeng.controller.address;

import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.auth.enumTestReq;
import com.lingmeng.dao.log.SysExecutedMethodsMapper;
import com.lingmeng.script.LifeCycleEnum;
import com.lingmeng.script.SysExecutedMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author skin
 * @createTime 2021年01月13日
 * @Description
 */
@RestController
@RequestMapping("/lingmengTest")
public class lingmengTest {

    @Autowired
    private SysExecutedMethodsMapper sysExecutedMethodsMapper;
    @GetMapping("/list")
    public RestReturn list() {

        enumTestReq req = new enumTestReq();
        req.setEnum2(LifeCycleEnum.EXCEPTION);
        SysExecutedMethods sysExecutedMethods = sysExecutedMethodsMapper.query(req);
              return RestReturn.ok(sysExecutedMethods);
    }
}
