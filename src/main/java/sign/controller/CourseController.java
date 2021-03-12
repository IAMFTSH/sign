package sign.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sign.common.result.Result;
import sign.entity.Account;
import sign.entity.VO.CourseVo;
import sign.service.CourseService;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("getCourseAndNumerical")
    public Result selectCourseAndNumerical(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Account account = (Account) redisTemplate.opsForValue().get("account:" + username);
        List<CourseVo> courseVoList = courseService.selectCourseAndNumerical(account.getId());
        return Result.success(courseVoList);
    }
}

