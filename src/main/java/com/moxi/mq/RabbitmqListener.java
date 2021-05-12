package com.moxi.mq;

import com.moxi.common.config.Constant;
import com.moxi.domain.AppInfo;
import com.moxi.domain.ClickRecord;
import com.moxi.exceptions.BusinessException;
import com.moxi.mapper.ApplicationMapper;
import com.moxi.mapper.ClickRecordMapper;
import com.moxi.model.ButtReq;
import com.moxi.model.RabbitmqMessageVo;
import com.moxi.service.IApiService;
import com.moxi.util.JacksonUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhouzq
 * @date 2019/10/31
 * @desc mq消息监听者
 */
@Slf4j
@Component
public class RabbitmqListener {

    @Resource
    private ClickRecordMapper clickRecordMapper;
    @Resource
    private ApplicationMapper applicationMapper;

    @Resource
    private IApiService apiService;

    @RabbitListener(queues = "${mq.queue.center}", containerFactory = "singleListenerContainer")
    public void nettyWebsocketMsgQueue(Message message, Channel channel) {
        boolean isSuccess = false;
        try {

            String content = new String(message.getBody(), StandardCharsets.UTF_8);
            log.debug(">>> rabbitmq pay-center-notify queue listener: {}", content);
            RabbitmqMessageVo messageVo = JacksonUtils.json2Pojo(content, RabbitmqMessageVo.class);
            ButtReq req = JacksonUtils.json2Pojo(JacksonUtils.pojo2json(messageVo.getData()), ButtReq.class);
            req.setBeginTime(LocalDateTime.now());

            AppInfo appInfo = new AppInfo();
            appInfo.setAppId(req.getAppid());
            //保存点击记录
            appInfo = applicationMapper.findByAppId(appInfo);

            ClickRecord clickRecord = new ClickRecord();
            clickRecord.setReqUrl(req.getReqUrl());
            clickRecord.setReqParam(req.getReqParam());
            clickRecord.setAppId(req.getAppid());
            clickRecord.setChannelId(appInfo.getChannelId());
            clickRecord.setIdfa(req.getIdfa());
            clickRecord.setUa(req.getUa());
            clickRecord.setIp(req.getIp());
            clickRecord.setCallbackAddress(req.getCallback());
            clickRecord.setIsActivation(Integer.valueOf(Constant.Commons.ZERO));
            clickRecord.setCreateTime(new Date());
            clickRecord.setChannelCode(req.getCCode());
            clickRecordMapper.insert(clickRecord);
            final Integer clickId = clickRecord.getId();

            apiService.toAppTask(clickId,appInfo,req);

            isSuccess = true;
        } catch (Exception e) {
            log.error(">>> 消息队列-点击上服消费异常: {}", e.getMessage(), e);
            throw new BusinessException("点击上服消费异常");
        } finally {
            try {
                if (isSuccess) {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                } else {
                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                }
            } catch (Exception e) {
                log.error(">>> rabbitmq confirm error：{}", e.getMessage(), e);
                throw new BusinessException(e.getMessage());
            }
        }
    }
}
