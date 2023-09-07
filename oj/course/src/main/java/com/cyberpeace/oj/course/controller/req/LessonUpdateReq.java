package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class LessonUpdateReq extends LessonAddReq {

	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	@ApiModelProperty(name = "handout_url", value = "讲义类型")
	private String handoutUrl;

	@ApiModelProperty(name = "video_url", value = "视频地址")
	private String videoUrl;

	@ApiModelProperty(value = "备课笔记")
	private String prepare_note;

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
