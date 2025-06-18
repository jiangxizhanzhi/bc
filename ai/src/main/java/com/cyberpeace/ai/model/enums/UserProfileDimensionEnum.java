package com.cyberpeace.ai.model.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户画像维度枚举")
public enum UserProfileDimensionEnum {

	COURSE_LEARNING(1, "课程学习"), 
	PRACTICE(2, "题目练习"), 
	EVALUATION(3, "综合训练"), 
	TRAINING(4, "岗位评估");

	@ApiModelProperty("维度int值")
	private final Integer value;

	@ApiModelProperty("维度名称")
	private final String label;

	UserProfileDimensionEnum(Integer value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getValueStr() {
		return String.valueOf(value);
	}

	public String getLabel() {
		return label;
	}

	public static UserProfileDimensionEnum fromValue(Integer value) {
		for (UserProfileDimensionEnum e : values()) {
			if (e.value.equals(value))
				return e;
		}
		return null;
	}
}