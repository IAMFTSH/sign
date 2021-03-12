package sign.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sign.common.result.Result;
import sign.entity.VO.ClassTimeAllInfoVo;
import sign.entity.VO.SignAndClassTimeAndClassroomAndTeachingAreaVo;
import sign.service.ClassTimeService;

import java.util.List;

/**
 * <p>
 * 上课时间信息 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/class_time")
public class ClassTimeController {
    @Autowired
    ClassTimeService classTimeService;

    @GetMapping("getSignAndClassTimeVoList")
    public Result getSignAndClassTimeVoList(@RequestParam("courseId") int courseId, @RequestParam("studentId") int studentId) {
        List<SignAndClassTimeAndClassroomAndTeachingAreaVo> signAndClassTimeAndClassroomAndTeachingAreaVoList = classTimeService.selectSignAndClassTimeVoList(studentId, courseId);
        return Result.success(signAndClassTimeAndClassroomAndTeachingAreaVoList);
    }

    @GetMapping("getClassTimeAllInfo")
    public Result getClassTimeAllInfo(@RequestParam("classTimeId") int classTimeId) {
        List<ClassTimeAllInfoVo> classTimeAllInfoVoList = classTimeService.selectClassTimeAllInfo(classTimeId);
        return Result.success(classTimeAllInfoVoList);
    }

}

