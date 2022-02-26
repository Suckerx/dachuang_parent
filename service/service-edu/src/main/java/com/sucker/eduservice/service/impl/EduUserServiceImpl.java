package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.eduservice.entity.EduTeacher;
import com.sucker.eduservice.entity.EduUser;
import com.sucker.eduservice.entity.vo.LoginVo;
import com.sucker.eduservice.mapper.EduUserMapper;
import com.sucker.eduservice.service.EduTeacherService;
import com.sucker.eduservice.service.EduUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sucker.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import sun.security.provider.MD5;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
@Service
public class EduUserServiceImpl extends ServiceImpl<EduUserMapper, EduUser> implements EduUserService {

}
