package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author myy
 */
@Data
public class GuideMissionStageRsp implements Serializable {

	@ApiModelProperty("阶段名称")
	private String stageName;

	@ApiModelProperty("任务列表")
	private List<GuideMissionRsp> missionList;


}
