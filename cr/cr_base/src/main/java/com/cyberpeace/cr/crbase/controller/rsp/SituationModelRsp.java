package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 态势导调攻防类型
 * @author myy
 */
@Data
public class SituationModelRsp {
    @ApiModelProperty(value = "攻击类型")
    private List<SituationOffensiveDescRsp> attackList;

    @ApiModelProperty(value = "防御类型")
    private List<SituationOffensiveDescRsp> defenseList;

    @ApiModelProperty(value = "阻断类型")
    private String blocking;

    @ApiModelProperty(value = "归属类型")
    private SituationBelongDescRsp belong;
}
