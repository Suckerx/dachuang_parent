package com.sucker.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sucker.eduservice.entity.EduTeacher;
import com.sucker.eduservice.entity.EduUser;
import com.sucker.eduservice.entity.vo.LoginVo;
import com.sucker.eduservice.entity.vo.RegisterVo;
import com.sucker.eduservice.service.EduLoginService;
import com.sucker.eduservice.service.EduTeacherService;
import com.sucker.eduservice.service.EduUserService;
import com.sucker.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class EduLoginServiceImpl implements EduLoginService {

    @Autowired
    private EduUserService eduUserService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //登录
    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        Integer character = loginVo.getCharacter();

        if(ObjectUtils.isEmpty(mobile) || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(character)){
            throw new GuliException(20001,"error");
        }

        String id = "";

        if(character == 0){//user
            EduUser user = eduUserService.getOne(new QueryWrapper<EduUser>().eq("mobile", mobile));
            if(user == null) throw new GuliException(20001,"error");
            if(password.equals(user.getPassword())) id = user.getUserId();
            else throw new GuliException(20001,"error");
        }else {//teacher
            EduTeacher teacher = eduTeacherService.getOne(new QueryWrapper<EduTeacher>().eq("mobile", mobile));
            if(teacher == null) throw new GuliException(20001,"error");
            if(password.equals(teacher.getPassword())) id = teacher.getTeacherId();
            else throw new GuliException(20001,"error");
        }

        return id;
    }


    //登录Test
    @Override
    public void loginTest(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        Integer character = loginVo.getCharacter();
        if(ObjectUtils.isEmpty(mobile) || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(character)){
            throw new GuliException(20001,"error");
        }
        if(mobile.equals("root") && password.equals("root")) return;
        else throw new GuliException(20001,"error");
    }


    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String mobile = registerVo.getMobile();
        String name = registerVo.getName();
        String password = registerVo.getPassword();
        Integer character = registerVo.getCharacter();

        //校验参数
        if(ObjectUtils.isEmpty(mobile) || ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(password) || ObjectUtils.isEmpty(character)){
            throw new GuliException(20001,"error");
        }


        //查询数据库中是否存在相同的手机号码
        if(character == 0){
            Integer count = eduUserService.count((new QueryWrapper<EduUser>().eq("mobile", mobile)));
            if(count > 0) { throw new GuliException(20001,"账号已存在");}
            //添加注册信息到数据库
            EduUser user = new EduUser();
            user.setMobile(mobile);
            user.setUsername(name);
            user.setPassword(password);
            user.setAvatar("https://img-blog.csdnimg.cn/5f2f078d38de4f098380734e7d4902f8.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbTBfNjczNjk2MjI=,size_14,color_FFFFFF,t_70,g_se,x_16");
            eduUserService.save(user);
        }else{
            Integer count = eduTeacherService.count((new QueryWrapper<EduTeacher>().eq("mobile", mobile)));
            if(count > 0) { throw new GuliException(20001,"账号已存在"); }
            //添加注册信息到数据库
            EduTeacher teacher = new EduTeacher();
            BeanUtils.copyProperties(registerVo,teacher);
            teacher.setAvatar("https://img-blog.csdnimg.cn/4918321b5e7d40cb8814992cb8236d30.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAbTBfNjczNjk2MjI=,size_14,color_FFFFFF,t_70,g_se,x_16");
            eduTeacherService.save(teacher);
        }

    }


}
