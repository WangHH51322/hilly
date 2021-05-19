package cn.edu.cup.hilly.dataSource.model.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.List;

public class ReceivePushMsgListener implements MessageListener {
    private Message message;
    @Override
    public void onMessage(Message message) {
        this.message = message;
    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {
        MessageListener.super.containerAckMode(mode);
    }

    @Override
    public void onMessageBatch(List<Message> messages) {
        MessageListener.super.onMessageBatch(messages);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
