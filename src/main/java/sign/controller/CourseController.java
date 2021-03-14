package sign.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import sign.common.result.Result;
import sign.entity.Account;
import sign.entity.ClassTime;
import sign.entity.Course;
import sign.entity.StudentCourse;
import sign.entity.VO.CourseVo;
import sign.service.ClassTimeService;
import sign.service.CourseService;
import sign.service.SignService;
import sign.service.StudentCourseService;

import java.util.ArrayList;
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
    @Autowired
    ClassTimeService classTimeService;
    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    SignService signService;
    @GetMapping("getCourseAndNumerical")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result selectCourseAndNumerical(){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Account account = (Account) redisTemplate.opsForValue().get("account:" + username);
        List<CourseVo> courseVoList = courseService.selectCourseAndNumerical(account.getId());
        return Result.success(courseVoList);
    }

    @PutMapping("putCourse")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result putCourse(@RequestParam("courseId") int courseId,@RequestParam("name") String name,@RequestParam("className") String className){
        Course course = courseService.getById(courseId);
        course.setClassName(className);
        course.setName(name);
        boolean b = courseService.updateById(course);
        return Result.success(b);
    }

    @PostMapping("postCourse")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result postCourse(@RequestParam("name") String name,@RequestParam("className") String className){
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Account account = (Account) redisTemplate.opsForValue().get("account:" + username);
        Course course = new Course(0,name,account.getId(),className);
        courseService.save(course);
        return Result.success(course);
    }

    @DeleteMapping("deleteCourse")
    @PreAuthorize("hasAnyAuthority('1','2')")
    @Transactional(rollbackFor = {RuntimeException.class})
    public Result deleteCourse(@RequestParam("courseId") int courseId){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("course_id",courseId);

        List<ClassTime> classTimeList = classTimeService.list(queryWrapper);
        List list=new ArrayList();
        for(ClassTime classTime:classTimeList){
            list.add(classTime.getId());
        }

        signService.removeByIds(list);
        classTimeService.remove(queryWrapper);
        studentCourseService.remove(queryWrapper);
        courseService.removeById(courseId);
        return Result.success();
    }
}

