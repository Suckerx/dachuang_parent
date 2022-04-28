package com.sucker.eduservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 预约时间表
 * </p>
 *
 * @author sucker
 * @since 2022-04-28
 */
@Getter
@Setter
@TableName("reserve")
@ApiModel(value = "Reserve对象", description = "预约时间表")
public class Reserve implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("预约房间号")
    @TableId(value = "reserve_id")
    private Integer reserveId;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("老师id")
    @TableField("teacher_id")
    private String teacherId;

    @ApiModelProperty("预约时间")
    @TableField("reserve_time")
    private String reserveTime;


}
