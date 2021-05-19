package cn.edu.cup.hilly.dataSource.model.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Component
public class PushMsgProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private static Logger log = LoggerFactory.getLogger(PushMsgProducer.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    //消息发送确认回调方法
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息发送成功:" + correlationData);
    }

    //消息发送失败回调方法
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息发送失败:" + new String(message.getBody()));
    }

    /**
     * 发送消息
     *
     * @param messageVo
     */
    public void send(WiselyMessage messageVo) throws IOException {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("exchange_test", messageVo.getRoutingKey(), messageVo, correlationId);
    }
}


