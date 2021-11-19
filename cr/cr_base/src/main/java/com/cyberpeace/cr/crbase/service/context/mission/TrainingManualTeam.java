package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleMissionSchemeStage;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;
import lombok.Getter;

@Data
public class TrainingManualTeam {

	private StringBuffer text;

	private List<CrRoleMissionSchemeStage> stages;

	private Map<String, Mission> missionMap;

	private TemplateLocale locale;

	public void addStage(CrRoleMissionSchemeStage stage, Integer index) {
		String head = String.format("\n\n## %s%d： %s\n\n\n", locale.getStage(), index,
				stage.getStage());
		text.append(head);

		for (int i = 1; i <= stage.getMissions().size(); i++) {
			addMission(stage.getMissions().get(i - 1), i);
		}
		text.append("\n---\n");
	}

	public void addMission(String missionId, Integer index) {
		Mission mission = missionMap.get(missionId);
		String missionText = String.format("#### %s%d： %s\n\n%s: \n%s\n\n\n", locale.getTask(),
				index,
				mission.getTitle(), locale.getWriteup(), mission.getWriteUp());
		text.append(missionText);
	}

	public TrainingManualTeam(List<CrRoleMissionSchemeStage> stages,
			Map<String, Mission> missionMap, String language) {
		this.text = new StringBuffer();
		this.stages = stages;
		this.missionMap = missionMap;
		this.locale = new TemplateLocale(language);
	}

	public String toMarkDown() {
		for (int i = 1; i <= stages.size(); i++) {
			addStage(stages.get(i - 1), i);
		}
		return text.toString();
	}
}

class TemplateLocale {

	@Getter
	private String stage;

	@Getter
	private String task;

	@Getter
	private String writeup;

	public TemplateLocale(String language) {
		if (language.equals(WebConstant.LANGUAGE_ZH)) {
			this.stage = "阶段";
			this.task = "任务";
			this.writeup = "题解";
		}
		if (language.equals(WebConstant.LANGUAGE_EN)) {
			this.stage = "Stage";
			this.task = "Task";
			this.writeup = "Write Up";
		}
	}
}