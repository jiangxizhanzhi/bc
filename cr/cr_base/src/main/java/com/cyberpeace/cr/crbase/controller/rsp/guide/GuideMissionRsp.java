package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author myy
 */
@Data
public class GuideMissionRsp implements Serializable {

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

}
