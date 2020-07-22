package com.moxi.mq;

import com.moxi.model.RabbitmqMessageVo;
import com.moxi.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhouzq
 * @date 2019/10/31
 * @desc mq消息发送者
 */
@Slf4j
@Component
public class RabbitmqSender {

    private String exchangeName;
    private String queueCenter;
    private String queueCenterKey;
    private String exchangeDeadName;
    private String queueDeadCenter;
    private String queueDeadCenterKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendNotifyMessage(RabbitmqMessageVo messageVo) {
        //发送mq消息
        log.info(">>> rabbitmq message sender tags: {}", messageVo.getTags());
        rabbitTemplate.convertAndSend(messageVo.getDirectExchange(), messageVo.getRoutingKey(), messageVo);
    }

    public String getExchangeName() {
        return exchangeName;
    }

    @Value("${mq.exchange.name}")
    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getQueueCenter() {
        return queueCenter;
    }

    @Value("${mq.queue.center}")
    public void setQueueCenter(String queueCenter) {
        this.queueCenter = queueCenter;
    }

    public String getQueueCenterKey() {
        return queueCenterKey;
    }

    @Value("${mq.queue.center.key}")
    public void setQueueCenterKey(String queueCenterKey) {
        this.queueCenterKey = queueCenterKey;
    }

    public String getExchangeDeadName() {
        return exchangeDeadName;
    }

    @Value("${mq.exchange.dead.name}")
    public void setExchangeDeadName(String exchangeDeadName) {
        this.exchangeDeadName = exchangeDeadName;
    }

    public String getQueueDeadCenter() {
        return queueDeadCenter;
    }

    @Value("${mq.queue.dead.center}")
    public void setQueueDeadCenter(String queueDeadCenter) {
        this.queueDeadCenter = queueDeadCenter;
    }

    public String getQueueDeadCenterKey() {
        return queueDeadCenterKey;
    }

    @Value("${mq.queue.dead.center.key}")
    public void setQueueDeadCenterKey(String queueDeadCenterKey) {
        this.queueDeadCenterKey = queueDeadCenterKey;
    }
}
