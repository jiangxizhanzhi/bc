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

	@TableField("`option`")
	private String option;

	private String keyword;

	public enum TheoryType implements GenericEnum {

		SINGLE(0, "单选"), MULTIPLE(1, "多选"), JUDGMENT(2, "判断"),
		FILL_BLANK(3, "填空"), SHORT_ANSWER(4, "简答");

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
