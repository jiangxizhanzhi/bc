package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author myy
 */
@Data
public class GuideRoleStageRsp implements Serializable {

	@ApiModelProperty("角色名称")
	private String roleName;

	@ApiModelProperty("阶段任务")
	private List<GuideMissionStageRsp> stageList;

}
