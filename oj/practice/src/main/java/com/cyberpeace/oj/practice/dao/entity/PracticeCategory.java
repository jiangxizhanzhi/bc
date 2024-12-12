package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 执业类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_practice_category")
public class PracticeCategory extends BaseTable {

	/**
	 * 资源ID
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 创建者
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 节点编码, 基于父级节点: p1(父节点) -> p1|c1(子节点)
	 *
	 * @see String
	 */
	@JsonIgnore
	private String code;

	/**
	 * 练习类型
	 *
	 * @see Integer
	 */
	@JsonIgnore
	private Integer practiceType;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	private String parentId;

	public enum PracticeType implements GenericEnum {

		THEORY(0, "理论基础"), OPERATION(2, "实践操作");

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
