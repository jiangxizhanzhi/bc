package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserStatsReq extends PageReqBase {
    @ApiModelProperty("组织查询")
    private String organization;

    @ApiModelProperty("时间统计类型")
    private String date_type;
}
