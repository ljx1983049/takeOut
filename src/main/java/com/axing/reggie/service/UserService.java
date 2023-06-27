package com.axing.reggie.service;

import com.axing.reggie.common.R;
import com.axing.reggie.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    R<User> login(User user, HttpServletRequest request);

    User getUser(Long userId);

}
