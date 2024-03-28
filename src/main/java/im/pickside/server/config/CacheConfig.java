package im.pickside.server.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder()
                .transactionAware()
                .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(defaultCacheConfig())
                .build();
    }

    /**
     * Key 직렬화 설정
     */
    @Bean
    protected RedisSerializationContext.SerializationPair<String> keySerialization() {
        return RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
    }

    /**
     * Value 직렬화 설정
     */
    @Bean
    protected RedisSerializationContext.SerializationPair<Object> valueSerialization() {
        return RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
    }

    @Bean
    protected RedisCacheConfiguration defaultCacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(keySerialization())
                .serializeValuesWith(valueSerialization())
                .disableCachingNullValues();
    }

}
