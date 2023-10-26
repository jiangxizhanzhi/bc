package com.cyberpeace.cr.crbase.controller.rsp.guide;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author myy
 */
@Data
public class GuideRoleStageRsp {

	@ApiModelProperty("角色名称")
	private String roleName;

	@ApiModelProperty("阶段任务")
	private List<GuideMissionStageRsp> stageList;

}
