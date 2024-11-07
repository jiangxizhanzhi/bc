package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.common.model.NameCount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB模板列表响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbTemplateListRsp {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "模板id")
	private String resourceId;

	/**
	 * 业务模板id
	 */
	@ApiModelProperty(value = "基础业务模板id")
	private String businessTemplateId;

	/**
	 * 靶场事件信息
	 */
	@ApiModelProperty(value = "基本信息")
	private BaseInfoRsp crEventInfo;

	/**
	 * 节点计数
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	/**
	 * 角色信息
	 */
	@ApiModelProperty(value = "角色信息")
	private List<RbTemplateRoleRsp> roleInfo;

	/**
	 * 资源占用数
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 与攻击有关
	 */
	@ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

	/**
	 * 攻击计数
	 */
	@ApiModelProperty(value = "关联 ATT&CK 计数")
	private List<NameCount> attackCount;

	/**
	 * 是否与 IC 相关
	 */
	@ApiModelProperty("是否关联 ATT&CK ICS")
	private Boolean isIcsRelated;

	/**
	 * 集成电路计数
	 */
	@ApiModelProperty(value = "关联 ATT&CK ICS 计数")
	private List<NameCount> icsCount;

	/**
	 * 与 engage 相关
	 */
	@ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

	/**
	 * 聘计数
	 */
	@ApiModelProperty(value = "关联 Engage 计数")
	private List<NameCount> engageCount;

}
