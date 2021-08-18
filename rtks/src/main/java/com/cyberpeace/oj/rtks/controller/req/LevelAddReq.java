package com.cyberpeace.oj.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class LevelAddReq implements Serializable {

    @ApiModelProperty()
    private String resourceId;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String parent;

}
