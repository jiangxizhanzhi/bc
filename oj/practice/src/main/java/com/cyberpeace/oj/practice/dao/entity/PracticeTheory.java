package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;

import com.cyberpeace.oj.practice.entity.PracticeBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_practice_theory")
public class PracticeTheory extends PracticeBase {

	@ApiModelProperty("理论基础题型")
	private Integer theoryType;

	@ApiModelProperty("填空题填空内容")
	private String blank;

	@ApiModelProperty("选择题选项")
	@TableField("`option`")
	private String option;

	@ApiModelProperty("简答题参考关键字")
	private String keyword;

	@ApiModelProperty("是否是真题")
	private Boolean isTrue;

	public enum TheoryType implements GenericEnum {

		SINGLE(0, "单选"), MULTIPLE(1, "多选"), JUDGMENT(2, "判断"), FILL_BLANK(3, "填空"), SHORT_ANSWER(4,
				"简答"),
		/** 不属于基础练习本身*/
		OPERATION(5, "实操");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TheoryType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		public static String getDescByCode(Integer code) {
			for (TheoryType item : TheoryType.values()) {
				if (item.getCode().equals(code)) {
					return item.getDesc();
				}
			}
			return null;
		}
	}

}
