package com.sucker.eduservice.controller;

import com.sucker.commonutils.R;
import com.sucker.eduservice.aliyunRTC.LoginHandle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 音视频控制
 * </p>
 *
 * @author sucker
 * @since 2022-04-13
 */
@Api(tags = "音视频管理")
@RestController
@RequestMapping("/eduservice/eduRTC")
@CrossOrigin
public class RtcController {

    @ApiOperation(value = "音视频通话")
    @PostMapping("")
    public R aliyunRTC(){
        R httpServer = LoginHandle.createHttpServer();
        return httpServer;
    }

}
