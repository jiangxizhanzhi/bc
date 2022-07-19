package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainStatisticsDetailReq extends PageReqBase {
    @ApiModelProperty("组织查询")
    private String organization;

    @ApiModelProperty(name = "startTime", value = "开始日期")
    private String startTime;

    @ApiModelProperty(name = "endTime", value = "结束日期")
    private String endTime;
}
