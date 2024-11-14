package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.common.model.NameCount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗模板列表响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbTemplateListRsp {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "模板id")
	private String resourceId;

	/**
	 * 基础业务模板id
	 */
	@ApiModelProperty(value = "基础业务模板id")
	private String businessTemplateId;

	/**
	 * 基本信息
	 */
	@ApiModelProperty(value = "基本信息")
	private BaseInfoRsp crEventInfo;

	/**
	 * 节点数
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	/**
	 * 角色信息
	 */
	@ApiModelProperty(value = "角色信息")
	private List<RbTemplateRoleRsp> roleInfo;

	/**
	 * 拓扑中占用的授权节点数量
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 是否关联 ATT&CK
	 */
	@ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

	/**
	 * 关联 ATT&CK 计数
	 */
	@ApiModelProperty(value = "关联 ATT&CK 计数")
	private List<NameCount> attackCount;

	/**
	 * 是否关联 ATT&CK ICS
	 */
	@ApiModelProperty("是否关联 ATT&CK ICS")
	private Boolean isIcsRelated;

	/**
	 * 关联 ATT&CK ICS 计数
	 */
	@ApiModelProperty(value = "关联 ATT&CK ICS 计数")
	private List<NameCount> icsCount;

	/**
	 * 是否关联 Engage
	 */
	@ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

	/**
	 * 关联 Engage 计数
	 */
	@ApiModelProperty(value = "关联 Engage 计数")
	private List<NameCount> engageCount;

}
