package com.cyberpeace.oj.practice.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("资源授权操作")
public class ResourceShareReq {

    @JsonProperty("ids")
    @ApiModelProperty("资源ID")
    private List<String> resourceIds;

    @JsonProperty("share_teachers")
    @ApiModelProperty("授权资源")
    private List<String> shareTeachers;

    @ApiModelProperty("授权模式, 1-所有, 2-自定义")
    private Integer shareMode;

}
