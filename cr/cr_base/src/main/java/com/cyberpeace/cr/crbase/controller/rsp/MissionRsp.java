package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionRsp implements Serializable {

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
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty("任务类别id")
	private String category;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 写入向上
	 *
	 * @see String
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 显示 Writeup
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否展示任务题解")
	private Boolean showWriteup;

	/**
	 * 用户答案
	 *
	 * @see String
	 */
	@ApiModelProperty("用户答案")
	private String userAnswer;

	/**
	 * 备注
	 *
	 * @see String
	 */
	@ApiModelProperty("备注")
	private String remark;
}
