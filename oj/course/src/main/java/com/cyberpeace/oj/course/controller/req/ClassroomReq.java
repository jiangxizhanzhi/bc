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

/**
 * 课堂请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class ClassroomReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课程表编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	/**
	 * 课程id
	 */
	@ApiModelProperty("课程编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL)
	private String courseId;

	/**
	 * 课id
	 */
	@ApiModelProperty("课时编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private String lessonId;

	/**
	 * 组织id
	 */
	@ApiModelProperty("组织编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL)
	private String organizationId;

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始时间")
	@NotNull(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.START_TIME_NOT_NULL)
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	@NotNull(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.END_TIME_NOT_NULL)
	private Date endTime;

	/**
	 * 训练计划id
	 */
	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

}
