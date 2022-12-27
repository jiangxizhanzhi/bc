package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdDashboardReq implements Serializable {

	@ApiModelProperty(value = "大屏名称")
	private String name;

	@ApiModelProperty(value = "大屏描述")
	private String description;

	@ApiModelProperty(value = "缩略图")
	private MultipartFile thumbnail;

	@ApiModelProperty(value = "分靶场ID")
	private String subRangeId;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@ApiModelProperty(value = "可视化方案ID")
	private String schemeId;

	@ApiModelProperty(value = "跳转链接")
	private String thirdUrl;
}
