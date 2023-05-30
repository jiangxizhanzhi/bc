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

@Data
public class TeamTrainTemplateRoleRsp implements Serializable {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "角色id")
	private String id;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	@ApiModelProperty(value = "任务编排")
	private ColorMission<AddTemplateMission> missionsScheme;

	@ApiModelProperty(value = "资产节点")
	private List<CrRoleNode> assetNodes;

	@ApiModelProperty(value = "子角色，冗余")
	private List<String> children;

	@ApiModelProperty(value = "战争迷雾")
	private List<CrRoleNode> visibleNodes;

	@ApiModelProperty(value = "KEY（不知道有什么用）")
	private String key;

	@ApiModelProperty(value = "包含的用户")
	private String users;

	@ApiModelProperty(value = "类型")
	private String type;

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
