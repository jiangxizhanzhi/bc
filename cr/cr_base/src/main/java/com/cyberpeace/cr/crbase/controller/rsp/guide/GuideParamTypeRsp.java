package com.cyberpeace.cr.crbase.controller.rsp.guide;

import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 态势导调攻防类型
 *
 * @author myy
 */
@Data
public class GuideParamTypeRsp {
    @ApiModelProperty(value = "事件类型")
    private List<ParamTypeRsp> eventType;

    @ApiModelProperty(value = "攻击类型")
    private List<ParamTypeRsp> attackType;

    @ApiModelProperty(value = "防御类型")
    private List<ParamTypeRsp> defenseType;

    @ApiModelProperty(value = "攻击结果")
    private List<ParamTypeRsp> resultType;

    @ApiModelProperty(value = "归属角色")
    private List<ParamTypeRsp> roleType;

    @ApiModelProperty(value = "触发条件")
    private List<ParamTypeRsp> conditionType;

    @ApiModelProperty(value = "节点")
    private List<MissionCategoryRsp> nodeType;

    @ApiModelProperty(value = "攻击源")
    private List<MissionCategoryRsp> sourceType;

    @ApiModelProperty(value = "攻击目标")
    private List<MissionCategoryRsp> targetType;
}

