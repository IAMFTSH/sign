package sign.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import sign.common.result.Result;
import sign.entity.Course;
import sign.entity.StudentCourse;
import sign.entity.TeachingArea;
import sign.entity.VO.CourseAndTeacherVo;
import sign.entity.VO.StudentAndCoursesVo;
import sign.service.CourseService;
import sign.service.StudentCourseService;

import java.util.List;

/**
 * <p>
 * 学生与课程 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/student_course")
public class StudentCourseController {
    @Autowired
    StudentCourseService studentCourseService;
    @Autowired
    CourseService courseService;

    @GetMapping("getCourseAndTeacherVoListById")
    public Result getCourseAndTeacherVoListById(@RequestParam("id") int id) {
        List<CourseAndTeacherVo> courseAndTeacherVoList = studentCourseService.studentAndCourseAndTeacher(id);
        return Result.success(courseAndTeacherVoList);
    }

    @PostMapping("joinCourse")
    public Result joinCourse(@RequestParam("studentId") int studentId, @RequestParam("courseId") int courseId) {
        Course course = courseService.getById(courseId);
        if (course == null) {
            return Result.success("课程不存在");
        }
        StudentCourse studentCourse = new StudentCourse(studentId, courseId);
        boolean duplicate = false;
        boolean save = false;
        try {
            save = studentCourseService.save(studentCourse);
        } catch (DuplicateKeyException duplicateKeyException) {
            duplicate = true;
        }
        if (save == false && duplicate == false) {
            return Result.success("加入失败");
        } else if (duplicate == true) {
            return Result.success("请勿重复加入课程");
        } else {
            return Result.success();
        }
    }

    @DeleteMapping("exitCourse")
    public Result exitCourse(@RequestParam("studentId") int studentId, @RequestParam("courseId") int courseId) {
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId);
        queryWrapper.eq("course_id", courseId);
        boolean save = studentCourseService.remove(queryWrapper);
        return Result.success(save);
    }
}

