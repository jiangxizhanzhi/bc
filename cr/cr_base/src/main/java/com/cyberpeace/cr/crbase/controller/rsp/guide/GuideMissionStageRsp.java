package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 导调任务阶段响应
 *
 * @author myy@date 2024/11/12
 */
@Data
public class GuideMissionStageRsp {

	/**
	 * 阶段名称
	 */
	@ApiModelProperty("阶段名称")
	private String stageName;

	/**
	 * 任务列表
	 */
	@ApiModelProperty("任务列表")
	private List<GuideMissionRsp> missionList;


}
