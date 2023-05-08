package com.cyberpeace.situation.controller.req;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏实例新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationPublishInstanceAddReq {

	/**
	 * 可视化大屏名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "可视化大屏名称", required = true)
	private String name;

	/**
	 * 大屏描述
	 */
	@ApiModelProperty(value = "大屏描述")
	private String description;

	/**
	 * 特定事件
	 */
	@ApiModelProperty(value = "特定事件")
	private List<List<String>> eventList = Lists.newArrayList();

	/**
	 * 可视化方案ID
	 */
	@NotBlank(groups = Create.class, message = SituationErrorCode.SCHEME_EMPTY_ERROR)
	@ApiModelProperty(value = "可视化方案ID")
	private String schemeId;

	/**
	 * 可视化方案名称
	 */
	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;

	/**
	 * 任务ID
	 */
	@ApiModelProperty(value = "任务ID")
	private String taskId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名称")
	private String taskName;

	/**
	 * 任务类型
	 */
	@ApiModelProperty(value = "任务类型")
	private String taskType;

	/**
	 * 科目ID
	 */
	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	/**
	 * 科目名称
	 */
	@ApiModelProperty(value = "科目名称")
	private String subjectName;

	/**
	 * 模板ID
	 */
	@NotBlank(groups = Update.class, message = SituationErrorCode.TEMPLATE_EMPTY_ERROR)
	@ApiModelProperty(value = "模板ID")
	private String templateId;

	/**
	 * 任务分类
	 */
	@ApiModelProperty(value = "任务分类")
	private String category;

	/**
	 * 缩略图
	 */
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;
}
