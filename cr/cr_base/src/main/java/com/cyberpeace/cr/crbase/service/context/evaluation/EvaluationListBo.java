package com.cyberpeace.cr.crbase.service.context.evaluation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 直接查询可得的评估列表信息
 *
 * @author SN
 * @date 2023/05/18
 */
@Getter
@Setter
public class EvaluationListBo implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * cr_task_submit
	 */
	@ApiModelProperty(value = "评估编号")
	private String resourceId;

	/**
	 * 评估时间
	 */
	@ApiModelProperty(value = "评估时间")
	private Date evaluateTime;

	/**
	 * 提交时间
	 */
	@ApiModelProperty(value = "任务提交时间")
	private Date submitTime;

	/**
	 * 提交用户
	 */
	@ApiModelProperty(value = "提交人id")
	private String submitUser;

	/**
	 * 岗位id
	 */
	@ApiModelProperty(value = "提交人角色id")
	private String roleId;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "提交人角色")
	private String roleName;

	/**
	 * 岗位群
	 */
	@ApiModelProperty(value = "提交人角色类型, 2:团队学员，4：红方，5：蓝方")
	private Integer roleGroup;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名称")
	private String missionTitle;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估状态，0:待评估,1:成功, 2:失败, 3:驳回,4:未交卷")
	private Integer evaluateStatus;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;

	/**
	 * 评估类型
	 */
	@ApiModelProperty(value = "评估模式， 0:机评，1：人评")
	private Integer evaluateType;
}
