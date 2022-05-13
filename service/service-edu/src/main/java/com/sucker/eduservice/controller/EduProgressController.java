package com.sucker.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.commonutils.R;
import com.sucker.eduservice.aliyunRTC.App;
import com.sucker.eduservice.entity.EduProgress;
import com.sucker.eduservice.service.EduProgressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 诊疗过程表 前端控制器
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
@Api(description = "诊疗过程管理")
@RestController
@RequestMapping("/eduservice/eduProgress")
@CrossOrigin
public class EduProgressController {

    @Autowired
    private EduProgressService eduProgressService;

    /**
     *
     * @param userId
     * @param progress
     * @return
     */
    @ApiOperation(value = "新增诊疗过程",notes = "详细描述：若病人没有创建过则根据userId新建诊疗过程,需传入诊疗过程字符串")
    @PostMapping("newProgress/{userId}")
    public R newProgress(@ApiParam(value = "userId") @PathVariable String userId,
                         @ApiParam(value = "诊疗过程字符串") String progress){

        //判断是否已经存在诊疗过程表
        int count = eduProgressService.count(new QueryWrapper<EduProgress>().eq("progress_id", userId));
        if(count>0) return R.error().message("新建失败！已存在诊疗过程！");

        EduProgress eduProgress = new EduProgress();
        eduProgress.setProgressId(userId);
        eduProgress.setProgress(progress);
        boolean save = eduProgressService.save(eduProgress);
        if(save) return R.ok().message("新建诊疗过程成功!");
        else return R.error().message("新建诊疗过程失败!");
    }

    /**
     * 查询诊疗过程
     * @param userId
     * @return
     */
    @ApiOperation(value = "查询诊疗过程",notes = "详细描述：根据userId查询诊疗过程")
    @GetMapping("getProgressById/{userId}")
    public R getProgressById(@ApiParam(value = "userId") @PathVariable String userId){
        EduProgress progress = null;
        try {
            progress = eduProgressService.getOne(new QueryWrapper<EduProgress>().eq("user_id",userId));
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("查询失败！");
        }
        return R.ok().data("progress",progress);
    }


    /**
     * 修改诊疗过程
     * @param userId
     * @param progress
     * @return
     */
    @ApiOperation(value = "修改诊疗过程",notes = "详细描述：若病人已经创建过，需要修改，则根据userId修改诊疗过程，需传入诊疗过程字符串")
    @PostMapping("updateProgressById/{userId}")
    public R updateProgressById(@ApiParam(value = "userId") @PathVariable String userId,
                                @ApiParam(value = "诊疗过程字符串") @RequestBody String progress){
        EduProgress eduProgress = new EduProgress();
        eduProgress.setProgressId(userId);
        eduProgress.setProgress(progress);
        System.out.println(progress);
        boolean update = eduProgressService.update(eduProgress, new QueryWrapper<EduProgress>().eq("user_id", userId));
        if(update) return R.ok().message("修改成功!");
        else return R.error().message("修改失败!");


    }



    @ApiOperation(value = "查询所有诊疗过程",notes = "详细描述：根据teacherId查询所有诊疗过程")
    @GetMapping("findAllByTeacherId/{teacherId}")
    public R findAll(@ApiParam(value = "老师Id") @PathVariable String teacherId){
        List<EduProgress> allProgress = eduProgressService.findAll(teacherId);
        if(allProgress.size()>0) return R.ok().data("size",allProgress.size()).data("allProgress",allProgress);
        return R.error().message("没有相关记录或其他异常！");
    }

}

