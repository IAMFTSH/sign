package sign.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import sign.common.result.Result;
import sign.entity.ClassTime;
import sign.entity.VO.ClassTimeAllInfoVo;
import sign.entity.VO.SignAndClassTimeAndClassroomAndTeachingAreaVo;
import sign.service.ClassTimeService;
import sign.service.SignService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
    @Autowired
    SignService signService;
    @GetMapping("getSignAndClassTimeVoList")
    public Result getSignAndClassTimeVoList(@RequestParam("courseId") int courseId, @RequestParam("studentId") int studentId) {
        List<SignAndClassTimeAndClassroomAndTeachingAreaVo> signAndClassTimeAndClassroomAndTeachingAreaVoList = classTimeService.selectSignAndClassTimeVoList(studentId, courseId);
        return Result.success(signAndClassTimeAndClassroomAndTeachingAreaVoList);
    }

    @GetMapping("getClassTimeAllInfo")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result getClassTimeAllInfo(@RequestParam("pageNum") int pageNum, @RequestParam("courseId") int courseId) {
        Page page = new Page(pageNum, 10);
        classTimeService.selectClassTimeAllInfo(page, courseId);
        return Result.success(page);
    }

    @PostMapping("postClassTime")
    @PreAuthorize("hasAnyAuthority('1')")
    public Result postClassTime(@RequestParam("classroomId") int classroomId, @RequestParam("courseId") int courseId, @RequestParam("beginTime") long beginTime, @RequestParam("lateTime") long lateTime, @RequestParam("deadline") long deadline) {
        Date begin=new Date(beginTime);
        Date late=new Date(lateTime);
        Date dead=new Date(deadline);
        LocalDateTime beginTimeDate = LocalDateTime.ofInstant(begin.toInstant(), ZoneId.of("Asia/Shanghai"));
        LocalDateTime lateTimeDate = LocalDateTime.ofInstant(late.toInstant(), ZoneId.of("Asia/Shanghai"));
        LocalDateTime deadlineDate = LocalDateTime.ofInstant(dead.toInstant(), ZoneId.of("Asia/Shanghai"));
        System.out.println(beginTimeDate);
        ClassTime classTime = new ClassTime(0, classroomId, courseId, beginTimeDate, lateTimeDate, deadlineDate);
        classTimeService.save(classTime);
        ClassTimeAllInfoVo classTimeAllInfoVo = classTimeService.selectOneById(classTime.getId());
        return Result.success(classTimeAllInfoVo);
    }

    @PutMapping("putClassTime")
    @PreAuthorize("hasAnyAuthority('1')")
    public Result putClassTime(@RequestParam("classTimeId") int classTimeId,@RequestParam("classroomId") int classroomId, @RequestParam("courseId") int courseId, @RequestParam("beginTime") long beginTime, @RequestParam("lateTime") long lateTime, @RequestParam("deadline") long deadline)  {
        Date begin=new Date(beginTime);
        Date late=new Date(lateTime);
        Date dead=new Date(deadline);
        LocalDateTime beginTimeDate = LocalDateTime.ofInstant(begin.toInstant(), ZoneId.of("Asia/Shanghai"));
        LocalDateTime lateTimeDate = LocalDateTime.ofInstant(late.toInstant(), ZoneId.of("Asia/Shanghai"));
        LocalDateTime deadlineDate = LocalDateTime.ofInstant(dead.toInstant(), ZoneId.of("Asia/Shanghai"));
        System.out.println(beginTimeDate);
        ClassTime classTime = new ClassTime(classTimeId, classroomId, courseId, beginTimeDate, lateTimeDate, deadlineDate);
        classTimeService.save(classTime);
        ClassTimeAllInfoVo classTimeAllInfoVo = classTimeService.selectOneById(classTime.getId());
        return Result.success(classTimeAllInfoVo);
    }
    @DeleteMapping("deleteClassTime")
    @PreAuthorize("hasAnyAuthority('1')")
    @Transactional
    public Result deleteClassTime(@RequestParam("classTimeId") int classTimeId)  {
        QueryWrapper signQueryWrapper=new QueryWrapper();
        signQueryWrapper.eq("class_time_id",classTimeId);
        signService.remove(signQueryWrapper);
        classTimeService.removeById(classTimeId);
        return Result.success();
    }
}

