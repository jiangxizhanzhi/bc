package com.cyberpeace.cr.crbase.dao.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleSimple;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;

/**
 * 靶场角色
 *
 * @author SN
 * @date 2024/11/07
 */
@TableName(value = "cr_role")
@Data
public class CrRole extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 角色组
	 */
	private Integer roleGroup;

	/**
	 * 节点
	 */
	private String nodes;


	/**
	 * 节点配置快照
	 */
	private String nodesSnapshot;

	/**
	 * 用户id列表
	 */
	private String userIds;

	/**
	 * 练习纸
	 */
	private String exercisePaper;

	/**
	 * 父母资源id
	 */
	private String parentResourceId;

	/**
	 * 任务计划
	 */
	private String missionScheme;

	/**
	 * 业务类型
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


	public interface RoleGroup {
		/**
		 * 角色类型数字代码
		 */
		Integer getCode();
	}

	public void setRoleGroup(RoleGroup roleGroup) {
		this.roleGroup = roleGroup.getCode();
	}

	/**
	 * 团队训练
	 */

	public boolean isTeamRole() {
		return BusinessTemplate.BusinessType.TRAINING_TEAM.getCode().equals(businessType);
	}

	public enum TeamRoleGroup implements GenericEnum, RoleGroup, BaseI18n {

		/***/
		TEACHER(1, "教员", "Teacher"),
		STUDENT(2, "学员", "Student");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		TeamRoleGroup(Integer code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}

		public boolean equals(Integer roleGroupCode) {
			return getCode().equals(roleGroupCode);
		}

		@Override
		public String getChinese() {
			return this.chinese;
		}

		@Override
		public String getEnglish() {
			return this.english;
		}

		@Override
		public String getDesc() {
			return this.getMsg();
		}
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
		return BusinessTemplate.BusinessType.RED_VS_BLUE.getCode().equals(businessType);
	}

	public enum RbRoleGroup implements GenericEnum, BaseI18n, RoleGroup {
		/***/
		ADMIN(1, "管理员", "Administrator"),
		DIRECTOR(2, "导演", "Director"),
		REFEREE(3, "裁判", "Referee"),
		RED(4, "红方", "Red"),
		BLUE(5, "蓝方", "Blue"),
		RED_BLUE(6, "红方蓝方", "RedBlue"),
		WHITE(7, "白方", "White"),
		TEAM(8, "团队", "Team"),
		RED_GROUP(4, "红方群", "Red Group"),
		BLUE_GROUP(5, "蓝方群", "Blue Group"),
		WHITE_GROUP(7, "白方群", "White Group"),
		TEAM_GROUP(8, "团队群", "Team Group"),
		;

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		@Override
		public String getDesc() {
			return getMsg();
		}

		RbRoleGroup(Integer code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}

		public boolean equals(Integer roleGroupCode) {
			return getCode().equals(roleGroupCode);
		}

		public static boolean isRvbStudentRole(Integer roleGroupCode) {
			return RED.equals(roleGroupCode) || BLUE.equals(roleGroupCode);
		}

		public static List<RbRoleGroup> studentRoleGroups() {
			ArrayList<RbRoleGroup> rbRoleGroups = Lists.newArrayList();
			rbRoleGroups.add(RED);
			rbRoleGroups.add(BLUE);
			return rbRoleGroups;
		}
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
	/**
	 * 单角色群代号
	 */
	public static final Integer SINGLE_ROLE_GROUP_CODE = 1;
}