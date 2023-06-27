package com.axing.reggie.service.impl;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.User;
import com.axing.reggie.mapper.UserMapper;
import com.axing.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public R<User> login(User user, HttpServletRequest request) {
        //获取用户输入账号（本来是手机这里用邮箱）
        String phone = user.getPhone();
        //查询是否是新用户
        User u = userMapper.newUser(phone);
        if (u == null){
            //是新用户
            log.info("是新用户");
            //验证验证码是否正确
            String code = user.getPassword();//用户输入的验证码

            //取session中的验证码
            // String _code = (String) request.getSession().getAttribute(phone);
            //从redis中取得验证码
            String _code = stringRedisTemplate.opsForValue().get(phone);

            if (!code.equals(_code)){
                return R.error("验证码错误");
            }
            //验证码正确，登录成功，创建用户，成为老用户，默认密码123456
            u=new User();
            // request.getSession().removeAttribute(phone);//验证完成后销毁session
            stringRedisTemplate.delete(phone);//销毁redis的验证码
            String password = DigestUtils.md5DigestAsHex("123456".getBytes());
            u.setPassword(password);//设置密码
            u.setPhone(user.getPhone());//设置账号
            u.setName("昵称10086");//设置名称
            userMapper.addUser(u);
        }else {
            //不是新用户
            log.info("不是新用户");
            //验证密码是否正确
            //用户输入的密码
            String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            if (!password.equals(u.getPassword())) {
                //用户输入的密码与数据库密码不一样
                log.info("密码错误");
                return R.error("密码错误");
            }
        }
        //登录成功
        request.getSession().setAttribute("userId",u.getId());//将用户id存入session
        return R.success(u);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public User getUser(Long userId) {
        return userMapper.getUserById(userId);
    }
}
