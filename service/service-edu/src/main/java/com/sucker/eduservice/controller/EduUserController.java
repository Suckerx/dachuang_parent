package com.sucker.eduservice.controller;


import com.sucker.commonutils.R;
import com.sucker.eduservice.entity.EduUser;
import com.sucker.eduservice.service.EduUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/eduservice/eduUser")
@CrossOrigin
public class EduUserController {

    @Autowired
    private EduUserService eduUserService;

    //根据用户id查询用户信息
    @ApiOperation(value = "根据用户id查询用户信息")
    @GetMapping("getUserInfo/{userId}")
    public R getUserInfo(@PathVariable String userId){
        EduUser user = eduUserService.getById(userId);
        user.setPassword("xxxxxxxx");
        return R.ok().data("user",user);
    }


}

