package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户训练报告
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class UserReport {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名")
	private String userName;

	/**
	 * 角色名称
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;

	/**
	 * 任务完成
	 */
	@ApiModelProperty(value = "任务完成状态")
	private List<Integer> missionCompleted;

	/**
	 * 评论
	 */
	@ApiModelProperty(value = "评语")
	private String comment;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "分数")
	private Integer score;
}
