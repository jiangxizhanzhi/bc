package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 在获取任务列表接口使用
 *
 * @author
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SchemeListMissionRsp implements Serializable {

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
	 * 州
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务状态")
	private Integer state;

}
