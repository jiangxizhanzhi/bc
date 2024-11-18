package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.cyberpeace.common.model.NameCount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB模板列表响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RbTemplateListRsp {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模板id")
	private String resourceId;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "基础业务模板id")
	private String businessTemplateId;

	/**
	 * 靶场事件信息
	 *
	 * @see BaseInfoRsp
	 */
	@ApiModelProperty(value = "基本信息")
	private BaseInfoRsp crEventInfo;

	/**
	 * 节点计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	/**
	 * 角色信息
	 *
	 * @see List<RbTemplateRoleRsp>
	 */
	@ApiModelProperty(value = "角色信息")
	private List<RbTemplateRoleRsp> roleInfo;

	/**
	 * 资源占用数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 与攻击有关
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

	/**
	 * 攻击计数
	 *
	 * @see List<NameCount>
	 */
	@ApiModelProperty(value = "关联 ATT&CK 计数")
	private List<NameCount> attackCount;

	/**
	 * 是否与 IC 相关
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否关联 ATT&CK ICS")
	private Boolean isIcsRelated;

	/**
	 * 集成电路计数
	 *
	 * @see List<NameCount>
	 */
	@ApiModelProperty(value = "关联 ATT&CK ICS 计数")
	private List<NameCount> icsCount;

	/**
	 * 与 engage 相关
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

	/**
	 * 聘计数
	 *
	 * @see List<NameCount>
	 */
	@ApiModelProperty(value = "关联 Engage 计数")
	private List<NameCount> engageCount;

}
