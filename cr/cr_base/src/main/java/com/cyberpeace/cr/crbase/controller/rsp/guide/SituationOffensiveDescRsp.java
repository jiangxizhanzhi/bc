package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势导调攻防类型
 *
 * @author myy@date 2024/11/12
 */
@Data
public class SituationOffensiveDescRsp {

    /**
     * 代号
     */
    @ApiModelProperty(value = "编码")
    private Integer code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "类型(攻击/防御)")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String desc;

    /**
     * 成功 URL
     */
    @ApiModelProperty(value = "成功链接")
    private String successUrl;

    /**
     * 失败 URL
     */
    @ApiModelProperty(value = "失败链接")
    private String failUrl;

    public SituationOffensiveDescRsp(Integer code, String name, String desc, String successUrl, String failUrl) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
    }

}
