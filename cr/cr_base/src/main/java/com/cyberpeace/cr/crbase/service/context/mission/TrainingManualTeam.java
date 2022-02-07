package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;
import java.util.Map;

import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import lombok.Data;
import lombok.Getter;

@Data
public class TrainingManualTeam {

	private StringBuffer text;

	private List<CrRoleMissionSchemeStage> stages;

	private Map<String, Mission> missionMap;

	public TrainingManualTeam(List<CrRoleMissionSchemeStage> stages, Map<String, Mission> missionMap) {
		this.text = new StringBuffer();
		this.stages = stages;
		this.missionMap = missionMap;
	}

	public void addStage(CrRoleMissionSchemeStage stage, Integer index) {
		String head = String.format("\n\n## %s%d： %s\n\n\n", Locale.STAGE, index,	stage.getStage());
		text.append(head);

		for (int i = 1; i <= stage.getMissions().size(); i++) {
			addMission(stage.getMissions().get(i - 1), i);
		}
		text.append("\n---\n");
	}

	public void addMission(String missionId, Integer index) {
		Mission mission = missionMap.get(missionId);
		String missionText = String.format("#### %s%d： %s\n\n%s: \n%s\n\n\n", Locale.TASK,
				index, mission.getTitle(), Locale.WRITEUP, mission.getWriteUp());
		text.append(missionText);
	}

	public String toMarkDown() {
		for (int i = 1; i <= stages.size(); i++) {
			addStage(stages.get(i - 1), i);
		}
		return text.toString();
	}
}


enum Locale implements BaseI18n {
	/***/
	STAGE("阶段", "Stage"),
	TASK("任务", "Task"),
	WRITEUP("题解", "Write Up");

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	Locale(String chinese, String english) {
		this.chinese = chinese;
		this.english = english;
	}
}