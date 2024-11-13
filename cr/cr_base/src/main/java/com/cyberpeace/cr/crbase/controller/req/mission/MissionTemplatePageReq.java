package com.cyberpeace.cr.crbase.controller.req.mission;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板分页请求体
 *
 * @author SN
 * @date 2024/11/08
 */
public class MissionTemplatePageReq extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	@Getter
	@Setter
	private Integer type;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	@Setter
	@Getter
	private Integer difficulty;
}
