package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.eduservice.entity.OptionInfo;
import com.sucker.eduservice.mapper.OptionInfoMapper;
import com.sucker.eduservice.service.OptionInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.management.Query;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 调查问卷问题选项主表 服务实现类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Service
public class OptionInfoServiceImpl extends ServiceImpl<OptionInfoMapper, OptionInfo> implements OptionInfoService {

    //根据问题id查询问题对应选项
    @Override
    public List<OptionInfo> selectAllOptionsList(Integer question_id) {
        //通过问题查找对应问题所有选项
        QueryWrapper<OptionInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id",question_id);
        //通过排序字段排序ABC
        wrapper.orderByAsc("option_sort");
        List<OptionInfo> optionInfoList = baseMapper.selectList(wrapper);
        return optionInfoList;
    }




}
