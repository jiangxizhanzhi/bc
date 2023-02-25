package com.cyberpeace.situation.controller.req;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.service.context.ListContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationPublishInstanceAddReq {

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "可视化大屏名称", required = true)
	private String name;

	@ApiModelProperty(value = "大屏描述")
	private String description;

	@ApiModelProperty(value = "特定事件")
	private List<ListContext> eventList = Lists.newArrayList();

	@NotBlank(groups = Create.class, message = SituationErrorCode.SCHEME_EMPTY_ERROR)
	@ApiModelProperty(value = "可视化方案ID")
	private String schemeId;

	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "任务名称")
	private String taskName;

	@ApiModelProperty(value = "任务类型")
	private String taskType;

	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@ApiModelProperty(value = "科目名称")
	private String subjectName;

	@NotBlank(groups = Update.class, message = SituationErrorCode.TEMPLATE_EMPTY_ERROR)
	@ApiModelProperty(value = "模板ID")
	private String templateId;

	@ApiModelProperty(value = "任务分类")
	private String category;

	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;
}
