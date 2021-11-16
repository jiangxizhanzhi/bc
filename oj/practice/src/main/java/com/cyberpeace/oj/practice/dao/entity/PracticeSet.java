package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_practice_set")
public class PracticeSet extends AuthTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private boolean builtin;

	/** 名称*/
	private String name;

	/** 封面*/
	private String logo;

	/** 练习类型*/
	private Integer practiceType;

	/** 是否公开*/
	@TableField("public")
	private boolean isPublic;

	/** 创建者*/
	private String createUserId;

	public enum PracticeType implements GenericEnum {

		THEORY(0, "基础理论"), OPERATION(2, "实操练习");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		PracticeType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
