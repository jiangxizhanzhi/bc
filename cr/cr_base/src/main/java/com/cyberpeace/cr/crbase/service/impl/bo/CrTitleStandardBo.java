package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrTitleStandardBo implements Serializable {
    @ApiModelProperty("训练resource_id")
    private String resourceId;

    @ApiModelProperty("训练名称")
    private String trainName;

    @ApiModelProperty("训练类型")
    private Integer businessType;

    @ApiModelProperty("训练次数")
    private Integer trainCount;

    @ApiModelProperty("训练用户最高分")
    private Integer maxScore;
}
