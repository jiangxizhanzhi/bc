package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

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

		ADMIN(6, "管理员"), DIRECTOR(7, "导演"), REFEREE(3, "裁判"), RED(4, "红方"), BLUE(5, "蓝方");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		RbRoleGroupCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}