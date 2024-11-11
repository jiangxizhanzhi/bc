package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户统计请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class UserStatsReq extends PageReqBase {
    /**
     * 组织
     */
    @ApiModelProperty("组织查询")
    private String organization;

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

    /**
     * 模式
     */
    @ApiModelProperty(value = "模糊查询筛选参数(不会转义)")
    private String pattern;
}
