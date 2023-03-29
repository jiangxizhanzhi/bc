package com.cyberpeace.situation.controller.req;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReplayInstanceReq {

	@ApiModelProperty()
	@NotBlank(groups = { Update.class }, message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;

	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "重演大屏名称", required = true)
	private String name;

	@ApiModelProperty(value = "大屏描述")
	private String description;

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

	@ApiModelProperty(value = "重演模式")
	private Integer replayMode;

	@ApiModelProperty(value = "特定事件")
	private List<List<String>> eventList = Lists.newArrayList();

	@NotBlank(groups = Update.class, message = SituationErrorCode.TEMPLATE_EMPTY_ERROR)
	@ApiModelProperty(value = "模板ID")
	private String templateId;

	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	@ApiModelProperty(value = "重演开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@ApiModelProperty(value = "重演结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
}
