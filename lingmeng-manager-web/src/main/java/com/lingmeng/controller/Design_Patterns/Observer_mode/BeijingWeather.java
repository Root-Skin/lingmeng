package com.lingmeng.controller.Design_Patterns.Observer_mode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skin
 * @createTime 2021年04月03日
 * @Description 现在发布北京的气象信息
 */
public class BeijingWeather implements WeatherCenter {

    private List<Observer> observerArrayList = new ArrayList<>();

    @Override
    public void publishWeatherInfo() {
        for(Observer observer : observerArrayList) {
            observer.sendWeatherWarning();
        }
    }
}
