package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
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

	/** 简介*/
	@TableField("`desc`")
	private String desc;

	/** 封面*/
	private String logo;

	/** 练习类型*/
	private Integer practiceType;

	/** 是否公开*/
	@TableField("public")
	private Boolean isPublic;

	/** 创建者*/
	private String createUserId;

	/** 习题集所含习题数量*/
	private Integer practiceCount;

	/** 习题集来源 */
	private String source;

	public enum PracticeType implements GenericEnum, MdConvertEnum<Integer, String> {

		THEORY(0, "理论基础"), OPERATION(2, "CTF解题");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		PracticeType(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}


		@Override
		public Integer to() {
			return code;
		}

		@Override
		public String reTo() {
			return desc;
		}
	}

}
