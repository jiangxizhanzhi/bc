package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.RbUserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位任务响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class RoleMissionRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 选项
	 */
	@ApiModelProperty("选项")
	private String option;

	/**
	 * 类别
	 */
	@ApiModelProperty("任务类别")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 任务状态
	 */
	@ApiModelProperty("任务状态")
	private Integer missionStatus;

	/**
	 * 类型
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 技能点
	 */
	@ApiModelProperty("任务技能分数")
	private String skillPoints;

	/**
	 * 写入向上
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 答案
	 */
	@ApiModelProperty("任务答案")
	private String answer;

	/**
	 * 得分
	 */
	@ApiModelProperty("任务分数")
	private Integer score;

	/**
	 * 用户列表
	 */
	@ApiModelProperty("用户列表")
	private List<RbUserData> deliveryDetails;
}
