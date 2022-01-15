package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.TemplateMissionId;

import lombok.Data;
import lombok.Getter;

@Data
public class TeamTrainTemplateRole {

	private String name;

	private Integer roleGroup;

	private ColorMission<TemplateMissionId> missionsScheme;

	private List<String> assetNodes;

	private List<String> visibleNodes;

	public enum RoleName {

		TEACHER(1, "授权所有"), STUDENT(2, "自定义");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		RoleName(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}