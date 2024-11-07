package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.constant.TrainI18n;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;

import lombok.Data;

/**
 * 团队训练手册
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingManualTeam {

	/**
	 * 发短信
	 */
	private StringBuffer text;

	/**
	 * 阶段
	 */
	private List<CrRoleMissionSchemeStage> stages;

	/**
	 * 任务图
	 */
	private Map<String, Mission> missionMap;

	public TrainingManualTeam(List<CrRoleMissionSchemeStage> stages, Map<String, Mission> missionMap) {
		this.text = new StringBuffer();
		this.stages = stages;
		this.missionMap = missionMap;
	}

	public void addStage(CrRoleMissionSchemeStage stage, Integer index) {
		String head = String.format("\n\n## %s%d： %s\n\n\n", TrainI18n.STAGE, index,	stage.getStage());
		text.append(head);

		for (int i = 1; i <= stage.getMissions().size(); i++) {
			addMission(stage.getMissions().get(i - 1), i);
		}
		text.append("\n---\n");
	}

	public void addMission(String missionId, Integer index) {
		Mission mission = missionMap.get(missionId);
		String missionText = String.format("#### %s%d： %s\n\n%s: \n%s\n\n\n", TrainI18n.TASK,
				index, mission.getTitle(), TrainI18n.WRITEUP, mission.getWriteUp());
		text.append(missionText);
	}

	public String toMarkDown() {
		for (int i = 1; i <= stages.size(); i++) {
			addStage(stages.get(i - 1), i);
		}
		return text.toString();
	}
}
