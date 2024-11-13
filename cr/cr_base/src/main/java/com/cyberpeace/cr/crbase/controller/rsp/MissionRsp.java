package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class MissionRsp implements Serializable {

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
	 * 选择
	 */
	@ApiModelProperty("选项")
	private String option;

	/**
	 * 类型
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 类别
	 */
	@ApiModelProperty("任务类别id")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 写入向上
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 显示 Writeup
	 */
	@ApiModelProperty("是否展示任务题解")
	private Boolean showWriteup;

	/**
	 * 用户答案
	 */
	@ApiModelProperty("用户答案")
	private String userAnswer;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;
}
