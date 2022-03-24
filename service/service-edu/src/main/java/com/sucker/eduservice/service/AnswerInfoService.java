package com.sucker.eduservice.service;

import com.sucker.eduservice.entity.AnswerInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户答案表 服务类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
public interface AnswerInfoService extends IService<AnswerInfo> {

    //根据userId查询问卷答案
    List<AnswerInfo> getAnswerByUserId(String userId);
}
