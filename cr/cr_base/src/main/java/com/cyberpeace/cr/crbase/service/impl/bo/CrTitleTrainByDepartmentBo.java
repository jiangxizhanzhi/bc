package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrTitleTrainByDepartmentBo {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("场景名称")
    private String trainName;

    @ApiModelProperty("职称")
    private String titleName;

    @ApiModelProperty("职称id")
    private String titleId;

    @ApiModelProperty("训练人")
    private String createUser;

    @ApiModelProperty("级别")
    private String level;

    @ApiModelProperty("训练次数")
    private Integer trainCount;

    @ApiModelProperty("最高分")
    private Integer maxScore;

    @ApiModelProperty("及格分")
    private Integer passScore;
}
