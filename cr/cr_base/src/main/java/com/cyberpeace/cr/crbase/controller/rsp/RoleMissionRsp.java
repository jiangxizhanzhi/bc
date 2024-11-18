package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.RbUserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色任务响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleMissionRsp implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 选择
	 *
	 * @see String
	 */
	@ApiModelProperty("选项")
	private String option;

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty("任务类别")
	private String category;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 任务状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务状态")
	private Integer missionStatus;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 技能点
	 *
	 * @see String
	 */
	@ApiModelProperty("任务技能分数")
	private String skillPoints;

	/**
	 * 写入向上
	 *
	 * @see String
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@ApiModelProperty("任务答案")
	private String answer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务分数")
	private Integer score;

	/**
	 * 用户列表
	 *
	 * @see List<RbUserData>
	 */
	@ApiModelProperty("用户列表")
	private List<RbUserData> deliveryDetails;
}
