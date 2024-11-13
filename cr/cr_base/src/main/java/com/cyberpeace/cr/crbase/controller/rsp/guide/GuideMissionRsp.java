package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导调任务响应
 *
 * @author myy@date 2024/11/12
 */
@Data
public class GuideMissionRsp {

    /**
     * 事件类型
     */
    @ApiModelProperty("事件类型")
    private Integer eventType;

    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String missionTitle;

    /**
     * 任务id
     */
    @ApiModelProperty("任务id")
    private String missionId;

    /**
     * 导调id
     */
    @ApiModelProperty("态势导调id;无值新增,有值为编辑")
    private String guideId;

    /**
     * 是可编辑
     */
    @ApiModelProperty("是否可编辑")
    private Boolean isEditable;

    /**
     * 被触发
     */
    @ApiModelProperty("是否触发")
    private Boolean isTriggered;
}
