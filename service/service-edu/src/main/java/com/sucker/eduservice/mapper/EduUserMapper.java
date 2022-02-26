package com.sucker.eduservice.mapper;

import com.sucker.eduservice.entity.EduUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
@Mapper
public interface EduUserMapper extends BaseMapper<EduUser> {

}
