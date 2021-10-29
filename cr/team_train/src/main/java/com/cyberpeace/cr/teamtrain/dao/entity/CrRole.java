package com.cyberpeace.cr.teamtrain.dao.entity;

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

	public enum RoleGroupCodeInteger {

		TEACHER(1), STUDENT(2);

		@Getter
		private final Integer value;

		RoleGroupCodeInteger(Integer value) {
			this.value = value;
		}
	}

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
	private String businessInstanceId;

	/**
	 * 
	 */
	private String description;

	/**
	 * 
	 */
	private String roleGroup;

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
	private String missionSchemeId;

	/**
	 * 
	 */
	private Integer businessType;

}