package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ClassroomReq implements Serializable {

	@ApiModelProperty("课程表编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	@ApiModelProperty("课程编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL)
	private String courseId;

	@ApiModelProperty("课时编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private String lessonId;

	@ApiModelProperty("组织编号")
	private String organizationId;

	@ApiModelProperty("开始时间")
	@NotNull(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.START_TIME_NOT_NULL)
	private Date startTime;

	@ApiModelProperty("结束时间")
	@NotNull(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.END_TIME_NOT_NULL)
	private Date endTime;

	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

}
