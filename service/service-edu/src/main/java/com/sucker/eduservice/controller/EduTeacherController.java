package com.sucker.eduservice.controller;


import com.sucker.commonutils.R;
import com.sucker.eduservice.entity.EduTeacher;
import com.sucker.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 咨询师表 前端控制器
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
@Api(description = "咨询师管理")
@RestController
@RequestMapping("/eduservice/eduTeacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //查询所有老师信息
    @ApiOperation(value = "查询所有讲师列表")
    @GetMapping("findAll")
    public R list(){
        List<EduTeacher> list = teacherService.list(null);
        for (int i = 0; i < list.size(); i++) {
            EduTeacher teacher = list.get(i);
            teacher.setPassword("xxxxxx");
            list.set(i,teacher);
        }
        return R.ok().data("items",list);
    }

    //根据老师id获取老师信息
    @ApiOperation(value = "根据老师id获取老师信息")
    @GetMapping("getTeacherInfo/{teacherId}")
    public R getTeacherInfo(@PathVariable String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);
        teacher.setPassword("xxxxxxx");
        return R.ok().data("teacher",teacher);
    }


}

