package com.gangsterhyj.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gangsterhyj.config.RabbitMQConfig;
import com.gangsterhyj.domain.SecondKillInfo;
import com.gangsterhyj.model.UserInfo;
import com.gangsterhyj.service.ISecondKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by gangsterhyj on 17-2-17.
 */
@Service
@Component
@Slf4j
public class SecondKillService implements ISecondKillService {
    private AmqpTemplate rabbitTemplate;
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public SecondKillService(AmqpTemplate rabbitTemplate, StringRedisTemplate stringRedisTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.stringRedisTemplate.setEnableTransactionSupport(true);
    }
    class RedisOpValue {
        private Long oldValue;
        private Long newValue;
        public RedisOpValue() {}
        public RedisOpValue(Long o, Long n) {
            this.oldValue = o;
            this.newValue = n;
        }
        public boolean opSuccess() {
            return oldValue > 0 && oldValue.equals(newValue + 1);
        }

        @Override
        public String toString() {
            return "oldValue: " + oldValue + " newValue: " + newValue;
        }
    }
    /**
     * 首先访问Redis, counter+type 是否还有剩余
     * 若有剩余,则执行减一操作,并向消息队列发送该用户和优惠券的信息
     *     WATCH counter
     *     val = GET counter
     *     val = val + 1
     *     MULTI
     *     SET counter$val
     *     EXEC
     * @param type: 优惠券的类型, 0: 5元; 1: 10元; 2: 15元
     * @param userInfo: 用户信息
     * @return
     */
    @Override
    public boolean secondKill(final Integer type, final UserInfo userInfo)  {
        final RedisOpValue redisOpValue;
        SessionCallback<RedisOpValue> callback = new SessionCallback<RedisOpValue>() {
            @Override
            public <K, V> RedisOpValue execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                redisOperations.watch((K) ("counter" + type));
                ValueOperations<K, V> kvValueOperations = redisOperations.opsForValue();
                String s = (String) kvValueOperations.get("counter" + type);
                if (s == null || Long.parseLong(s) <= 0) {
                    throw new RuntimeException("优惠券不存在或者优惠券已经被抢完");
                }
                redisOperations.multi();
                kvValueOperations.increment((K) ("counter" + type), -1);
                return  new RedisOpValue(Long.parseLong(s), (Long) redisOperations.exec().get(0));
            }
        };
        // 如果有事件机制就好了...以下代码是假设rabbitTemplate不会抛出异常
        try {
            redisOpValue = stringRedisTemplate.execute(callback);
            log.info("" + redisOpValue);
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
        // 执行成功后向消息队列发送信息
        if (redisOpValue.opSuccess()) {
            SecondKillInfo secondKillInfo = new SecondKillInfo(type, userInfo.getUsername(), new Date());
            ObjectMapper mapper = new ObjectMapper();
            try {
                rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, mapper.writeValueAsString(secondKillInfo));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("消息队列生产者的json处理出错");
            }
        } else {
            throw new RuntimeException("自增操作没有成功");
        }
        return true;
    }
}
