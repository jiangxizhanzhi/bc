package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PracticeSetReq {

    @ApiModelProperty(value = "ID", required = true)
    private String resourceId;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "类型", allowableValues = "0, 2", required = true)
    private Integer type;

    @ApiModelProperty(value = "公开", required = true)
    private boolean isPublic;

    @ApiModelProperty(value = "授权访问", required = true)
    private Integer auth;

    @ApiModelProperty(value = "授权操作", required = true)
    private Integer share;

    @ApiModelProperty(value = "授权访问组织", hidden = true)
    private String authOrganization;

    @ApiModelProperty(value = "授权操作用户", hidden = true)
    private String shareTeachers;

}
