package com.lingmeng.config.filter;

import org.springframework.stereotype.Component;

/**
 * @author skin
 * @createTime 2021年02月26日
 * @Description
 */
@Component
public class TestServiceImpl  implements TestService {
    @Override
    public void a() {
        System.out.println("我是方法A");
    }
}
