package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户训练报告
 *
 * @author SN
 * @date 2024/11/08
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
	 * 岗位名称
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
