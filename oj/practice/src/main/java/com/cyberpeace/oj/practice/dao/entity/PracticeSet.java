package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;
import com.cyberpeace.common.model.AuthTable;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 实践设置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AuthTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_practice_set")
public class PracticeSet extends AuthTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 内置
	 */
	private boolean builtin;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 封面
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 练习类型
	 *
	 * @see Integer
	 */
	private Integer practiceType;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@TableField("public")
	private Boolean isPublic;

	/**
	 * 创建者
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 习题集所含习题数量
	 *
	 * @see Integer
	 */
	private Integer practiceCount;

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
