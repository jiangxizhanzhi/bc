package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperContext implements Serializable {

    @ApiModelProperty("资源编号 空为新增 非空则修改")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("封面")
    private String logo;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("试卷类型")
    private Integer type;

    @ApiModelProperty("练习数据体(每个元素需要包含`resource_id`, `practice_type`, `is_option_random`三个属性)")
    private List<PaperPracticeContext> practiceList;

}
