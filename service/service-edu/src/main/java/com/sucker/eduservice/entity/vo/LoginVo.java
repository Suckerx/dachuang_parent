package com.sucker.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="LoginVo", description="登录对象")
public class LoginVo {
    @ApiModelProperty(value = "账号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色(0为用户，1为咨询师)")
    private Integer character;
}
