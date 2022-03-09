package com.cyberpeace.situation.model.ws;

import com.cyberpeace.situation.model.LineStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class JyjWsEvent implements Serializable {

    @ApiModelProperty("攻击队伍名称")
    private String name;

    @ApiModelProperty("攻击节点id")
    private String sourceId;

    @ApiModelProperty("被攻击节点id")
    private String targetId;

    @ApiModelProperty("攻击类型")
    private Integer attackType;

    @ApiModelProperty("攻击线型")
    private LineStyle lineStyle;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty("是否聚焦到被攻击节点")
    private Boolean lookAtTarget;

}
