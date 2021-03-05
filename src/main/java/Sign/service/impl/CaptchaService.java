package Sign.service.impl;


import Sign.entity.VO.CaptchaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author FTSH
 * 生成图片验证码类的信息，并将code提交的redis
 */
@Service
public class CaptchaService {

    @Value("${server.session.timeout:300}")
    private Long timeout;
    @Autowired
    RedisTemplate redisTemplate;

    private final String CAPTCHA_KEY = "captcha:verification:";

    public CaptchaVO cacheCaptcha(String captcha){
        //生成一个随机标识符
        String captchaKey = UUID.randomUUID().toString();

        //缓存验证码并设置过期时间
        redisTemplate.opsForValue().set(CAPTCHA_KEY.concat(captchaKey),captcha,timeout, TimeUnit.SECONDS);

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaKey(captchaKey);
        captchaVO.setExpire(timeout);

        return captchaVO;
    }

}

