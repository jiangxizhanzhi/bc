package com.cyberpeace.cr.teamtrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XdStatisticsReq {

    /**
     * 开始时间
     */
    @ApiModelProperty(name = "startTime", value = "开始日期")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(name = "endTime", value = "结束日期")
    private String endTime;

}
