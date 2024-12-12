package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import lombok.*;

/**
 * 纸
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AuthTable
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_paper")
public class Paper extends AuthTable {

	/**
	 * 资源id
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
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 图标
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 实践计数
	 *
	 * @see Integer
	 */
	private Integer practiceCount;

	/**
	 * 总分
	 *
	 * @see Double
	 */
	private Double totalScore;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@TableField("public")
	private Boolean isPublic;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type = PaperType.LIB.getCode();

	public enum PaperType implements GenericEnum {

		LIB(1, "试卷库"),

		EXAM(2, "考试"),

		LESSON(3, "课后练习");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		PaperType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
