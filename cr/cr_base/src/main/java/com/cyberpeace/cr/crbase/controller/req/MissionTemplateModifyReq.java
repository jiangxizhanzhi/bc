package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模板修改请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionTemplateModifyReq extends MissionTemplateReq {

	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

}
