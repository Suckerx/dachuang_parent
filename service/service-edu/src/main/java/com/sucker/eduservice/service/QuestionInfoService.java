package com.sucker.eduservice.service;

import com.sucker.eduservice.entity.QuestionInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 调查问卷问题主表 服务类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
public interface QuestionInfoService extends IService<QuestionInfo> {

    //查询问题表中所有问题
    List<QuestionInfo> selectAllQuestions();

}
