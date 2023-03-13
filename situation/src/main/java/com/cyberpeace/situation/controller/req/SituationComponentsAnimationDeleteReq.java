package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SituationComponentsAnimationDeleteReq {

    @ApiModelProperty()
    @NotBlank(message = ErrorCode.RESOURCE_NOT_FOUND)
    private String resourceId;
}
