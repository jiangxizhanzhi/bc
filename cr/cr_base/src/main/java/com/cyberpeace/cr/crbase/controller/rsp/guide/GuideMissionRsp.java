package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author myy
 */
@Data
public class GuideMissionRsp {

    @ApiModelProperty("事件类型")
    private Integer eventType;

    @ApiModelProperty("任务名称")
    private String missionTitle;

    @ApiModelProperty("任务id")
    private String missionId;

    @ApiModelProperty("态势导调id;无值新增,有值为编辑")
    private String guideId;

    @ApiModelProperty("是否可编辑")
    private Boolean isEditable;

    @ApiModelProperty("是否触发")
    private Boolean isTriggered;
}
