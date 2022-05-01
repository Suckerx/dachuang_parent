package com.sucker.eduservice.service;

import com.sucker.eduservice.entity.EduProgress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 诊疗过程表 服务类
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
public interface EduProgressService extends IService<EduProgress> {

    //查询所有诊疗过程根据老师Id
    List<EduProgress> findAll(String teacherId);
}
