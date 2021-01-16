package com.catan.infrastructure.persistence.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
public class RedisClient {

    @Bean
    public JedisPool getRedisClient(){
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, Protocol.DEFAULT_TIMEOUT);
        return pool;
    }
}
