package com.cyberpeace.cr.evaluation.dao.entity.rb;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @TableName cr_role
 */
@TableName(value = "cr_eval_role")
@Data
public class CrEvalRole extends BaseTable {

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

	private String evalTemplateId;

	public CrEvalRole(String name, Integer code) {
		this.name = name;
		this.roleGroup = code;
	}

	public CrEvalRole() {

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
		TEACHER(1, "导调员", "Teacher"),
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
		REFEREE(3, "主裁判", "Referee"),
		RED(4, "红方", "Red"),
		BLUE(5, "蓝方", "Blue"),
		RED_BLUE(6, "红方蓝方", "RedBlue"),
		REFEREE2(7, "副裁判", "Second Referee");

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


	public CrEvalRole(RbRoleGroup roleGroup) {
		this(roleGroup, roleGroup.getDesc());
	}

	public CrEvalRole(RbRoleGroup roleGroup, String name) {
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