package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.constant.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SituationComponentsAnimationDeleteReq {

    @ApiModelProperty()
    @NotEmpty()
    private List<String> resourceIds;
}
