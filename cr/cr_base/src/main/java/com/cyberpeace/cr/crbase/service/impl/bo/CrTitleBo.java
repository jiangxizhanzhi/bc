package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrTitleBo implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("等级id")
    private String levelId;

    @ApiModelProperty("等级")
    private String level;

    @ApiModelProperty("称号名称")
    private String name;

    @ApiModelProperty("关联训练数量")
    private Integer trainsCount;

    @ApiModelProperty("创建用户id")
    private String createUserId;

    @ApiModelProperty("创建用户名称")
    private String createUser;
}
