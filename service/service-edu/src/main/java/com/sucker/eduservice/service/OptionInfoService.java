package com.sucker.eduservice.service;

import com.sucker.eduservice.entity.OptionInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 调查问卷问题选项主表 服务类
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
public interface OptionInfoService extends IService<OptionInfo> {

    //根据问题id查询问题对应选项
    List<OptionInfo> selectAllOptionsList(Integer question_id);

}
