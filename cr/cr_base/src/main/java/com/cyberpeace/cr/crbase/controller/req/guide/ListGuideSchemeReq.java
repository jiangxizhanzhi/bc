package com.cyberpeace.cr.crbase.controller.req.guide;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class ListGuideSchemeReq extends PageReqBase {

    @ApiModelProperty("场景id")
    @NotBlank
    private String sceneId;

}
