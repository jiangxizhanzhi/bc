package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 课时更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see LessonAddReq
 * @since 1.0.0
 */
@Data
@ApiModel
public class LessonUpdateReq extends LessonAddReq {

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	/**
	 * 讲义类型
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "handout_url", value = "讲义类型")
	private String handoutUrl;

	/**
	 * 视频地址
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "video_url", value = "视频地址")
	private String videoUrl;

	public void setResource_id(String resource_id) {
		this.resourceId = resource_id;
	}

	public void setHandout_url(String handoutUrl) {
		this.handoutUrl = handoutUrl;
	}

	public void setVideo_url(String videoUrl) {
		this.videoUrl = videoUrl;
	}
}
