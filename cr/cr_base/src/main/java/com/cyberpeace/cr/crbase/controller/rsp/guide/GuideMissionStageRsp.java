package com.cyberpeace.cr.crbase.controller.rsp.guide;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author myy
 */
@Data
public class GuideMissionStageRsp {

	@ApiModelProperty("阶段名称")
	private String stageName;

	@ApiModelProperty("任务列表")
	private List<GuideMissionRsp> missionList;


}
