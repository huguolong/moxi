package com.moxi.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouzq
 * @date 2019/9/3
 * @desc mq配置
 */
@Configuration
public class RabbitmqConfig {
    private Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Value("${mq.exchange.name}")
    String exchangeName;
    @Value("${mq.queue.center}")
    String queueCenter;
    @Value("${mq.queue.center.key}")
    String queueCenterKey;
    @Value("${mq.exchange.dead.name}")
    String exchangeDeadName;
    @Value("${mq.queue.dead.center}")
    String queueDeadCenter;
    @Value("${mq.queue.dead.center.key}")
    String queueDeadCenterKey;


    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(5);
        factory.setMaxConcurrentConsumers(5);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 多个消费者
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        cachingConnectionFactory.setPublisherConfirms(true);
        cachingConnectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message));
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange topicExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public DirectExchange deadDirectExchange() {
        return new DirectExchange(exchangeDeadName);
    }

    @Bean
    public Queue paycenterNotifyQueue() {
        Map<String, Object> arguments = new HashMap<>();
        //出现异常进入重试队列
        arguments.put("x-dead-letter-exchange", exchangeDeadName);
        arguments.put("x-dead-letter-routing-key", queueDeadCenterKey);
        return new Queue(queueCenter, true, false, false, arguments);
    }

    @Bean
    public Binding bindPaycenterNotify() {
        return BindingBuilder.bind(paycenterNotifyQueue()).to(topicExchange()).with(queueCenterKey);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(queueDeadCenter,true,false,false);
    }
    /**
     * 绑定死信队列
     */
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadDirectExchange()).with(queueDeadCenterKey);
    }
}
