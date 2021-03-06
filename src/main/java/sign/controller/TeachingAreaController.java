package sign.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sign.common.result.Result;
import sign.entity.Account;
import sign.entity.TeachingArea;
import sign.service.TeachingAreaService;

import java.util.List;

/**
 * <p>
 * 教学区 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/teaching-area")
public class TeachingAreaController {
    @Autowired
    TeachingAreaService teachingAreaService;

    @GetMapping("teachingAreaByNameOrId")
    public Result accountsBySomething(int id,String name, @RequestParam("pageNum") int pageNum) {

        QueryWrapper queryWrapper = new QueryWrapper();
        //arg1  当前页    arg2   页大小
        if (name.length() != 0) {
            queryWrapper.like("name", name);
        }
        if (id!= 0) {
            queryWrapper.eq("id", id);
        }
        queryWrapper.orderByAsc("id");
        Page<TeachingArea> page = new Page<>(pageNum, 10);
        teachingAreaService.page(page, queryWrapper);
        return Result.success(page);
    }
    @GetMapping("teachingAreas")
    public Result accounts() {
        List<TeachingArea> teachingAreas = teachingAreaService.list();
        return Result.success(teachingAreas);
    }

    @PostMapping("postTeachingArea")
    public Result PostTeachingArea(@RequestParam("name") String name, @RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        TeachingArea teachingArea = new TeachingArea(0, name, lng, lat, radius);
        teachingAreaService.save(teachingArea);
        return Result.success(teachingArea.getId());
    }
    @DeleteMapping("deleteTeachingArea")
    public Result DeleteTeachingArea(@RequestParam("id") String id) {
        teachingAreaService.removeById(id);
        return Result.success();
    }
    @PutMapping("putTeachingArea")
    public Result putTeachingArea(@RequestParam("id") int id,@RequestParam("name") String name, @RequestParam("lng") Double lng, @RequestParam("lat") Double lat, @RequestParam("radius") Double radius) {
        TeachingArea teachingArea = new TeachingArea(id, name, lng, lat, radius);
        teachingAreaService.updateById(teachingArea);
        return Result.success();
    }
}

