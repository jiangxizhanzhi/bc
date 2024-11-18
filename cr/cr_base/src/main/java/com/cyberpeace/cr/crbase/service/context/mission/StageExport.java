package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;

import lombok.Data;

/**
 * 阶段导出
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class StageExport {

	/**
	 * 阶段名称
	 *
	 * @see String
	 */
	private String stageName;

	/**
	 * 问题
	 *
	 * @see List<StageQuestion>
	 */
	private List<StageQuestion> questions;

	public StageExport(MissionSchemeObject<TrainingRecordMissionRsp> missionScheme) {

		List<StageQuestion> questionList = Lists.newArrayList();
		for (TrainingRecordMissionRsp mission : missionScheme.getMissions()) {

			StageQuestion stageQuestion = new StageQuestion();

			stageQuestion.setTitle(mission.getTitle());
			stageQuestion.setRecord(mission.getTrainingRecord().getRecord());
			stageQuestion.setTime(mission.getTrainingRecord().getTime());
			stageQuestion.setDescription(mission.getDescription());
			stageQuestion.setRemoteMissionId(mission.getResourceId());
			stageQuestion.setTaskEvaluateStatus(mission.getTraininStatus());
			stageQuestion.setTaskEvaluateStatusName(EnumUtils
					.getEnumDescByCode(mission.getTraininStatus(), CrTask.SingleTaskStatus.class));

			questionList.add(stageQuestion);
		}

		this.stageName = missionScheme.getStage();
		this.questions = questionList;

	}
}
