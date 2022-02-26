package com.sucker.eduservice.controller;

import com.sucker.commonutils.R;
import com.sucker.eduservice.entity.EduProgress;
import com.sucker.eduservice.entity.EduTeacher;
import com.sucker.eduservice.entity.EduUser;
import com.sucker.eduservice.entity.vo.LoginVo;
import com.sucker.eduservice.entity.vo.RegisterVo;
import com.sucker.eduservice.service.EduLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "登录注册管理")
@RestController
@RequestMapping("/eduservice/eduLogin")
@CrossOrigin
public class EduLoginController {

    @Autowired
    private EduLoginService eduLoginService;

    //登录
    @ApiOperation(value = "登录功能")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String id = eduLoginService.login(loginVo);
        return R.ok().data("id",id).message("登录成功");
    }

    //登录Test
    @ApiOperation(value = "登录Test")
    @PostMapping("loginTest")
    public R loginTest(@RequestBody LoginVo loginVo, @RequestBody(required = false)EduTeacher  eduTeacher,
                       @RequestBody(required = false)EduUser eduUser , @RequestBody(required = false)EduProgress eduProgress){
        eduLoginService.loginTest(loginVo);
        return R.ok().message("登录成功");
    }

    //register
    @ApiOperation(value = "注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        eduLoginService.register(registerVo);
        return R.ok();
    }



}
