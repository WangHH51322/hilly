package cn.edu.cup.hilly.dataSource.config;

import cn.edu.cup.hilly.dataSource.model.rabbitmq.ReceivePushMsgListener;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class RabbitmqConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConfig.class);
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("192.168.136.128");
//        connectionFactory.setHost("hillyrabbitmq");
//        connectionFactory.setPort(5762);
        /*channel缓存的大小*/
        connectionFactory.setChannelCacheSize(200);
        /**/
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {
                logger.info("创建rabbitmq连接");
            }

            @Override
            public void onClose(Connection connection) {
                logger.info("关闭rabbitmq连接");
            }
        });
        //设置虚拟主机，默认/
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.SIMPLE);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    /**
     * 配置MQ传输序列化对象
     *
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.enable(MapperFeature.USE_GETTERS_AS_SETTERS);
        mapper.enable(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new Jackson2JsonMessageConverter(mapper);
    }

    /**
     * 配置模版对象
     *
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate template() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    /**
     * 配置管理器
     *
     * @return RabbitAdmin
     */
    @Bean
    public RabbitAdmin rabbitAdmin() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    /**
     * RepublishMessageRecoverer允许在重试次数耗尽后，发布失败消息
     *
     * @return RetryOperationsInterceptor
     */
    @Bean
    public RetryOperationsInterceptor interceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(5)
                .recoverer(new RepublishMessageRecoverer(template()))
                .build();
    }

    /**
     * 配置spring上下文监听容器 需要延迟启动
     *
     * @return SimpleMessageListenerContainer
     */
    public SimpleMessageListenerContainer newListenerContainer(MessageListener messageListener, String[] queues) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setAutoDeclare(false);
        //声明失败重复次数
        container.setDeclarationRetries(1);
        //可接受来自broker一个socket帧中的消息数目. 数值越大，消息分发速度就越快, 但无序处理的风险更高
        container.setPrefetchCount(10);
        container.addQueueNames(queues);
        container.setAcknowledgeMode(AcknowledgeMode.NONE);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(100);
        container.setConcurrentConsumers(10);
        container.setMessageListener(messageListener);
        container.setAutoStartup(false);
        container.setExclusive(false);
         /*丢弃所有失败的消息 false丢弃 true 循环处理 或者，
         你可以抛出一个AmqpRejectAndDontRequeueException;这会阻止消息重新入列,不管defaultRequeueRejected 属性设置的是什么.*/
        container.setDefaultRequeueRejected(false);
        container.start();
        return container;
    }

    /**
     * 声明交互器
     *
     * @return
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("exchange_test3");
    }

    /**
     * 创建一个ReceivePushMsgListener，监听routingKey为“rk_recivemsg”的队列实现客户端收到消息后向此队列发送确认收到消息
     */
    @Bean
    public Object declareDirectQueue() {
        List<String> receiveQueueNames = new ArrayList<>();
        String receive = "queue_pushmsg3";
        declare(receive, directExchange(), "rk_recivemsg3");
        receiveQueueNames.add(receive);
        newListenerContainer(new ReceivePushMsgListener(), receiveQueueNames.toArray(new String[receiveQueueNames.size()]));
        return null;
    }

    private void declare(String queueName, DirectExchange exchange, String routingKey) {
        RabbitAdmin admin = rabbitAdmin();
        Queue queue = new Queue(queueName, true, false, false);
        admin.declareQueue(queue);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));
    }
}

