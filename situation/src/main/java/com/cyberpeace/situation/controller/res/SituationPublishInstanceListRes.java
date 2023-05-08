package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势发布实例列表返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationPublishInstanceListRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String category;

	/**
	 * 大屏状态
	 */
	@ApiModelProperty(value = "大屏状态")
	private String status;

	/**
	 * 想定方案名称
	 */
	@ApiModelProperty(value = "想定方案名称")
	private String planName;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名称")
	private String taskName;

	/**
	 * 科目名称
	 */
	@ApiModelProperty(value = "科目名称")
	private String subjectName;

	/**
	 * 模板名称
	 */
	@ApiModelProperty(value = "模板名称")
	private String templateName;
}
