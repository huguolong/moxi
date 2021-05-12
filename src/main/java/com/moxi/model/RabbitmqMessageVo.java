package com.moxi.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouzq
 * @date 2019/10/31
 * @desc mq消息基类
 */
@Data
public class RabbitmqMessageVo implements Serializable {

    /**
     *  队列名称
     */
    private String queue;

    /**
     *  交换机
     */
    private String directExchange;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 消息内容
     */
    private Object data;

    private String tags;

    public static RabbitmqMessageVo.Builder custom() {
        return new RabbitmqMessageVo.Builder();
    }

    public static class Builder {

        private String queue;

        private String directExchange;

        private String routingKey;

        private Object data;

        public Builder queue(String queue) {
            this.queue = queue;
            return this;
        }

        public Builder directExchange(String directExchange) {
            this.directExchange = directExchange;
            return this;
        }

        public Builder routingKey(String routingKey) {
            this.routingKey = routingKey;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public RabbitmqMessageVo build() {
            RabbitmqMessageVo messageVo = new RabbitmqMessageVo();
            messageVo.setQueue(this.queue);
            messageVo.setRoutingKey(this.routingKey);
            messageVo.setDirectExchange(this.directExchange);
            messageVo.setData(this.data);
            return messageVo;
        }
    }

}
