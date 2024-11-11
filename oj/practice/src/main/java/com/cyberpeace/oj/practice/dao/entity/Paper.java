package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import lombok.*;

/**
 * 试卷
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_paper")
public class Paper extends AuthTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 练习计数
	 */
	private Integer practiceCount;

	/**
	 * 总分
	 */
	private Double totalScore;

	/**
	 * 是否公开
	 */
	@TableField("public")
	private Boolean isPublic;

	/**
	 * 类型
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
