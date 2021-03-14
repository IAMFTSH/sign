package sign.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import sign.common.contant.ProjectConstant;
import sign.common.result.Result;
import sign.entity.ClassTime;
import sign.entity.Sign;
import sign.entity.VO.NumericalVo;
import sign.service.ClassTimeService;
import sign.service.SignService;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    @PreAuthorize("hasAnyAuthority('1')")
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

    @GetMapping("getSignList")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result getSignList(@RequestParam("classTimeId") int classTimeId, @RequestParam("username") String username, @RequestParam("name") String name, @RequestParam("state") int state) {
        Page page = new Page();
        signService.selectSignList(page, classTimeId, username, name, state);
        return Result.success(page);
    }

    @PutMapping("putState")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result putState(@RequestParam("signId") int signId,  @RequestParam("state") int state) {
        Sign sign = signService.getById(signId);
        sign.setState(state);
        signService.updateById(sign);
        return Result.success(sign);
    }

    @GetMapping("getNumerical")
    @PreAuthorize("hasAnyAuthority('1','2')")
    public Result getNumerical(@RequestParam("courseId") int courseId) {
        List<NumericalVo> numerical = signService.numerical(courseId);
        return Result.success(numerical);
    }

    @GetMapping("getImage")
    public Result getImage(@RequestParam("imageAddress") String imageAddress) throws IOException {
        FileInputStream inStream = null;
        inStream = new FileInputStream(ProjectConstant.REASON_IMAGE_PATH + imageAddress);


//byte数组用于存放图片字节数据
        byte[] buff = new byte[0];
        buff = new byte[inStream.available()];

        inStream.read(buff);
        inStream.close();

//设置发送到客户端的响应内容类型
        Base64 encoder = new Base64();

        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encodeToString(buff);


        return Result.success(base64Img);
    }
}

