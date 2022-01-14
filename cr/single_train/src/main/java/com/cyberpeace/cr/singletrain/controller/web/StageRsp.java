package com.cyberpeace.cr.singletrain.controller.web;

import java.util.List;

import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

@Data
public class StageRsp {

	private String stageName;

	private List<StageQuestion> questions;

	public StageRsp(MissionSchemeObject<TrainingRecordMissionRsp> missionScheme) {

		List<StageQuestion> questionList = Lists.newArrayList();
		for (TrainingRecordMissionRsp mission : missionScheme.getMissions()) {


 			StageQuestion stageQuestion= new StageQuestion();

			stageQuestion.setTitle(mission.getTitle());
			stageQuestion.setRecord(mission.getTrainingRecord().getRecord());
			stageQuestion.setTime(mission.getTrainingRecord().getTime());
			stageQuestion.setDescription(mission.getDescription());
			stageQuestion.setRemoteMissionId(mission.getResourceId());

			questionList.add(stageQuestion);
		}

		this.stageName = missionScheme.getStage();
		this.questions = questionList;

	}
}
