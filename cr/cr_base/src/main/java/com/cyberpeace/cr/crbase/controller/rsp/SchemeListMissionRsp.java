package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 在获取任务列表接口使用
 *
 * @author@date 2024/11/12
 */
@Data
public class SchemeListMissionRsp implements Serializable {

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
	 * 州
	 */
	@ApiModelProperty("任务状态")
	private Integer state;

}
