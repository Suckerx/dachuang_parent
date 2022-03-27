package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.commonutils.R;
import com.sucker.eduservice.entity.AnswerInfo;
import com.sucker.eduservice.entity.OptionInfo;
import com.sucker.eduservice.entity.QuestionInfo;
import com.sucker.eduservice.entity.SurveyInfo;
import com.sucker.eduservice.entity.vo.NewSurveyVo;
import com.sucker.eduservice.mapper.SurveyInfoMapper;
import com.sucker.eduservice.service.AnswerInfoService;
import com.sucker.eduservice.service.OptionInfoService;
import com.sucker.eduservice.service.QuestionInfoService;
import com.sucker.eduservice.service.SurveyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sucker.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.security.jgss.GSSExceptionImpl;

import java.util.*;

/**
 * <p>
 * 调查问卷主表 服务实现类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Service
public class SurveyInfoServiceImpl extends ServiceImpl<SurveyInfoMapper, SurveyInfo> implements SurveyInfoService {

    @Autowired
    private QuestionInfoService questionInfoService;

    @Autowired
    private OptionInfoService optionInfoService;

    @Autowired
    private AnswerInfoService answerInfoService;


    //创建一个新的问卷
    @Override
    public List<NewSurveyVo> newSurvey(String userId) {
        // 1. 首先创建一个调查问卷
        //先判断是否填过！
        List<SurveyInfo> surveyInfoList = baseMapper.selectList(new QueryWrapper<SurveyInfo>().eq("creator_id", userId));
        SurveyInfo surveyInfo = new SurveyInfo();
        surveyInfo.setCreatorId(userId);
        if(surveyInfoList.size()!=0){
            baseMapper.update(surveyInfo,new QueryWrapper<SurveyInfo>().eq("creator_id", userId));
        }else{
            baseMapper.insert(surveyInfo);
        }

        // 2.查询问卷所有问题
        List<QuestionInfo> questionInfos = questionInfoService.selectAllQuestions();

        // 3.根据问题id查询每个问题对应选项
        List<NewSurveyVo> list = new ArrayList<>();

        questionInfos.forEach(questionInfo -> {
            NewSurveyVo newSurveyVo = new NewSurveyVo();
            newSurveyVo.setQuestionInfo(questionInfo);
            //查找每个问题对应选项集合
            List<OptionInfo> optionInfos = optionInfoService.selectAllOptionsList(questionInfo.getId());
            //封装数据
            newSurveyVo.setOptionInfoList(optionInfos);
            list.add(newSurveyVo);
        });
        return list;
    }

    //根据userId查询问卷主表id
    @Override
    public String getSurveyIdByUserId(String userId){

        //查到一个调查问卷的信息
        List<SurveyInfo> surveyInfoList = baseMapper.selectList(new QueryWrapper<SurveyInfo>().eq("creator_id", userId));
        if(surveyInfoList.size()==0) return "-1";
        return surveyInfoList.get(0).getId();
    }

    @Override
    public boolean saveAnswer(String userId, Map<Integer, String> answerMap,String surveyId) {

        if(answerMap.isEmpty()) throw new GuliException(20001,"请填写答案再提交！");
        //记得判断size是否等于问题数量
        //if(answerMap.size()!=)

        //查询以前是否填过，若填过则删除
        List<AnswerInfo> answerByUserIdList = answerInfoService.getAnswerByUserId(userId);
        if(!ObjectUtils.isEmpty(answerByUserIdList)) {
            answerInfoService.remove(new QueryWrapper<AnswerInfo>().eq("user_id",userId));
        }


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
            //如果不存在就保存，存在就更新

            answerInfoService.saveBatch(newSurveyVoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"保存数据失败！");
        }
        return true;
    }
}
