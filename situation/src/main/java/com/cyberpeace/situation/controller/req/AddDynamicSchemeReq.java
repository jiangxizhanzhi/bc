package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class AddDynamicSchemeReq implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("动效方案名称")
    @NotBlank
    private String name;

    public AddDynamicSchemeReq(String sceneId, String name) {
        this.sceneId = sceneId;
        this.name = name;
    }
}
