package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrTitleUserBo implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("部门")
    private String departmentName;

    @ApiModelProperty("创建用户id")
    private String createUserId;

    @ApiModelProperty("荣誉")
    private String title;
}
