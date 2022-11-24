package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("bs_user_model")
public class UserModel extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 简介
	 */
	private String description;

	/**
	 * 用户类型id
	 */
	private String userTypeId;

	/**
	 * 访问设备类型
	 */
	private Integer accessOs;

	/**
	 * 访问配置
	 */
	private Integer accessConfig;

	/**
	 * 访问行为json
	 */
	private String behaviorList;

	/**
	 * 访问习惯
	 */
	private String accessHabit;

	/**
	 * 是否限制带宽
	 */
	private Boolean bandwidthLimited;

	/**
	 * 访问带宽数值，单位 Mbps
	 */
	private Integer bandwidth;

	public enum AccessOs implements GenericEnum {

		CODER(1, "程序员"), TEACHER(2, "教师"), STUDENT(3, "学生");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private AccessOs(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum AccessConfig implements GenericEnum {

		COSTUMER(1, "自定义"), RANDOM(2, "随机"), NORMAL(3, "正态分布");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private AccessConfig(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
