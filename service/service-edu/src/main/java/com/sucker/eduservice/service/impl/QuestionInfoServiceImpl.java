package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.eduservice.entity.QuestionInfo;
import com.sucker.eduservice.mapper.QuestionInfoMapper;
import com.sucker.eduservice.service.QuestionInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 调查问卷问题主表 服务实现类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Service
public class QuestionInfoServiceImpl extends ServiceImpl<QuestionInfoMapper, QuestionInfo> implements QuestionInfoService {

    //查询问题表中所有问题
    @Override
    public List<QuestionInfo> selectAllQuestions() {
        QueryWrapper<QuestionInfo> wrapper = new QueryWrapper<>();
        //根据问题id升序
        wrapper.orderByAsc("id");
        return baseMapper.selectList(wrapper);
    }
}
