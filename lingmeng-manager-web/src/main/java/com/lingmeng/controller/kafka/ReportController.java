package com.lingmeng.controller.kafka;

import com.alibaba.fastjson.JSON;
import com.lingmeng.common.config.kafka.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skin
 * @createTime 2021年03月21日
 * @Description
 */
@RestController
@RequestMapping("/good")
public class ReportController {

    @Value("${spring.kafka.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping("/receive")
    public Map receive(@RequestBody String json) {
        Map<String, String> result = new HashMap<>();

        try {
            Message msg = new Message();

            msg.setMessage(json);
            msg.setCount(1);
            msg.setTimeStamp(System.currentTimeMillis());

            String msgJSON = JSON.toJSONString(msg);

            kafkaTemplate.send(topic, msgJSON);
            System.out.println(msgJSON);

            result.put("success", "true");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", "false");
        }

        return result;
    }
}
