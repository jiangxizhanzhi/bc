package com.cyberpeace.cr.singletrain.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("单兵训练模板更新")
public class SingleTrainTemplateUpdateReqRel extends SingleTrainTemplateReqRel implements Serializable {

    @ApiModelProperty("模板业务id")
    private String resourceId;

}
