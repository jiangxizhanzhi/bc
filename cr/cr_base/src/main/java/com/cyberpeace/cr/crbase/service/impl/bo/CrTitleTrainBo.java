package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrTitleTrainBo {
    @ApiModelProperty("ID")
    private String resourceId;

    @ApiModelProperty("训练名称")
    private String name;

    @ApiModelProperty("训练类型")
    private Integer businessType;

    @ApiModelProperty("创建者ID")
    private String createUserId;

    @ApiModelProperty("创建者名称")
    private String createUser;
}
