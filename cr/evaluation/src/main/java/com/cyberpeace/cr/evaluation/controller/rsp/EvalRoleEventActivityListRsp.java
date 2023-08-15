package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("评估实时消息数据")
public class EvalRoleEventActivityListRsp implements Serializable {

    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    private String creatTime;

    private Integer count;
}
