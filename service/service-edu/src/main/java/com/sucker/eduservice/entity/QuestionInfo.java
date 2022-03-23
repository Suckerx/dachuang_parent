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
 * 调查问卷问题主表
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("question_info")
@ApiModel(value = "QuestionInfo对象", description = "调查问卷问题主表")
public class QuestionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("问题id")
    @TableId(value = "id")
    private Integer id;

    @ApiModelProperty("1 单选 2多选 3填空")
    @TableField("question_type")
    private String questionType;

    @ApiModelProperty("问题内容")
    @TableField("question_description")
    private String questionDescription;

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "id=" + id +
                ", questionType='" + questionType + '\'' +
                ", questionDescription='" + questionDescription + '\'' +
                '}';
    }
}
