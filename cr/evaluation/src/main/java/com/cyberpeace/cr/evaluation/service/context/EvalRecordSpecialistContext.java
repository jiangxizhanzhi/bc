package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EvalRecordSpecialistContext implements Serializable {
    @ApiModelProperty("数据类型 3级指标时 property value不为空 4、5级指标时 仅传value")
    @NotNull
    private Integer type;

    @ApiModelProperty("数据属性")
    private Integer property;

    @ApiModelProperty(value = "值", required = true)
    private Integer value;
}
