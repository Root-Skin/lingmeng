package com.lingmeng.controller.test;

import com.lingmeng.base.RestReturn;
import com.lingmeng.dao.test.TestEnumMapper;
import com.lingmeng.testEnum.testEnum;
import com.lingmeng.testEnum.ttEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author skin
 * @createTime 2021年02月04日
 * @Description
 */
@RestController
public class Ttsc {


    @Autowired
    private TestEnumMapper testEnumMapper;

    @RequestMapping("/testEnum")
    public RestReturn sendEmailCode() {
        testEnum test = new testEnum();
        test.setTest2(ttEnum.ONLINE);
        testEnumMapper.insert(test);
        return RestReturn.ok();
    }
    @RequestMapping("/testEnum2")
    public RestReturn test2() {
        testEnum testEnum = testEnumMapper.selectById("1364149787373686786");
        return RestReturn.ok(testEnum);
    }
    //普通接口调用
    @RequestMapping("/testUse")
    public RestReturn testUse() {

        System.out.println("哇哈哈222");
        for(int i=0;i<9999999;i++){
            Thread t = Thread.currentThread();
            String name = t.getName();
            System.out.println(name);
        }
//        System.out.println("sleep11");

        return RestReturn.ok("成功");
    }

}
