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
        test.setTest(ttEnum.ONLINE);
        testEnumMapper.insert(test);
        return RestReturn.ok();
    }
    @RequestMapping("/testEnum2")
    public RestReturn test2() {

        testEnum testEnum = testEnumMapper.selectById("1357285455075520514");
        return RestReturn.ok(testEnum);
    }

}
