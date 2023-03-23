package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrNoticeGroupRsp implements Serializable {
    @ApiModelProperty("公告目标标识")
    private Integer id;

    @ApiModelProperty("公告目标名称")
    private String name;
}
