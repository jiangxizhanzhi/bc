package com.cyberpeace.common.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.enums.RoleGroup;
import com.cyberpeace.common.enums.TeamRoleGroup;
import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.context.MissionStageScheme;
import com.cyberpeace.common.context.role.CrRoleNodesData;
import com.cyberpeace.common.context.role.CrRoleSimple;

import lombok.Data;

/**
 * @TableName cr_role
 */
@TableName(value = "cr_role")
@Data
public class CrRole extends BaseTable {

	/**
	 *
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private String roomId;

	/**
	 *
	 */
	private String description;

	/**
	 *
	 */
	private Integer roleGroup;

	/**
	 *
	 */
	private String nodes;


	/**
	 * 节点配置快照
	 */
	private String nodesSnapshot;

	/**
	 *
	 */
	private String userIds;

	/**
	 *
	 */
	private String exercisePaper;

	/**
	 *
	 */
	private String parentResourceId;

	/**
	 *
	 */
	private String missionScheme;

	/**
	 *
	 */
	private Integer businessType;

	public CrRole(String name, Integer code) {
		this.name = name;
		this.roleGroup = code;
	}

	public CrRole() {

	}

	public static List<CrRoleSimple> getSimpleRoles(List<CrRole> studentRoles) {
		if (CollectionUtils.isEmpty(studentRoles)) {
			return new ArrayList<>();
		}
		return studentRoles.stream().map(CrRoleSimple::new)
				.collect(Collectors.toList());
	}

	public static CrRole findRoleByUserId(List<CrRole> roleList, String userId) {
		for (CrRole role : roleList) {
			if (role.getUserIds().contains(userId)) {
				return role;
			}
		}
		return null;
	}

	public static CrRole findRoleByRoleId(List<CrRole> roleList, String roleId) {
		for (CrRole role : roleList) {
			if (role.getResourceId().equals(roleId)) {
				return role;
			}
		}
		return null;
	}

	public static Map<String, CrRole> getUserIdCrRoleMap(List<CrRole> crRoles) {
		Map<String, CrRole> res =new HashMap<>();
		for (CrRole crRole : crRoles) {
			for (String userId : crRole.getUserIdList()) {
				res.put(userId, crRole);
			}

		}
		return res;
	}

	public void setRoleGroup(Integer roleGroup) {
		this.roleGroup = roleGroup;
	}

	public List<String> getUserIdList() {
		return JsonUtil.toJsonObjectArray(getUserIds(), String.class);
	}

	public boolean containsUser(String userId) {
		return getUserIdList().contains(userId);
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIds = JsonUtil.toJsonString(userIdList);
	}

	public List<MissionStageScheme> getMissionStageSchemes() {
		return JsonUtil.toJsonObjectArray(getMissionScheme(), MissionStageScheme.class);
	}

	public void setMissionStageSchemes(List<MissionStageScheme> missionStageSchemeList) {
		this.missionScheme = JSONObject.toJSONString(missionStageSchemeList);
	}

	public CrRoleNodesData getNodeData(){
		return JsonUtil.toJsonObject(getNodes(), CrRoleNodesData.class);
	}

	public void setNodesData(CrRoleNodesData nodesData){
		this.nodes = JsonUtil.toJsonString(nodesData);
	}


	public void setRoleGroup(RoleGroup roleGroup) {
		this.roleGroup = roleGroup.getCode();
	}

	/**
	 * 团队训练
	 */

	public boolean isTeamRole() {
		return BusinessType.TRAINING_TEAM.getCode().equals(businessType);
	}

	public boolean isTeamStudent() {
		return isTeamRole() && TeamRoleGroup.STUDENT.equals(getRoleGroup());
	}

	public boolean isTeamTeacher() {
		return isTeamRole() && TeamRoleGroup.TEACHER.equals(getRoleGroup());
	}

	/**
	 * 红蓝对抗
	 */

	public boolean isRvbRole() {
		return BusinessType.RED_VS_BLUE.getCode().equals(businessType);
	}


	public CrRole(RbRoleGroup roleGroup) {
		this(roleGroup, roleGroup.getDesc());
	}

	public CrRole(RbRoleGroup roleGroup, String name) {
		this(name, roleGroup.getCode());
	}

	public boolean isInGroup(RbRoleGroup roleGroup) {
		return isRvbRole() && getRoleGroup().equals(roleGroup.getCode());
	}

	public boolean isRedStudent() {
		return isInGroup(RbRoleGroup.RED);
	}

	public boolean isBlueStudent() {
		return isInGroup(RbRoleGroup.BLUE);
	}

	public boolean isRvbStudent() {
		return isRedStudent() || isBlueStudent();
	}

	public boolean isRvbStaff() {
		return isInGroup(RbRoleGroup.ADMIN) || isInGroup(RbRoleGroup.DIRECTOR) || isInGroup(RbRoleGroup.REFEREE);
	}


	/**
	 * 单兵训练
	 */
	public static final String SINGLE_ROLE_GROUP_NAME = "单兵训练角色";
	public static final Integer SINGLE_ROLE_GROUP_CODE = 1;
}