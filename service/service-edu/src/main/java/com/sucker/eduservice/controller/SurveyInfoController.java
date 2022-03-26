package com.sucker.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.commonutils.R;
import com.sucker.eduservice.entity.AnswerInfo;
import com.sucker.eduservice.entity.OptionInfo;
import com.sucker.eduservice.entity.QuestionInfo;
import com.sucker.eduservice.entity.SurveyInfo;
import com.sucker.eduservice.entity.vo.NewSurveyVo;
import com.sucker.eduservice.service.AnswerInfoService;
import com.sucker.eduservice.service.OptionInfoService;
import com.sucker.eduservice.service.QuestionInfoService;
import com.sucker.eduservice.service.SurveyInfoService;
import com.sucker.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.User;
import org.apache.ibatis.javassist.bytecode.annotation.IntegerMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.*;

/**
 * <p>
 * 调查问卷主表
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */

@Api(description = "调查问卷管理")
@RestController
@RequestMapping("/eduservice/surveyInfo")
@CrossOrigin
public class SurveyInfoController {

    @Autowired
    private SurveyInfoService surveyInfoService;

    @Autowired
    private AnswerInfoService answerInfoService;


    /**
     * 新建调查问卷，同时查询出所有问题即对应选项
     * @param userId
     * @return
     */
    @ApiOperation(value = "新建调查问卷",notes = "详细介绍：根据用户id创建一个调查问卷，返回所有问题的描述和对应选项描述的List集合")
    @GetMapping("newSurvey/{userId}")
    public R newSurvey(@ApiParam(value = "用户id",required = true)@PathVariable String userId){

        List<NewSurveyVo> list = surveyInfoService.newSurvey(userId);

        return R.ok().data("list",list);
    }


    /**
     * 根据userid保存问卷答案
     * map集合存储每个问题对应答案
     * @param userId
     * @param answerMap
     * @return
     */
    @ApiOperation(value = "根据userId保存问卷答案",notes = "详细描述：传入userId和一个map集合，通过map存储问题的id和对应答案的id（答案id是用String）")
    @PostMapping("saveAnswer/{userId}")
    public R saveAnswer(@ApiParam(value = "用户id",required = true)@PathVariable String userId,
                        @ApiParam(value = "Map<问题id(int),答案id(String)>",required = true)@RequestBody Map<Integer,String> answerMap){

        //根据userId查询问卷主表id
        String surveyId = null;
        try {
            surveyId = surveyInfoService.getSurveyIdByUserId(userId);
            if(surveyId.equals("-1") || surveyId == null) return R.error().message("查无此人！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("查无此人！");
        }

        boolean flag = surveyInfoService.saveAnswer(userId, answerMap,surveyId);
        if(flag) return R.ok();
        return R.error();
    }


    /**
     * 根据userId查询问卷答案
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据userId查询问卷答案",notes = "详细描述：传入userId，返回答案List集合，集合中每一个元素是一个答案对象AnswerInfo，详情查看Model中对象介绍")
    @GetMapping("getAnswerByUserId/{userId}")
    public R getAnswerByUserId(@ApiParam(value = "用户id",required = true)@PathVariable String userId){
        List<AnswerInfo> answerByUserIdList = answerInfoService.getAnswerByUserId(userId);
        return R.ok().data("answerByUserIdList",answerByUserIdList);
    }



    @ApiOperation(value = "没什么用，为了swagger显示所有实体类")
    @GetMapping("aaaa")
    public R getAllModel(@RequestBody(required = false) SurveyInfo surveyInfo,
                         @RequestBody(required = false) OptionInfo optionInfo,
                         @RequestBody(required = false) QuestionInfo questionInfo,
                         @RequestBody(required = false)AnswerInfo answerInfo){
        return R.ok();
    }

}

