package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户统计请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
public class UserStatsReq extends PageReqBase {
    /**
     * 组织
     *
     * @see String
     */
    @ApiModelProperty("组织查询")
    private String organization;

    /**
     * 开始时间
     *
     * @see String
     */
    @ApiModelProperty(name = "startTime", value = "开始日期")
    private String startTime;

    /**
     * 结束时间
     *
     * @see String
     */
    @ApiModelProperty(name = "endTime", value = "结束日期")
    private String endTime;

    /**
     * 模式
     *
     * @see String
     */
    @ApiModelProperty(value = "模糊查询筛选参数(不会转义)")
    private String pattern;
}
