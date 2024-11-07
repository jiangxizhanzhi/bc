package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模板修改请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionTemplateModifyReq extends MissionTemplateReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

}
