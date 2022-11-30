package com.cyberpeace.bs.service.bo.simulator;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class MeterPageReq extends PageReqBase implements Serializable {

    @Getter
    @Setter
    @ApiModelProperty(value = "场景id")
    private String scene_id;
}
