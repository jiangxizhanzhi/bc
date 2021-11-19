package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cr_mission")
public class Mission extends MissionBase {

	private Boolean showWriteup;

	private String roomId;

	public Boolean isObjective() {
		Integer type = getType();
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
