package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 训练计划请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see PageReqBase
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TrainPlanWebReq extends PageReqBase {

	/**
	 * 训练计划状态
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "plan_status", value = "训练计划状态")
	private String planStatus;


	public void setPlan_status(String planStatus) {
		this.planStatus = planStatus;
	}
}
