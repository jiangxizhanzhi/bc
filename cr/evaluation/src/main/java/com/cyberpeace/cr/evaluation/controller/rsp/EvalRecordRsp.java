package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalRecordRsp implements Serializable {
	/**
	 * ID
	 */
	@ApiModelProperty("ID")
    private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
    private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
    private String description;

	/**
	 * 靶场测评实例ID
	 */
	@ApiModelProperty("靶场测评实例ID")
    private String roomId;

	/**
	 * 使用指标体系ID
	 */
	@ApiModelProperty("使用指标体系ID")
    private String systemId;

	/**
	 * 开始时间（评估采集数据时间范围）
	 */
	@ApiModelProperty("开始时间（评估采集数据时间范围）")
    private Date startTime;

	/**
	 * 结束时间（评估采集数据时间范围）
	 */
	@ApiModelProperty("结束时间（评估采集数据时间范围）")
    private Date endTime;

	/**
	 * 评估开始时间
	 */
	@ApiModelProperty("评估开始时间")
    private Date evalTime;

	/**
	 * 启动状态 1-未评估 2-计算中 3-再次启动
	 */
	@ApiModelProperty("启动状态 1-未评估 2-计算中 3-再次启动")
    private Integer runStatus;

	/**
	 * 指标体系结构
	 */
	@ApiModelProperty("指标体系结构")
    private List<EvalSystemIndexesContext> indexes;
}
