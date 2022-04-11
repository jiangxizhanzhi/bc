package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_practice_random_flag")
public class PracticeRandomFlag extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String userId;

	private String attachmentId;

	private String practiceId;

	private String flag;

	private Integer type;

	public enum FlagType {

		PRACTICE(0, "习题"), LESSON_PRACTICE(1, "课后练习");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		FlagType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
