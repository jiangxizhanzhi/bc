package com.cyberpeace.oj.practice.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResourcePublicReq {

    @ApiModelProperty("更新资源ID")
    @JsonProperty("ids")
    private List<String> resourceIds;

    @ApiModelProperty("是否公开")
    private boolean isPublic;
}
