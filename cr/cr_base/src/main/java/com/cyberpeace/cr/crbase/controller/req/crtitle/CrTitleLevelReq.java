package com.cyberpeace.cr.crbase.controller.req.crtitle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrTitleLevelReq implements Serializable {

    @ApiModelProperty("资源ID标识")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;
}
