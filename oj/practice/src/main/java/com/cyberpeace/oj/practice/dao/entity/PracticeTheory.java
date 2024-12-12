package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 实践理论
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeBase
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_practice_theory")
public class PracticeTheory extends PracticeBase {

	/**
	 * 理论类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("理论基础题型")
	private Integer theoryType;

	/**
	 * 空白
	 *
	 * @see String
	 */
	@ApiModelProperty("填空题填空内容")
	private String blank;

	/**
	 * 选择
	 *
	 * @see String
	 */
	@ApiModelProperty("选择题选项")
	@TableField("`option`")
	private String option;

	/**
	 * 关键词
	 *
	 * @see String
	 */
	@ApiModelProperty("简答题参考关键字")
	private String keyword;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型，常量type=50， 1：视频题，2：图片题，3：文字题")
	private Integer type;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "视频链接")
	private String video;

	public enum TheoryType implements GenericEnum {

		/**
		 * 单选
		 *
		 * @see TheoryType
		 */
		SINGLE(0, "single"),
		/**
		 * 多选
		 *
		 * @see TheoryType
		 */
		MULTIPLE(1, "multiple"),
		/**
		 * 判断
		 *
		 * @see TheoryType
		 */
		JUDGMENT(2, "judgment"),
		/**
		 * 填空
		 *
		 * @see TheoryType
		 */
		FILL_BLANK(3, "fill_blank"),
		/**
		 * 简答
		 *
		 * @see TheoryType
		 */
		SHORT_ANSWER(4,
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
