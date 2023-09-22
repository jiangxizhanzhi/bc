package com.cyberpeace.oj.practice.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("试卷新增/更新")
public class PaperAddReq {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("封面")
    private String logo;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("练习数据体(每个元素需要包含`resource_id`, `practice_type`, `is_option_random`三个属性)")
    private List<SinglePracticeReq> practiceList;

}
