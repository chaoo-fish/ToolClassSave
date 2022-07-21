
package com.chaoo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * 消息接收者类
 */
@Slf4j
public class Receiver {
    private CountDownLatch latch;

    @Autowired
    public Receiver(CountDownLatch latch) {
        this.latch = latch;
    }

    // 用于接收消息的方法
    public void receiveMessage(String message) {
        log.info("接收到的消息为：<" + message + ">");
        latch.countDown();
    }
}
