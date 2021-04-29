package com.lingmeng.controller.Design_Patterns.Observer_mode;

/**
 * @author skin
 * @date 2021/4/3 17:03
 * @description: 定义气象中心
 */
public interface WeatherCenter {
    /**
     * 发送气象消息
     */
    void publishWeatherInfo();
}
