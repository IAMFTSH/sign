package sign.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sign.common.contant.ProjectConstant;
import sign.common.result.Result;
import sign.entity.ClassTime;
import sign.entity.Sign;
import sign.entity.TeachingArea;
import sign.service.ClassTimeService;
import sign.service.SignService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 签到信息 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    SignService signService;

    @Autowired
    ClassTimeService classTimeService;

    @PostMapping("sign")
    public Result PostTeachingArea(@RequestParam("classTimeId") int classTimeId, @RequestParam("studentId") int studentId, @RequestParam("signId") int signId) {
        ClassTime classTime = classTimeService.getById(classTimeId);
        Sign sign = signService.getById(signId);
        LocalDateTime now = LocalDateTime.now();
        if (sign != null) {
            return Result.success("请勿重复签到");
        } else {
            int state = 0;
            if (now.isBefore(classTime.getBeginTime())) {
                return Result.success("签到尚未开始");
            }
            if (now.isAfter(classTime.getBeginTime()) && now.isBefore(classTime.getLateTime())) {
                state = 3;
            }
            if (now.isAfter(classTime.getLateTime()) && now.isBefore(classTime.getDeadline())) {
                state = 2;
            }
            if (now.isAfter(classTime.getDeadline())) {
                state = 1;
            }
            sign = new Sign();
            sign.setClassTimeId(classTimeId);
            sign.setState(state);
            sign.setStudentId(studentId);
            signService.save(sign);
            return Result.success(sign);
        }
    }

    @PostMapping(value = "fileUpload")
    public Result fileUpload(@RequestParam(value = "file") MultipartFile file, @RequestParam("signId") int signId, @RequestParam("reason") String reason) {
        if (file.isEmpty()) {
            Result.success("图片上传失败，请稍后再试");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = ProjectConstant.REASON_IMAGE_PATH; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sign sign = signService.getById(signId);
        if (sign.getImageAddress().length() > 0) {
            File del = new File(filePath + sign.getImageAddress());
            if (del.exists() && del.isFile()) {
                del.delete();
            }
        }
        sign.setReason(reason);
        sign.setImageAddress(fileName);
        signService.updateById(sign);
        return Result.success();
    }
}

