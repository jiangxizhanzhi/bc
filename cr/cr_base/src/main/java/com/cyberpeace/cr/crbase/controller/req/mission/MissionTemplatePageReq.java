package com.cyberpeace.cr.crbase.controller.req.mission;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板页面请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class MissionTemplatePageReq extends PageReqBase {

	@ApiModelProperty("类型")
	@Getter
	@Setter
	private Integer type;

	@ApiModelProperty("难度")
	@Setter
	@Getter
	private Integer difficulty;
}
