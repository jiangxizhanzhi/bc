package com.cyberpeace.oj.practice.dao.entityUnused;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("理论基础练习")
public class PracticeTheory extends PracticeBase {

    @ApiModelProperty("题型")
    private Integer questionType;

    @ApiModelProperty("选项 - (判断、单选、多选题型使用)")
    private String option;

    @ApiModelProperty("空 - (填空题型使用)")
    private String blank;

    @ApiModelProperty("关键字 - (简答题题型使用)")
    private String keyword;

}
