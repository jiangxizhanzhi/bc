package com.cyberpeace.situation.controller.req;

import com.cyberpeace.situation.model.LineStyle;
import com.cyberpeace.situation.model.MediaAnimation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttackMessageReq implements Serializable {

    @ApiModelProperty("资源id")
    private String resourceId;

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("动效名称")
    private String name;

    @ApiModelProperty("动效描述")
    private String description;

    @ApiModelProperty("源节点id")
    private String sourceId;

    @ApiModelProperty("源节点名称")
    private String sourceName;

    @ApiModelProperty("目标节点id")
    private String targetId;

    @ApiModelProperty("目标节点名称")
    private String targetName;

    @ApiModelProperty("间隔时间")
    private Integer intervalTime;

    @ApiModelProperty("持续时间（预留）")
    private Integer durationTime;

    @ApiModelProperty("是否聚焦")
    private Boolean lookAtTarget;

    @ApiModelProperty("线型")
    private LineStyle lineStyle;

    @ApiModelProperty("媒体动效")
    private List<MediaAnimation> mediaAnimationList;

}
