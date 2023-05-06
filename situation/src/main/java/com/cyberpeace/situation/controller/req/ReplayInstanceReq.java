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

/**
 * 重演实例请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class ReplayInstanceReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty()
	@NotBlank(groups = { Update.class }, message = ErrorCode.RESOURCE_NOT_FOUND)
	private String resourceId;

	/**
	 * 重演大屏名称
	 */
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	@ApiModelProperty(value = "重演大屏名称", required = true)
	private String name;

	/**
	 * 大屏描述
	 */
	@ApiModelProperty(value = "大屏描述")
	private String description;

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
	 * 重演模式
	 */
	@ApiModelProperty(value = "重演模式")
	private Integer replayMode;

	/**
	 * 特定事件
	 */
	@ApiModelProperty(value = "特定事件")
	private List<List<String>> eventList = Lists.newArrayList();

	/**
	 * 模板ID
	 */
	@NotBlank(groups = Update.class, message = SituationErrorCode.TEMPLATE_EMPTY_ERROR)
	@ApiModelProperty(value = "模板ID")
	private String templateId;

	/**
	 * 缩略图
	 */
	@ApiModelProperty(value = "缩略图", required = true)
	private String thumbnail;

	/**
	 * 重演开始时间
	 */
	@ApiModelProperty(value = "重演开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**
	 * 重演结束时间
	 */
	@ApiModelProperty(value = "重演结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
}
