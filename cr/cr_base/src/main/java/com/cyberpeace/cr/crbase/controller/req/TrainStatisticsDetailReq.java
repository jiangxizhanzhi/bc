package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练统计学详情请求体
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class TrainStatisticsDetailReq extends PageReqBase {
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
     * 模糊查询筛选参数
     */
    @ApiModelProperty(value = "模糊查询筛选参数")
    private String pattern;
}
