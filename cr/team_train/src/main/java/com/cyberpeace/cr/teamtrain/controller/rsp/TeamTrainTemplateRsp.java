package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TeamTrainTemplateRsp implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "基础业务模板id")
	private String businessTemplateId;

	/**
	 * 基地信息
	 *
	 * @see BaseInfoRsp
	 */
	@ApiModelProperty(value = "基础信息")
	private BaseInfoRsp baseInfo;

	/**
	 * 人数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "建议人数")
	private Integer peopleNumber;

	/**
	 * 角色列表
	 *
	 * @see List<TeamTrainTemplateRoleRsp>
	 */
	@ApiModelProperty(value = "角色列表")
	private List<TeamTrainTemplateRoleRsp> roleList;

	/**
	 * 角色结构
	 *
	 * @see List<BaseRole>
	 */
	@ApiModelProperty(value = "角色结构")
	private List<BaseRole> roleStructure;

	/**
	 * 节点计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	/**
	 * 资源占用数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 时间线配置
	 *
	 * @see Object
	 */
	@ApiModelProperty(value = "时间轴配置")
	private Object timeLineConfig = "{}";

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
	@ApiModelProperty(value = "关联 ATT&CK 计数")
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
