package para.sso.ticket.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import para.sso.ticket.registry.TicketRedisTemplate;

public class RedisMessagePublisher {

    @Autowired
    @Qualifier("messageRedisTemplate")
    private TicketRedisTemplate messageRedisTemplate;

    @Autowired
    private ChannelTopic topic;

    public RedisMessagePublisher() {
    }

    public RedisMessagePublisher(
            TicketRedisTemplate redisTemplate, ChannelTopic topic) {
        this.messageRedisTemplate = redisTemplate;
        this.topic = topic;
    }

    public void publish(String message) {
        messageRedisTemplate.convertAndSend(topic.getTopic(), message);
    }
}