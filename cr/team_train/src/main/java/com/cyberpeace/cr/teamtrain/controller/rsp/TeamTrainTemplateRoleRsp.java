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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TeamTrainTemplateRoleRsp implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色id")
	private String id;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 任务计划
	 *
	 * @see ColorMission<AddTemplateMission>
	 */
	@ApiModelProperty(value = "任务编排")
	private ColorMission<AddTemplateMission> missionsScheme;

	/**
	 * 资产节点
	 *
	 * @see List<CrRoleNode>
	 */
	@ApiModelProperty(value = "资产节点")
	private List<CrRoleNode> assetNodes;

	/**
	 * 子级
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "子角色，冗余")
	private List<String> children;

	/**
	 * 可见节点
	 *
	 * @see List<CrRoleNode>
	 */
	@ApiModelProperty(value = "战争迷雾")
	private List<CrRoleNode> visibleNodes;

	/**
	 * 钥匙
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "KEY（不知道有什么用）")
	private String key;

	/**
	 * 用户
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "包含的用户")
	private String users;

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "类型")
	private String type;

	/**
	 * 资产地图
	 *
	 * @see Map<String, Integer>
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
