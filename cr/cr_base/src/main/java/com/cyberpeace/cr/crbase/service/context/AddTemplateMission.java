package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加模板任务
 * 任务模板简要信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class AddTemplateMission implements Serializable {

	@ApiModelProperty(value = "任务id")
	private String resourceId;

	@ApiModelProperty(value = "任务标题")
	private String title;

	@ApiModelProperty(value = "任务描述")
	private String description;

}
