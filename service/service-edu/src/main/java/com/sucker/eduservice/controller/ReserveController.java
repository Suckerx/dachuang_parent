package com.sucker.eduservice.controller;


import com.sucker.commonutils.R;
import com.sucker.eduservice.entity.EduTeacher;
import com.sucker.eduservice.entity.Reserve;
import com.sucker.eduservice.service.ReserveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预约时间表 前端控制器
 * </p>
 *
 * @author sucker
 * @since 2022-04-28
 */
@Api(tags = "预约管理")
@RestController
@RequestMapping("/eduservice/reserve")
@CrossOrigin
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    //预约时间和房间号
    @ApiOperation(value = "预约时间",notes = "详细描述：传入预约对象，返回房间号")
    @PostMapping("reserve")
    public R reserve(@ApiParam(value = "预约对象") @RequestBody Reserve reserve){
        //查询共有几条记录
        int count = reserveService.count();
        //设置房间号
        reserve.setReserveId(count+1);
        //保存
        try {
            reserveService.save(reserve);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("预约失败！");
        }
        return R.ok().data("count",count+1).message("预约成功!");
    }

}

