package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.eduservice.entity.AnswerInfo;
import com.sucker.eduservice.mapper.AnswerInfoMapper;
import com.sucker.eduservice.service.AnswerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户答案表 服务实现类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Service
public class AnswerInfoServiceImpl extends ServiceImpl<AnswerInfoMapper, AnswerInfo> implements AnswerInfoService {

    //根据userId查询问卷答案
    //通过问题id排序
    @Override
    public List<AnswerInfo> getAnswerByUserId(String userId) {
        List<AnswerInfo> answerInfoList = baseMapper.selectList(new QueryWrapper<AnswerInfo>().eq("user_id", userId).orderByAsc("question_id"));
        return answerInfoList;
    }
}
