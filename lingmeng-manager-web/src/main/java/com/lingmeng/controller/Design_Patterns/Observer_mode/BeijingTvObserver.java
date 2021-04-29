package com.lingmeng.controller.Design_Patterns.Observer_mode;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description
 */
public class BeijingTvObserver implements Observer{
    @Override
    public void sendWeatherWarning(){
        System.out.println("北京卫视天气预报开始了");
    }

}
