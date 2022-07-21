package com.chaoo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送
 */
@RestController
public class SenderController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/send")
    public String send(String msg) {
        redisTemplate.convertAndSend("myTopic", msg);
        return "消息发送成功";
    }
}
