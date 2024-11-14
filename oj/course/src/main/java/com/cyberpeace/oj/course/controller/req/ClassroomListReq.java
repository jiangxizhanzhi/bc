package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.util.TimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@ApiModel
@Data
public class ClassroomListReq implements Serializable {

	/**
	 * 开始日期
	 */
	@ApiModelProperty(name = "start_time", value = "开始日期")
	@NotNull
	private String startTime;

	/**
	 * 结束日期
	 */
	@ApiModelProperty(name = "end_time", value = "结束日期")
	@NotNull
	private String endTime;

	/**
	 * 组织编号
	 */
	@ApiModelProperty(name = "organization_id", value = "组织编号")
	private String organizationId;

	/**
	 * 训练计划编号
	 */
	@ApiModelProperty(name = "train_plan_id", value = "训练计划编号")
	private String trainPlanId;

	public void setStart_time(String startTime) {
		this.startTime = startTime;
	}

	public void setEnd_time(String endTime) {
		this.endTime = endTime;
	}

	public void setOrganization_id(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getStartTime() {
		return TimeUtil.toDate2(startTime);
	}

	public Date getEndTime() {
		return TimeUtil.toDate2(endTime);
	}

	public String getTrainPlanId() {
		return trainPlanId;
	}

	public void setTrain_plan_id(String trainPlanId) {
		this.trainPlanId = trainPlanId;
	}
}
