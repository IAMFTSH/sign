package sign.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sign.common.result.Result;
import sign.entity.Classroom;
import sign.entity.Do.ClassroomDo;
import sign.entity.TeachingArea;
import sign.entity.VO.ClassroomVo;
import sign.service.ClassroomService;

import java.util.List;

/**
 * <p>
 * 课室 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;

    @GetMapping("getClassroomAndTeachingAreaBySomething")
    public Result getClassroomAndTeachingAreaBySomething(int id, String classroomNum, int teachingAreaId, String teachingAreaName, @RequestParam("pageNum") int pageNum) {
        ClassroomDo classroomDo = new ClassroomDo(id, classroomNum, teachingAreaId, teachingAreaName);
        Page<ClassroomVo> page = new Page<>(pageNum, 10);
        classroomService.selectClassroomAndTeachingArea(page, classroomDo);
        return Result.success(page);
    }

    @PostMapping("postClassroom")
    public Result PostTeachingArea(@RequestParam("teachingAreaId") int teachingAreaId,@RequestParam("classroomNum") String classroomNum, @RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        Classroom classroom = new Classroom(0, classroomNum, teachingAreaId, lng, lat, radius);
        classroomService.save(classroom);
        return Result.success(classroom.getId());
    }

    @DeleteMapping("deleteClassroom")
    public Result DeleteClassroom(@RequestParam("id") String id) {
        classroomService.removeById(id);
        return Result.success();
    }

    @PutMapping("putClassroom")
    public Result putClassroom(@RequestParam("id") int id, @RequestParam("classroomNum") String classroomNum,@RequestParam("teachingAreaId") int teachingAreaId, @RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        Classroom classroom = new Classroom(id, classroomNum, teachingAreaId, lng, lat, radius);
        classroomService.updateById(classroom);
        return Result.success();
    }

    @PutMapping("copyTeachingAreaPoint")
    public Result copyTeachingAreaPoint(@RequestParam("id") int id,@RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        Classroom classroom = classroomService.getById(id);
        classroom.setLat(lat);
        classroom.setLng(lng);
        classroom.setRadius(radius);
        classroomService.updateById(classroom);
        return Result.success();
    }
}

