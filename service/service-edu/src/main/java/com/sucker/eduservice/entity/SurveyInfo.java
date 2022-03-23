package com.sucker.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 调查问卷主表
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("survey_info")
@ApiModel(value = "SurveyInfo对象", description = "调查问卷主表")
public class SurveyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("调查问卷id")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("创建人员ID")
    @TableField("creator_id")
    private String creatorId;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
