package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@TableName("cr_mission")
public class Mission extends MissionBase {

	@ApiModelProperty(value = "是否显示题解")
	private Boolean showWriteup;

	@ApiModelProperty(value = "房间id")
	private String roomId;

	@ApiModelProperty(value = "任务模板id")
	private String missionTemplateId;

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
