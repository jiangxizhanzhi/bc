package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("试用账号禁用权限")
public class MpOperationPermission implements Serializable {
    private Integer pk;

    private String id;

    private Date createTime;

    private Date updateTime;

    @ApiModelProperty("禁用按钮")
    private String key;

    @ApiModelProperty("模块")
    private String module;

    @ApiModelProperty("接口地址")
    private String url;

    @ApiModelProperty("desc")
    private String name;

}
