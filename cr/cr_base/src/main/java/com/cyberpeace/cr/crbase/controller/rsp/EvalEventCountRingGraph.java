package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("评估实时消息数据")
public class EvalEventCountRingGraph implements Serializable {

    private String resourceId;

    @ApiModelProperty("事件名称")
    private String eventName;

    @ApiModelProperty("事件数量")
    private double count;

    @ApiModelProperty("攻防事件")
    private Integer type;
}
