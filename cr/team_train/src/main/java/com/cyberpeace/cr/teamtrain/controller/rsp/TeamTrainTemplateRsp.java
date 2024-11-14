package com.cyberpeace.cr.teamtrain.controller.rsp;


import java.util.List;

import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队训练模板响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamTrainTemplateRsp  {

	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 基础业务模板id
	 */
	@ApiModelProperty(value = "基础业务模板id")
	private String businessTemplateId;

	/**
	 * 基础信息
	 */
	@ApiModelProperty(value = "基础信息")
	private BaseInfoRsp baseInfo;

	/**
	 * 建议人数
	 */
	@ApiModelProperty(value = "建议人数")
	private Integer peopleNumber;

	/**
	 * 角色列表
	 */
	@ApiModelProperty(value = "角色列表")
	private List<TeamTrainTemplateRoleRsp> roleList;

	/**
	 * 角色结构
	 */
	@ApiModelProperty(value = "角色结构")
	private List<BaseRole> roleStructure;

	/**
	 * 节点数
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodesCount;

	/**
	 * 拓扑中占用的授权节点数量
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;

	/**
	 * 时间轴配置
	 */
	@ApiModelProperty(value = "时间轴配置")
	private String timeLineConfig = "{}";

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
	 * 关联 ATT&CK 计数
	 */
	@ApiModelProperty(value = "关联 ATT&CK 计数")
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
