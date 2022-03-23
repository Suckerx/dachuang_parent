package com.sucker.eduservice.entity.vo;

import com.sucker.eduservice.entity.OptionInfo;
import com.sucker.eduservice.entity.QuestionInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="NewSurveyVo", description="新建问卷调查封装对象")
public class NewSurveyVo {

    @ApiModelProperty(value = "问题对象")
    private QuestionInfo questionInfo;

    @ApiModelProperty(value = "选项集合")
    private List<OptionInfo> optionInfoList;

}
