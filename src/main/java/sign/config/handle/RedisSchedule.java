package sign.config.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author 邝明山
 * on 2021/3/7 20:50
 *  笨方法，解决开发过程中redis时不时关闭问题
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class RedisSchedule {

    @Autowired
    RedisTemplate redisTemplate;

    //3.添加定时任务
    @Scheduled(cron = "* 0/1 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        redisTemplate.opsForValue().get("RedisSchedule");
    }
}