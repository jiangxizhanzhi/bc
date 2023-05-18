package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直接查询可得的评估列表信息
 *
 * @author SN
 * @date 2023/05/18
 */
@Data
public class EvaluationListBo implements Serializable {

	/**
	 * cr_task_submit
	 */
	@ApiModelProperty(value = "评估编号")
	private String resourceId;

	@ApiModelProperty(value = "评估时间")
	private Date evaluateTime;

	@ApiModelProperty(value = "任务提交时间")
	private Date submitTime;

	@ApiModelProperty(value = "提交人id")
	private String submitUser;

	@ApiModelProperty(value = "提交人角色id")
	private String roleId;

	@ApiModelProperty(value = "提交人角色")
	private String roleName;

	@ApiModelProperty(value = "提交人角色类型, 2:团队学员，4：红方，5：蓝方")
	private Integer roleGroup;

	@ApiModelProperty(value = "任务名称")
	private String missionTitle;

	@ApiModelProperty(value = "评估状态，0:待评估,1:成功, 2:失败, 3:驳回,4:未交卷")
	private Integer evaluateStatus;

	@ApiModelProperty(value = "得分")
	private Integer score;

	@ApiModelProperty(value = "评估模式， 0:机评，1：人评")
	private Integer evaluateType;
}
