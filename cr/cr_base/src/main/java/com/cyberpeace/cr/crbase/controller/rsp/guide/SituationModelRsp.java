package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 态势导调攻防类型
 *
 * @author myy@date 2024/11/12
 */
@Data
public class SituationModelRsp {
    /**
     * 攻击列表
     */
    @ApiModelProperty(value = "攻击类型")
    private List<SituationOffensiveDescRsp> attackList;

    /**
     * 防御列表
     */
    @ApiModelProperty(value = "防御类型")
    private List<SituationOffensiveDescRsp> defenseList;

    /**
     * 阻止 URL
     */
    @ApiModelProperty(value = "阻断类型")
    private String blockingUrl;

    /**
     * 属于 URL
     */
    @ApiModelProperty(value = "归属类型")
    private SituationBelongDescRsp belongUrl;
}
