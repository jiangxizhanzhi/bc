package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

/**
 * 角色简要信息
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
public class CrRoleSimple implements Serializable {

	private String resourceId;

	private String name;

	private Integer roleGroup;

	private List<CrRoleMissionSchemeStage> missionScheme;

	private List<String> userIds;

	public CrRoleSimple(CrRole role) {
		this.resourceId = role.getResourceId();
		this.name = role.getName();
		this.roleGroup = role.getRoleGroup();
		this.missionScheme = JsonUtil.toJsonObjectArray(role.getMissionScheme(),
				CrRoleMissionSchemeStage.class);
		this.userIds = JsonUtil.toJsonObjectArray(role.getUserIds(), String.class);
	}

	public List<String> getMissionIds() {
		return StreamApiUtils.flatMap(missionScheme, CrRoleMissionSchemeStage::getMissions);
	}

	public Integer getTaskCount() {
		return this.userIds.size()
				* missionScheme.stream().mapToInt(stage -> stage.getMissions().size()).sum();
	}
	//属性
	public static List<String> getFieldList(Function<CrRoleSimple,List<String>> function,List<CrRoleSimple>  list) {
		if (CollectionUtils.isEmpty(list)){
			return new ArrayList<>();
		}
		List<String> res = list.stream().map(function).flatMap(Collection::stream).collect(Collectors.toList());
		return res;
	}

	public Map<String,String> getMissionIdStageMap() {
		Map<String,String> map = new HashMap<>();
		for (CrRoleMissionSchemeStage schemeStage : missionScheme) {
			for (String missionId : schemeStage.getDynamic()) {
				map.put(missionId,schemeStage.getStage());
			}
		}

		return map;

	}
}
