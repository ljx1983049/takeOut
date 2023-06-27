package com.axing.reggie.web.controller;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.User;
import com.axing.reggie.service.UserService;
import com.axing.reggie.utils.MailUtils;
import com.axing.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody User user,HttpServletRequest request){
        R<User> r = userService.login(user,request);
        return r;
    }

    /**
     * 发送验证码
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpServletRequest request){
        // 获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            //手机号不为空
            //生成随机的6位验证码
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            log.info("生成的验证码{}",code);
            //发送邮件
            MailUtils.sendMail(phone,"你的验证码为 "+code+"，请妥善保管，2分钟后失效","菩提阁验证码");

            //将生成的验证码保存到session
            // request.getSession().setAttribute(phone,code);
            //将生成的验证码保存到redis中,并设置有效时间2分钟
            stringRedisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);

            return R.success("发送验证码成功");
        }
        return R.error("发送验证码失败");
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    public R<String> loginout(HttpServletRequest request){
        //清除用户session
        request.getSession().removeAttribute("userId");
        return R.success("用户退出成功");
    }







}
