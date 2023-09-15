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
}
