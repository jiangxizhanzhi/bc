package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板修改请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionTemplateModifyReq extends MissionTemplateReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

}
