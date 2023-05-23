package com.cyberpeace.cr.crbase.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class MissionBase extends BaseTable {

	@ApiModelProperty(value = "分值")
	protected Integer score;

	@ApiModelProperty(value = "任务id")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty(value = "任务名称")
	private String title;

	@ApiModelProperty(value = "任务描述")
	private String description;

	@ApiModelProperty(value = "技能点")
	private String skillPoints;

	@ApiModelProperty(value = "任务答案")
	private String answer;

	@ApiModelProperty(value = "选项")
	private String option;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "类别")
	private String categoryId;

	@ApiModelProperty(value = "题解")
	private String writeUp;

	@ApiModelProperty(value = "创建者id")
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

	public Boolean isObjective() {
		Integer missionType = getType();
		return isObjective(missionType);
	}

	public static Boolean isObjective(Integer type) {
		if (type == null) {
			return false;
		}
		boolean result = type.intValue() == MissionType.JUDGE.getCode();
		result = result || (type.intValue() == MissionType.SINGLE_CHOICE.getCode());
		result = result || (type.intValue() == MissionType.MULTIPLE_CHOICE.getCode());
		result = result || (type.intValue() == MissionType.FILE.getCode());

		return result;
	}
}
