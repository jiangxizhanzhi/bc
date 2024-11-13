package com.cyberpeace.cr.crbase.controller.rsp.guide;

import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 态势导调攻防类型
 *
 * @author myy@date 2024/11/12
 */
@Data
public class GuideParamTypeRsp {
    /**
     * 事件类型
     */
    @ApiModelProperty(value = "事件类型")
    private List<ParamTypeRsp> eventType;

    /**
     * 攻击类型
     */
    @ApiModelProperty(value = "攻击类型")
    private List<AttackSituationOffensiveDescRsp> attackType;

    /**
     * 防御型
     */
    @ApiModelProperty(value = "防御类型")
    private List<SituationOffensiveDescRsp> defenseType;

    /**
     * 结果类型
     */
    @ApiModelProperty(value = "攻击结果")
    private List<ParamTypeRsp> resultType;

    /**
     * 岗位类型
     */
    @ApiModelProperty(value = "归属角色")
    private List<ParamTypeRsp> roleType;

    /**
     * 条件类型
     */
    @ApiModelProperty(value = "触发条件")
    private List<ParamTypeRsp> conditionType;

    /**
     * 节点类型
     */
    @ApiModelProperty(value = "节点")
    private List<MissionCategoryRsp> nodeType;

    /**
     * 阻塞节点类型
     */
    @ApiModelProperty(value = "阻断节点")
    private List<MissionCategoryRsp> blockingNodeType;

    /**
     * 区域类型
     */
    @ApiModelProperty(value = "区域")
    private List<MissionCategoryRsp> areaType;

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

