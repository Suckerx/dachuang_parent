package com.sucker.eduservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @ApiModelProperty("对应userid")
    @TableId("progress_id")
    private String progressId;

    @ApiModelProperty("诊疗过程")
    @TableField("progress")
    private String progress;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Date updateTime;


}
