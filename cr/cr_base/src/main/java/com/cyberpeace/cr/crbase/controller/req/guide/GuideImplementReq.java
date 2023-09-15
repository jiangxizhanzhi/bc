package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class GuideImplementReq implements Serializable {

    @ApiModelProperty(value = "资源id", required = true)
    @NotBlank
    private String resourceId;

    @ApiModelProperty("是否为实例场景")
    private Boolean isInstance;

}
