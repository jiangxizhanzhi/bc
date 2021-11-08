package com.cyberpeace.cr.crbase.dao.entity;

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

	private Integer missionStage;

	public enum MissionType {

		JUDGE(0, "判断"), SINGLE__CHOICE(1, "单选"), MULTIPLE_CHOICE(2, "多选"), BLANK(3, "填空"), FAQ(4,
				"问答"), ATTACK(5, "攻击"), DEFENCE(6, "防御"), FILE(7, "文件");

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
