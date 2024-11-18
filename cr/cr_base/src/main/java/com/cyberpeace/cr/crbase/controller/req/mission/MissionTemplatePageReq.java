package com.cyberpeace.cr.crbase.controller.req.mission;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @since 1.0.0
 */
public class MissionTemplatePageReq extends PageReqBase {

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型")
	@Getter
	@Setter
	private Integer type;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	@Setter
	@Getter
	private Integer difficulty;
}
