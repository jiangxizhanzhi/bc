package com.cyberpeace.cs.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ResourcePublicReq {

    @ApiModelProperty("更新资源ID")
    @NotEmpty
    private List<String> resourceIds;

    @ApiModelProperty("是否公开")
    @NotNull
    private Boolean isPublic;
}
