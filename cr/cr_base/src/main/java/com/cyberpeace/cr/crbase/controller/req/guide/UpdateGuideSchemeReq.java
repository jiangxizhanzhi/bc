package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateGuideSchemeReq extends AddGuideSchemeReq {

    @ApiModelProperty(value = "资源id", required = true)
    @NotBlank
    private String resourceId;

}
