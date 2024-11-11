package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 练习理论
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("oj_practice_theory")
public class PracticeTheory extends PracticeBase {

	/**
	 * 理论类型
	 */
	@ApiModelProperty("理论基础题型")
	private Integer theoryType;

	/**
	 * 空白
	 */
	@ApiModelProperty("填空题填空内容")
	private String blank;

	/**
	 * 选择
	 */
	@ApiModelProperty("选择题选项")
	@TableField("`option`")
	private String option;

	/**
	 * 关键词
	 */
	@ApiModelProperty("简答题参考关键字")
	private String keyword;

	public enum TheoryType implements GenericEnum {

		SINGLE(0, "single"), MULTIPLE(1, "multiple"), JUDGMENT(2, "judgment"), FILL_BLANK(3, "fill_blank"), SHORT_ANSWER(4,
				"short_answer"),
		/** 不属于基础练习本身*/
		OPERATION(5, "operation");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TheoryType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
