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
 * 诊疗过程表
 * </p>
 *
 * @author sucker
 * @since 2022-02-12
 */
@Getter
@Setter
@TableName("edu_progress")
@ApiModel(value = "EduProgress对象", description = "诊疗过程表")
public class EduProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "progress_id",type = IdType.ASSIGN_ID)
    private String progressId;

    @ApiModelProperty("userid")
    @TableField(value = "user_id")
    private String userId;

    @ApiModelProperty("诊疗过程")
    @TableField("progress")
    private String progress;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
