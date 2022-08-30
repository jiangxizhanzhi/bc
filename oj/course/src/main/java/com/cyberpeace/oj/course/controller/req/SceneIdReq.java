package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SceneIdReq implements Serializable {

    @ApiModelProperty("场景编号")
    @NotBlank
    private String sceneId;

}
