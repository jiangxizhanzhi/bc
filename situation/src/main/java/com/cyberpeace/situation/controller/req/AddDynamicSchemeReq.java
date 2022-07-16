package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.constant.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class AddDynamicSchemeReq implements Serializable {

    @ApiModelProperty("场景id")
    @NotBlank
    private String sceneId;

    @ApiModelProperty("动效方案名称")
    @NotBlank(message = ErrorCode.EMPTY_SCHEME_NAME)
    private String name;

    public AddDynamicSchemeReq(String sceneId, String name) {
        this.sceneId = sceneId;
        this.name = name;
    }
}
