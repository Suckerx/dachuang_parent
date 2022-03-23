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
 * 调查问卷问题选项主表
 * </p>
 *
 * @author sucker
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("option_info")
@ApiModel(value = "OptionInfo对象", description = "调查问卷问题选项主表")
public class OptionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("选项id")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("问题ID")
    @TableField("question_id")
    private Integer questionId;

    @ApiModelProperty("选项内容")
    @TableField("option_content")
    private String optionContent;

    @ApiModelProperty("选项id 1为A选项 2为B选项 3为C选项 ...")
    @TableField("option_sort")
    private Integer optionSort;


}
