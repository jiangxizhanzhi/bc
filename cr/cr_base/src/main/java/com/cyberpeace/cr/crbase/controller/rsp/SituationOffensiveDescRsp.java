package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势导调攻防类型
 * @author myy
 */
@Data
public class SituationOffensiveDescRsp {

    @ApiModelProperty(value = "编码")
    private Integer code;

    @ApiModelProperty(value = "类型(攻击/防御)")
    private String name;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "成功链接")
    private String successUrl;

    @ApiModelProperty(value = "失败链接")
    private String failUrl;
}
