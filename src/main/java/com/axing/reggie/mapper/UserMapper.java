package com.axing.reggie.mapper;

import com.axing.reggie.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 验证账号是否存在
     * @param phone
     * @return
     */
    User newUser(String phone);


    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    User getUserById(Long id);
}
