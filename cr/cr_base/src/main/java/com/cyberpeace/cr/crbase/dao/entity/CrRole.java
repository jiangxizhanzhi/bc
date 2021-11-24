package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;

/**
 * 
 * @TableName cr_role
 */
@TableName(value = "cr_role")
@Data
public class CrRole implements Serializable {

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

	public enum TeamRoleGroupCode {

		TEACHER(1), STUDENT(2);

		@Getter
		private final Integer value;

		TeamRoleGroupCode(Integer value) {
			this.value = value;
		}
	}

	public enum RbRoleGroupCode {

		ADMIN(1, "管理员"), DIRECTOR(2, "导演"), REFEREE(3, "裁判"), RED(4, "红方"), BLUE(5, "蓝方");

		@Getter
		private Integer code;

		@Getter
		private String role;

		RbRoleGroupCode(Integer code, String role) {
			this.code = code;
			this.role = role;
		}
	}

}