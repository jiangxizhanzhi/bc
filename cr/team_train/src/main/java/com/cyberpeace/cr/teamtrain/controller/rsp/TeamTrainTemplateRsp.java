package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.BaseRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateRsp implements Serializable {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "基础信息")
	private BaseInfoRsp baseInfo;

	@ApiModelProperty(value = "建议人数")
	private Integer peopleNumber;

	@ApiModelProperty(value = "角色列表")
	private List<TeamTrainTemplateRoleRsp> roleList;

	@ApiModelProperty(value = "角色结构")
	private List<BaseRole> roleStructure;

	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	@ApiModelProperty(value = "时间轴配置")
	private Object timeLineConfig = "{}";

}
