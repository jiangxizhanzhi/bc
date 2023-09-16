package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author myy
 */
@Data
public class GuideMissionRsp implements Serializable {

    @ApiModelProperty("任务名称")
    private String missionTitle;

    @ApiModelProperty("任务id")
    private String missionId;

    @ApiModelProperty("态势导调id")
    private String guideId;

    @ApiModelProperty("是否可编辑 0: 不可编辑 1:可编辑")
    private Boolean isEditable;

}
