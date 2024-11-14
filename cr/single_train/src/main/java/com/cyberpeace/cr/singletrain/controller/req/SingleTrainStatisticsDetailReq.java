package com.cyberpeace.cr.singletrain.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单训练统计学详情请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SingleTrainStatisticsDetailReq extends PageReqBase {
	/**
	 * 组织查询
	 */
	@ApiModelProperty("组织查询")
    private String organization;

	/**
	 * 开始日期
	 */
	@ApiModelProperty(name = "startTime", value = "开始日期")
    private String startTime;

	/**
	 * 结束日期
	 */
	@ApiModelProperty(name = "endTime", value = "结束日期")
    private String endTime;

	/**
	 * 模糊查询筛选参数
	 */
	@ApiModelProperty(value = "模糊查询筛选参数")
    private String pattern;

}
