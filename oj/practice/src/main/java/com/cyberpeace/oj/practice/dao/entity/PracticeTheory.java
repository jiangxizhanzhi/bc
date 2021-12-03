package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_practice_theory")
public class PracticeTheory extends PracticeBase {

	private Integer theoryType;

	private String blank;

	@TableField("`option`")
	private String option;

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
