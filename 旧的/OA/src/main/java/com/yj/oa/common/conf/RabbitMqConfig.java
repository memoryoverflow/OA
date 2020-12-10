package com.yj.oa.common.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2019-12-23 10:52
 */
@Component
public class RabbitMqConfig
{
    public static final String LOG_QUEUE = "log_queue";
    public static final String LOG_EXCHANGE = "log_exChange";
    public static final String LOG_ROUTER_KEY = "log_router_key";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setExchange(LOG_EXCHANGE);
        template.setRoutingKey(LOG_ROUTER_KEY);
        template.setQueue(LOG_QUEUE);

        /**
         * <br>
         * 消息发送成功回调
         */
        template.setConfirmCallback(((correlationData, ack, cause) -> {
            if (ack)
            {
                log.debug("消息发送成功");
            }
            else
            {
                log.debug("消息发送到exchange失败,原因: {}", cause);
            }
        }));

        /**
         * <br>
         * 回掉函数
         */
        template.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationIdString();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        }));
        return template;
    }
}
