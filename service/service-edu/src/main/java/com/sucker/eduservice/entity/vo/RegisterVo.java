package com.sucker.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="RegisterVo", description="注册对象")
public class RegisterVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色(0为用户，1为咨询师)")
    private Integer character;
}
