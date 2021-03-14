package sign.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import sign.common.result.Result;
import sign.entity.ClassTime;
import sign.entity.Classroom;
import sign.entity.Do.ClassroomDo;
import sign.entity.TeachingArea;
import sign.entity.VO.ClassroomVo;
import sign.service.ClassTimeService;
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

        @Autowired
        ClassTimeService classTimeService;

    @GetMapping("getClassroomAndTeachingAreaBySomething")
    @PreAuthorize("hasAnyAuthority('1')")
    public Result getClassroomAndTeachingAreaBySomething(int id, String classroomNum, int teachingAreaId, String teachingAreaName, @RequestParam("pageNum") int pageNum) {
        ClassroomDo classroomDo = new ClassroomDo(id, classroomNum, teachingAreaId, teachingAreaName);
        Page<ClassroomVo> page = new Page<>(pageNum, 10);
        classroomService.selectClassroomAndTeachingArea(page, classroomDo);
        return Result.success(page);
    }

    @GetMapping("getClassroomAll")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result getClassroomAll() {
        List<Classroom> list = classroomService.list();
        return Result.success(list);
    }


    @PostMapping("postClassroom")
    @PreAuthorize("hasAnyAuthority('1')")
    public Result PostTeachingArea(@RequestParam("teachingAreaId") int teachingAreaId,@RequestParam("classroomNum") String classroomNum, @RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        Classroom classroom = new Classroom(0, classroomNum, teachingAreaId, lng, lat, radius);
        classroomService.save(classroom);
        return Result.success(classroom.getId());
    }

    @DeleteMapping("deleteClassroom")
    @PreAuthorize("hasAnyAuthority('1')")
    public Result DeleteClassroom(@RequestParam("id") String id) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("classroom_id",id);
        List<ClassTime> list = classTimeService.list(queryWrapper);
        if(list!=null&&list.size()>0){
            return Result.success("删除失败，当前课室被教师引用");
        }
        classroomService.removeById(id);
        return Result.success();
    }

    @PutMapping("putClassroom")
    @PreAuthorize("hasAnyAuthority('1')")
    public Result putClassroom(@RequestParam("id") int id, @RequestParam("classroomNum") String classroomNum,@RequestParam("teachingAreaId") int teachingAreaId, @RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        Classroom classroom = new Classroom(id, classroomNum, teachingAreaId, lng, lat, radius);
        classroomService.updateById(classroom);
        return Result.success();
    }

    @PutMapping("copyTeachingAreaPoint")
    @PreAuthorize("hasAnyAuthority('1')")
    public Result copyTeachingAreaPoint(@RequestParam("id") int id,@RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        Classroom classroom = classroomService.getById(id);
        classroom.setLat(lat);
        classroom.setLng(lng);
        classroom.setRadius(radius);
        classroomService.updateById(classroom);
        return Result.success();
    }
}

