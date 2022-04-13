package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrTitleDetailBo implements Serializable {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("等级id")
    private String levelId;

    @ApiModelProperty("等级名称")
    private String level;

    @ApiModelProperty("称号名称")
    private String name;

    @ApiModelProperty("及格标准")
    private Integer score;

    @ApiModelProperty("创建用户id")
    private String createUserId;

}
