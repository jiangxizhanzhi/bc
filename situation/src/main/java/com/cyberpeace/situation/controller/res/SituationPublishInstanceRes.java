package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;

import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势发布实例返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationPublishInstanceRes extends SituationPublishInstance implements Serializable {

	/**
	 * 可视化方案名称
	 */
	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;

	/**
	 * 可视化方案描述
	 */
	@ApiModelProperty(value = "可视化方案描述")
	private String schemeDesc;

	/**
	 * 任务类型
	 */
	@ApiModelProperty(value = "任务类型")
	private String taskType;

	/**
	 * 原始模板ID
	 */
	@ApiModelProperty("原始模板ID")
	private String originTemplateId;

	/**
	 * 重演开始时间
	 */
	@ApiModelProperty(value = "重演开始时间")
	private Date startTime;

	/**
	 * 重演结束时间
	 */
	@ApiModelProperty(value = "重演结束时间")
	private Date endTime;

}
