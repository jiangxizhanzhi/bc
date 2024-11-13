package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导调岗位阶段响应
 *
 * @author myy@date 2024/11/12
 */
@Data
public class GuideRoleStageRsp implements Serializable {

	/**
	 * 岗位名称
	 */
	@ApiModelProperty("角色名称")
	private String roleName;

	/**
	 * 阶段列表
	 */
	@ApiModelProperty("阶段任务")
	private List<GuideMissionStageRsp> stageList;

}
