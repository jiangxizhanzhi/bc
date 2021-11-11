package com.cyberpeace.cr.crbase.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_mission")
public class Mission extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String createUserId;

	private String title;

	private String description;

	private Integer score;

	private String skillPoints;

	private String answer;

	private String option;

	private Integer type;

	private String categoryId;

	private String writeUp;

	private Boolean showWriteup;

	private Integer missionStage;

	private String roomId;

	public enum MissionType {

		JUDGE(0, "判断"), SINGLE_CHOICE(1, "单选"), MULTIPLE_CHOICE(2, "多选"), BLANK(3, "填空"), FAQ(4,
				"问答"), ATTACK(5, "攻击"), DEFENCE(6, "防御"), FILE(7, "文件");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		MissionType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		@Getter
		private static final List<Integer> OBJECTIVE_TYPE_LIST = new ArrayList<>(
				Arrays.asList(JUDGE.getCode(), SINGLE_CHOICE.getCode(), MULTIPLE_CHOICE.getCode(),
						FILE.getCode()));

	}

	public Boolean isObjective() {
		boolean result = type.intValue() == MissionType.JUDGE.code;
		result = result || (type.intValue() == MissionType.SINGLE_CHOICE.code);
		result = result || (type.intValue() == MissionType.MULTIPLE_CHOICE.code);
		result = result || (type.intValue() == MissionType.FILE.code);

		return result;
	}
}
