package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SituationPublishInstanceFilterReq implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "任务类型")
    private String category;

    @ApiModelProperty(value = "状态")
    private String status;
}
