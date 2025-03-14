package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordSpecialistFifthIndexContext implements Serializable {

    @ApiModelProperty
    private String resourceId;

    @ApiModelProperty(value = "关联的五级指标resourceId")
    private String fifthIndexResourceId;

    @ApiModelProperty(value = "值")
    private Integer value;

    @ApiModelProperty(value = "评语")
    private String comment;
}
