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
 * 用户答案表
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("answer_info")
@ApiModel(value = "AnswerInfo对象", description = "用户答案表")
public class AnswerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("成员id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("问卷主表ID")
    @TableField("survey_id")
    private String surveyId;

    @ApiModelProperty("问题主表ID")
    @TableField("question_id")
    private Integer questionId;

    @ApiModelProperty("答案id 1A 2B 3C 4D 5E")
    @TableField("answer_id")
    private String answerId;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;


}
