package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("评估攻防事件数量数据")
public class EvalRoleEventPercentRsp implements Serializable {

    private String resourceId;

    private String name;

    @ApiModelProperty("红蓝队")
    private Integer role;

    @ApiModelProperty("事件数量")
    private Integer count;
}
