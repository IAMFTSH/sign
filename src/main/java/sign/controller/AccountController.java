package sign.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import sign.common.contant.ProjectConstant;
import sign.common.contant.SecurityConstant;
import sign.common.result.Result;
import sign.common.result.ResultCode;
import sign.common.util.StringUtils;
import sign.config.util.JWTUtils;
import sign.entity.Account;
import sign.entity.Sign;
import sign.entity.StudentCourse;
import sign.entity.VO.CaptchaVO;
import sign.entity.VO.OpenId;
import sign.service.*;
import sign.service.impl.CaptchaService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author 邝明山
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountService accountService;
    @Autowired
    private DefaultKaptcha producer;

    @Autowired
    CourseService courseService;
    @Autowired
    SignService signService;
    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    HttpClient httpClient;

    @Autowired
    private CaptchaService captchaService;


    @PostMapping("toLogin")
    public Result login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "imageCode") String imageCode, @RequestParam(value = "captchaKey") String captchaKey) throws Exception {
        String str = (String) redisTemplate.opsForValue().get("captcha:verification:".concat(captchaKey));
        redisTemplate.delete("captcha:verification:".concat(captchaKey));
/*        if (StringUtils.isBlank(str)) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "图片验证码已过期");
        }
        if (!str.toLowerCase().equals(imageCode.toLowerCase())) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "图片验证码不匹配");
        }*/
/*        Collection<? extends GrantedAuthority> authorities;
        try {
            //这里使用authenticationManager验证，最终还会用到Config中设置的userDetailsService的loadUserByUsername方法
            //也可以直接用userDetailsService进行验证，反正只是为了封装JWT信息
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            authorities = authenticationManager.authenticate(token).getAuthorities();
        } catch (AuthenticationException e) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "账户密码不匹配");
        }*/
        Account account = accountService.accountSelectOne("username", username);
        if (!passwordEncoder.matches(password, account.getPassword())) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "账户密码不匹配");
        }
        UserDetails userDetails = User.builder().username(account.getUsername()).password(account.getPassword()).authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(Integer.toString(account.getAuthority()))).build();
        String jwt = jwtUtils.createToken(userDetails);
        redisTemplate.opsForValue().set("jwt:" + username, jwt, 30 * 60, TimeUnit.SECONDS);
        return Result.success(jwt);
    }

    /*    @ApiOperation("这个无法通过swagger查看，请直接使用html中src查看，例如<img  src=\"http://IP地址:端口号/IMAGEVALIDATE\">")
        @GetMapping(SecurityConstant.IMAGE_VALIDATE)
        public void   ImageValidateByStream(HttpServletRequest request,HttpServletResponse response) throws IOException {
            //用我们的验证码类，生成验证码类对象
            ImageVerificationCode ivc = new ImageVerificationCode();
            BufferedImage image = ivc.getImage();
            //将验证码的文本存在session中
            request.getSession().setAttribute("imageCode", ivc.getText());
            ImageVerificationCode.output(image, response.getOutputStream());
        }*/
    @ApiOperation("查看图片：请直接使用html中src查看，例如<img src=\"data:image/jpeg;base64,base64 编码的 jpeg 图片数据\" />")
    @GetMapping("IMAGEVALIDATE")
    public Result getCaptcha() throws IOException {
        // 生成文字验证码
        String content = producer.createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        BufferedImage image = producer.createImage(content);

        outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray()).replace("\n", "").replace("\r", "");

        CaptchaVO captchaVO = captchaService.cacheCaptcha(content);
        captchaVO.setBase64Img(base64Img);

        return Result.success(captchaVO);
    }

    @GetMapping("putAccountPassword")
    public Result putAccountPassword(@RequestParam("password") String password, @RequestParam("username") String username, @RequestParam("phone") String phone, @RequestParam(value = "imageCode") String imageCode, @RequestParam(value = "captchaKey") String captchaKey) {
        String str = (String) redisTemplate.opsForValue().get("captcha:verification:".concat(captchaKey));
        redisTemplate.delete("captcha:verification:".concat(captchaKey));
/*        if (StringUtils.isBlank(str)) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "图片验证码已过期");
        }
        if (!str.toLowerCase().equals(imageCode.toLowerCase())) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "图片验证码不匹配");
        }*/
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        queryWrapper.eq("phone", phone);
        Account account = accountService.getOne(queryWrapper);
        if (account == null) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "学号与手机号不匹配 ");
        }
        account.setPassword(passwordEncoder.encode(password));
        accountService.updateById(account);
        redisTemplate.opsForValue().set("account:" + account.getUsername(), account, 1, TimeUnit.DAYS);
        return Result.success();
    }

    @PutMapping("putAccountPasswordByOldPassword")
    public Result putAccountPasswordByOldPassword(@RequestParam("password") String password, @RequestParam("oldPassword") String oldPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        Account account = accountService.getOne(queryWrapper);
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            return Result.error(ResultCode.UNAUTHORIZED.getCode(), "旧密码与工号不匹配");
        }
        account.setPassword(passwordEncoder.encode(password));
        accountService.updateById(account);
        UserDetails userDetails = User.builder().username(account.getUsername()).password(account.getPassword()).authorities(AuthorityUtils.commaSeparatedStringToAuthorityList(Integer.toString(account.getAuthority()))).build();
        String jwt = jwtUtils.createToken(userDetails);
        account.setOpenId(null);
        redisTemplate.opsForValue().set("account:" + account.getUsername(), account, 1, TimeUnit.DAYS);
        redisTemplate.opsForValue().set("jwt:" + username, jwt, 30 * 60, TimeUnit.SECONDS);
        return Result.success();
    }

    @PutMapping("putAccountByMyself")
    public Result putAccountByMyself(@RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("phone") String phone) {
        String oldUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", oldUsername);
        Account account = accountService.getOne(queryWrapper);
        account.setName(name);
        account.setUsername(username);
        account.setPhone(phone);
        accountService.updateById(account);
        UserDetails userDetails = User.builder().username(account.getUsername()).password(account.getPassword()).authorities(account.getAuthority().toString()).build();
        String jwt = jwtUtils.createToken(userDetails);
        redisTemplate.opsForValue().set("jwt:" + account.getUsername(), jwt, 30 * 60, TimeUnit.SECONDS);
        account.setOpenId(null);
        redisTemplate.opsForValue().set("account:" + account.getUsername(), account, 1, TimeUnit.DAYS);
        return Result.success(jwt);
    }

    @GetMapping("WCLogin")
    public Result WCLogin(@RequestParam("code") String code) {
        String appId = ProjectConstant.APP_ID;
        String appSecret = ProjectConstant.APP_SECRET;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        String body = httpClient.client(url, HttpMethod.GET, null);
        OpenId openId = JSON.toJavaObject((JSON) JSONObject.parse(body), OpenId.class);
        Account account = accountService.accountSelectOne("open_id", openId.getOpenid());
        String jwt = "";
        if (account != null) {
            UserDetails userDetails = User.builder().username(account.getUsername()).password(account.getPassword()).authorities(account.getAuthority().toString()).build();
            jwt = jwtUtils.createToken(userDetails);
            redisTemplate.opsForValue().set("jwt:" + account.getUsername(), jwt, 30 * 60, TimeUnit.SECONDS);
            account.setOpenId(null);
            account.setPassword(null);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("account", account);
        map.put("Authentication", jwt);
        return Result.success(map);
    }

    @PostMapping("WCRegister")
    public Result WCRegister(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam("code") String code, @RequestParam(value = "phone") String phone, @RequestParam(value = "name") String name) {
        String appId = ProjectConstant.APP_ID;
        String appSecret = ProjectConstant.APP_SECRET;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        String body = httpClient.client(url, HttpMethod.GET, null);
        OpenId openId = JSON.toJavaObject((JSON) JSONObject.parse(body), OpenId.class);
        String encode = passwordEncoder.encode(password);
        Account account = accountService.accountSelectOne("open_id", openId.getOpenid());
        if (account == null) {
            account = new Account(0, username, encode, openId.getOpenid(), 3, name, phone);
            accountService.save(account);
        } else {
            redisTemplate.delete("jwt:" + account.getUsername());
            redisTemplate.delete("account:" + account.getUsername());
            account = new Account(account.getId(), username, encode, openId.getOpenid(), 3, name, phone);
            accountService.updateById(account);

        }
        String jwt = "";
        UserDetails userDetails = User.builder().username(account.getUsername()).password(encode).authorities(account.getAuthority().toString()).build();
        jwt = jwtUtils.createToken(userDetails);
        redisTemplate.opsForValue().set("jwt:" + account.getUsername(), jwt, 30 * 60, TimeUnit.SECONDS);
        Map<String, Object> map = new HashMap<>();
        account.setOpenId(null);
        account.setPassword(null);
        map.put("account", account);
        map.put("Authentication", jwt);
        return Result.success(map);
    }

    @GetMapping("getAccountInformation")
    public Result getAccountInformation() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Account account = (Account) redisTemplate.opsForValue().get("account:" + username);
        account.setPassword(null);
        return Result.success(account);
    }

    @GetMapping("accountsBySomething")
    public Result accountsBySomething(String name, String username, String phone, int authority, @RequestParam("pageNum") int pageNum) {

        QueryWrapper queryWrapper = new QueryWrapper();
        //arg1  当前页    arg2   页大小
        if (name.length() != 0) {
            queryWrapper.like("name", name);
        }
        if (username.length() != 0) {
            queryWrapper.like("username", username);
        }
        if (phone.length() != 0) {
            queryWrapper.like("phone", phone);
        }
        if (authority != 0) {
            queryWrapper.eq("authority", authority);
        }
        queryWrapper.orderByAsc("id");
        Page<Account> page = new Page<>(pageNum, 10);
        accountService.page(page, queryWrapper);
        for (Account account : page.getRecords()) {
            account.setPassword(null);
            account.setOpenId(null);
        }
        return Result.success(page);
    }

    @PutMapping("putAccount")
    public Result putAccount(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("phone") String phone, @RequestParam("authority") int authority) {
        Account account = accountService.getById(id);
        redisTemplate.delete("jwt:" + account.getUsername());
        redisTemplate.delete("account:" + account.getUsername());
        account.setName(name);
        account.setUsername(username);
        account.setPhone(phone);
        account.setAuthority(authority);
        accountService.updateById(account);
        return Result.success();
    }

    @PostMapping("postAccount")
    public Result postAccount(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("phone") String phone, @RequestParam("authority") int authority) {
        Account account = new Account();
        account.setName(name);
        account.setUsername(username);
        account.setPhone(phone);
        account.setAuthority(authority);
        account.setPassword(passwordEncoder.encode("123456"));
        accountService.save(account);
        return Result.success(account.getId());
    }

    @DeleteMapping("deleteAccount")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteAccount(@RequestParam("id") String id) {
        Account account = accountService.getById(id);
        if (account.getAuthority() == 2) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("teacher_id", id);
            List list = courseService.list(queryWrapper);
            System.out.println(list);
            if (list.size() > 0) {
                return Result.success("该教师账号还有课程，无法删除");
            }
            accountService.removeById(id);
            return Result.success();
        } else if (account.getAuthority() == 3) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("student_id", id);
            List<Sign> signList = signService.list(queryWrapper);
            List signIdList = new ArrayList();
            if (signList.size() > 0) {
                for (Sign sign : signList) {
                    signIdList.add(sign.getId());
                }
                signService.removeByIds(signIdList);
            }

            studentCourseService.remove(queryWrapper);
            accountService.removeById(id);
            return Result.success();
        }
        accountService.removeById(id);
        return Result.success();
    }


    @PutMapping("initPassword")
    public Result initPassword(@RequestParam("id") String id) {
        Account account = accountService.getById(id);
        account.setPassword(passwordEncoder.encode("123456"));
        accountService.updateById(account);
        return Result.success();
    }
}

