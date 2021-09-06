package com.cyberpeace.oj.practice.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("资源授权访问")
public class ResourceAuthReq {

    @JsonProperty("ids")
    @ApiModelProperty("资源ID")
    private List<String> resourceIds;

    @JsonProperty("auth_organizations")
    @ApiModelProperty("授权资源")
    private List<String> authOrganizations;

    @ApiModelProperty("授权模式, 1-所有, 2-自定义")
    private Integer authMode;

}
