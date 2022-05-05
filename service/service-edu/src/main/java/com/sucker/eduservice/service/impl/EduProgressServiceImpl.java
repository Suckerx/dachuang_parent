package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.eduservice.entity.EduProgress;
import com.sucker.eduservice.entity.EduUser;
import com.sucker.eduservice.mapper.EduProgressMapper;
import com.sucker.eduservice.mapper.EduUserMapper;
import com.sucker.eduservice.service.EduProgressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sucker.eduservice.service.EduUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 诊疗过程表 服务实现类
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
@Service
public class EduProgressServiceImpl extends ServiceImpl<EduProgressMapper, EduProgress> implements EduProgressService {

    @Autowired
    private EduUserMapper eduUserMapper;

    @Autowired
    private EduProgressMapper eduProgressMapper;

    //根据老师id查询所有诊疗过程
    @Override
    public List<EduProgress> findAll(String teacherId) {
        List<EduProgress> progressList = new ArrayList<>();

        //首先，根据老师Id查询所有userId
        List<EduUser> userList = eduUserMapper.selectList(new QueryWrapper<EduUser>().eq("teacher_id", teacherId));
        for (EduUser eduUser : userList) {
            EduProgress eduProgress = new EduProgress();
            //根据所有userId查询对应诊疗过程
            try {
                eduProgress = eduProgressMapper.selectOne(new QueryWrapper<EduProgress>().eq("user_id",eduUser.getUserId()));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            progressList.add(eduProgress);
        }

        return progressList;
    }
}
