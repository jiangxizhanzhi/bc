package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("评估实时消息数据")
public class EvalEventStatusCount implements Serializable {

    private String resourceId;

    @ApiModelProperty("任务状态")
    private Integer status;

    @ApiModelProperty("事件数量")
    private Integer count;
}
