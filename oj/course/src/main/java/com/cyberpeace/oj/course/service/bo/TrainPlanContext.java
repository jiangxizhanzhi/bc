package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrainPlanContext {

    @ApiModelProperty("训练计划编号")
    private String resourceId;

    @ApiModelProperty("训练计划名称")
    private String name;

    @ApiModelProperty("类型, 1=普通，2=连续")
    private Integer type;

    @ApiModelProperty("组织编号")
    private String organizationId;

    @ApiModelProperty("组织名称")
    private String organizationName;

    @ApiModelProperty("组织路径")
    private String orgPath;

    @ApiModelProperty("父级组织编号")
    private List<String> parentOrgIds;
}
