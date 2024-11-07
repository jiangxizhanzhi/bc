package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

/**
 * 团队训练模板角色响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TeamTrainTemplateRoleRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * id
	 */
	@ApiModelProperty(value = "角色id")
	private String id;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 任务计划
	 */
	@ApiModelProperty(value = "任务编排")
	private ColorMission<AddTemplateMission> missionsScheme;

	/**
	 * 资产节点
	 */
	@ApiModelProperty(value = "资产节点")
	private List<CrRoleNode> assetNodes;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子角色，冗余")
	private List<String> children;

	/**
	 * 可见节点
	 */
	@ApiModelProperty(value = "战争迷雾")
	private List<CrRoleNode> visibleNodes;

	/**
	 * 钥匙
	 */
	@ApiModelProperty(value = "KEY（不知道有什么用）")
	private String key;

	/**
	 * 用户
	 */
	@ApiModelProperty(value = "包含的用户")
	private String users;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String type;

	/**
	 * 资产地图
	 */
	private Map<String,Integer>  assetMap;


	public void initAssetMap() {
		assetMap = new HashMap<>();
		if (CollectionUtils.isEmpty(assetNodes)) {
			for (CrRoleNode.CategoryGroup value : CrRoleNode.CategoryGroup.values()) {
				assetMap.put(value.getEnglish(),0);
			}
			return;
		}
		Map<Integer, List<CrRoleNode>> nodeMap = assetNodes.stream().collect(Collectors.groupingBy(CrRoleNode::getCategory));
		for (CrRoleNode.CategoryGroup value : CrRoleNode.CategoryGroup.values()) {
			assetMap.put(value.getEnglish(),nodeMap.getOrDefault(value.getCode(),new ArrayList<>()).size());
		}
	}

}
