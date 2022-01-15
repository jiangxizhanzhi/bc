package com.cyberpeace.cr.crbase.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
public class MissionBase extends BaseTable {

	protected Integer score;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String title;

	private String description;

	private String skillPoints;

	private String answer;

	private String option;

	private Integer type;

	private String categoryId;

	private String writeUp;

	private String createUserId;

	public enum MissionType {

		JUDGE(0, "判断"), SINGLE_CHOICE(1, "单选"), MULTIPLE_CHOICE(2, "多选"), BLANK(3, "填空"), FAQ(4,
				"问答"), ATTACK(5, "攻击"), DEFENCE(6, "防御"), FILE(7, "文件");

		public static final List<Integer> OBJECTIVE_TYPE_LIST = new ArrayList<>(
				Arrays.asList(JUDGE.code, SINGLE_CHOICE.code, MULTIPLE_CHOICE.code, FILE.code));

		public static final List<Integer> ANSWER_NULLABLE_TYPE_LIST = new ArrayList<>(
				Arrays.asList(FAQ.code, ATTACK.code, DEFENCE.code, FILE.code));

		public static final List<Integer> HAS_OPTION_TYPE_LIST = new ArrayList<>(
				Arrays.asList(SINGLE_CHOICE.code, MULTIPLE_CHOICE.code));

		@Getter
		private Integer code;

		@Getter
		private String desc;

		MissionType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
