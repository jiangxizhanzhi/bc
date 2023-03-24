package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CrNoticeGroupRsp implements Serializable {
    @ApiModelProperty("公告目标标识")
    private String value;

    @ApiModelProperty("公告目标名称")
    private String label;
}
