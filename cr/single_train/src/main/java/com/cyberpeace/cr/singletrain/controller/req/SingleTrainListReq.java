package com.cyberpeace.cr.singletrain.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("前台单兵训练分页查询")
public class SingleTrainListReq extends PageReqBase implements Serializable {
    @ApiModelProperty("训练类别")
    private Integer category;

    @ApiModelProperty("训练难度")
    private Integer difficult;

    private Integer withInstance;
}
