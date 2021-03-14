package sign.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sign.common.result.Result;
import sign.entity.Course;
import sign.service.CourseService;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CourseService courseService;

    @PutMapping("putFingerPrint")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result putFingerPrint(@RequestParam("courseId") int id,@RequestParam("teacherId") int teacher_id,@RequestParam("isOpen") Boolean isOpen){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",id);
        queryWrapper.eq("teacher_id",teacher_id);
        Course course = courseService.getOne(queryWrapper);
        if(course!=null){
            redisTemplate.opsForValue().set("courseFingerPrint:"+course.getId(),isOpen);
            return Result.success();
        }else {
            return Result.success("此课程非所属于您");
        }
    }

    @GetMapping("getFingerPrint")
    public Result getFingerPrint(@RequestParam("courseId") int courseId){
            Boolean fingerPrint =(Boolean) redisTemplate.opsForValue().get("courseFingerPrint:" + courseId);
            if(fingerPrint==null){
                redisTemplate.opsForValue().set("courseFingerPrint:" + courseId,false);
                return Result.success(false);
            }
            if(fingerPrint==false) {
                return Result.success(false);
            }
            return Result.success(true);
    }
}

