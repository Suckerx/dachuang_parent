package com.sucker.eduservice.service;

import com.sucker.eduservice.entity.OptionInfo;
import com.sucker.eduservice.entity.QuestionInfo;
import com.sucker.eduservice.entity.SurveyInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sucker.eduservice.entity.vo.NewSurveyVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 调查问卷主表 服务类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
public interface SurveyInfoService extends IService<SurveyInfo> {

    //创建一个新的问卷
    List<NewSurveyVo> newSurvey(String userId);

    //根据userId查询问卷主表id
    String getSurveyIdByUserId(String userId);

}
