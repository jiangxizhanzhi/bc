package com.cyberpeace.cr.crbase.dao.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.model.CommonContext;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;

/**
 * 
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

	public CrRole(String role, Integer code) {
		this.name = role;
		this.roleGroup = code;
	}

	public CrRole() {

	}

	public enum TeamRoleGroupCode implements GenericEnum {

		TEACHER(1, "教员"), STUDENT(2, "学员");

		@Getter
		private Integer code;

		@Getter
		private final String desc;

		TeamRoleGroupCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public enum RbRoleGroupCode implements GenericEnum {

		ADMIN(1, "管理员", "Administrator"), DIRECTOR(2, "导演", "Director"), REFEREE(3, "裁判",
				"Referee"), RED(4, "红方",
						"Red"), BLUE(5, "蓝方", "Blue"), RED_BLUE(6, "红方蓝方", "RedBlue");

		@Getter
		private Integer code;

		private String desc;

		private String enDesc;

		@Override
		public String getDesc() {
			if (StringUtils.equals(CommonContext.getLanguage(), WebConstant.LANGUAGE_EN)) {
				return enDesc;
			} else {
				return desc;
			}
		}

		RbRoleGroupCode(Integer code, String desc, String enDesc) {
			this.code = code;
			this.desc = desc;
			this.enDesc = enDesc;

		}
	}

	public interface SingleRoleGroupCode {

		String name = "单兵训练角色";

		Integer role = 1;

		Integer roleGroup = 1;
	}

	public Boolean isStudent() {
		boolean result = false;
		if (BusinessTemplate.BusinessType.RED_VS_BLUE.getCode().equals(this.businessType)) {
			result = result || roleGroup.intValue() == RbRoleGroupCode.RED.getCode();
			result = result || roleGroup.intValue() == RbRoleGroupCode.BLUE.getCode();
		}
		if (BusinessTemplate.BusinessType.TRAINING_TEAM.getCode().equals(this.businessType)) {
			result = result || roleGroup.intValue() == TeamRoleGroupCode.STUDENT.getCode();
		}

		return result;
	}

	public final static List<Integer> RB_STUDENT_ROLE_GROUPS = Lists.newArrayList();

	static {
		RB_STUDENT_ROLE_GROUPS.add(RbRoleGroupCode.RED.getCode());
		RB_STUDENT_ROLE_GROUPS.add(RbRoleGroupCode.BLUE.getCode());
	}

}