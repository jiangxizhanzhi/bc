package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.common.model.NameCount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateListRsp {

	@ApiModelProperty(value = "模板id")
	private String resourceId;

	@ApiModelProperty(value = "基本信息")
	private BaseInfoRsp crEventInfo;

	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	@ApiModelProperty(value = "角色信息")
	private List<RbTemplateRoleRsp> roleInfo;

	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	@ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

	@ApiModelProperty(value = "关联 ATT&CK 计数")
	private List<NameCount> attackCount;

	@ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

	@ApiModelProperty(value = "关联 Engage 计数")
	private List<NameCount> engageCount;

}
