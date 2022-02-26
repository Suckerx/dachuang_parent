package com.sucker.eduservice.service;

import com.sucker.eduservice.entity.vo.LoginVo;
import com.sucker.eduservice.entity.vo.RegisterVo;

public interface EduLoginService {

    //登录
    String login(LoginVo loginVo);
    //登录Test
    void loginTest(LoginVo loginVo);

    //注册
    void register(RegisterVo registerVo);
}
