package com.sucker.eduservice.controller;


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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.apache.ibatis.javassist.bytecode.annotation.IntegerMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ApiOperation(value = "新建调查问卷")
    @GetMapping("newSurvey/{userId}")
    public R newSurvey(@PathVariable String userId){

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
    @ApiOperation(value = "根据userId保存问卷答案")
    @PostMapping("saveAnswer/{userId}")
    public R saveAnswer(@PathVariable String userId,@RequestBody Map<Integer,String> answerMap){

        //根据userId查询问卷主表id
        String surveyId = surveyInfoService.getSurveyIdByUserId(userId);

        //创建问卷答案集合
        List<AnswerInfo> newSurveyVoList = new ArrayList<>();

        //遍历map集合获得每个问题id和答案
        Set<Integer> questionIds = answerMap.keySet();
        for (Integer questionId : questionIds) {
            AnswerInfo answerInfo = new AnswerInfo();
            answerInfo.setQuestionId(questionId);
            answerInfo.setUserId(userId);
            answerInfo.setSurveyId(surveyId);
            //保存答案
            answerInfo.setAnswerId(answerMap.get(questionId));
            newSurveyVoList.add(answerInfo);
        }

        try {
            answerInfoService.saveBatch(newSurveyVoList);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("保存数据失败！");
        }

        return R.ok();
    }


    /**
     * 根据userId查询问卷答案
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据userId查询问卷答案")
    @GetMapping("getAnswerByUserId/{userId}")
    public R getAnswerByUserId(@PathVariable String userId){
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

