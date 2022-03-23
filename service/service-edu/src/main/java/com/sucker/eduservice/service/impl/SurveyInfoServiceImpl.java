package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.eduservice.entity.OptionInfo;
import com.sucker.eduservice.entity.QuestionInfo;
import com.sucker.eduservice.entity.SurveyInfo;
import com.sucker.eduservice.entity.vo.NewSurveyVo;
import com.sucker.eduservice.mapper.SurveyInfoMapper;
import com.sucker.eduservice.service.OptionInfoService;
import com.sucker.eduservice.service.QuestionInfoService;
import com.sucker.eduservice.service.SurveyInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //创建一个新的问卷
    @Override
    public List<NewSurveyVo> newSurvey(String userId) {
        // 1. 首先创建一个调查问卷
        SurveyInfo surveyInfo = new SurveyInfo();
        surveyInfo.setCreatorId(userId);
        baseMapper.insert(surveyInfo);

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
    public String getSurveyIdByUserId(String userId) {

        SurveyInfo surveyInfo = baseMapper.selectOne(new QueryWrapper<SurveyInfo>().eq("creator_id", userId));
        return surveyInfo.getId();
    }
}
