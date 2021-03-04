package Sign.config.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * @author 邝明山
 * on 2020/9/16 20:57
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        //为了方便，下面key变成String类型
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //如果不序列号Key会乱码，会采用jdk去序列化，这会导致直接读取失效
        //JSON序列化配置
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
       //String序列化
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        //Key的序列号
        template.setKeySerializer(stringRedisSerializer);
        //value的序列化
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        //hash的key也采用String的序列化
        template.setHashKeySerializer(stringRedisSerializer);
        //hash的value采用Json的序列化
        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
