package com.cyberpeace.job;

import com.cyberpeace.cr.evaluation.dao.entity.EvalGuideScheme;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalGuideMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
import com.cyberpeace.cr.evaluation.service.EvalGuideService;
import com.cyberpeace.cr.evaluation.service.context.EvalGuideSceneContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EvalSituationGuideJob {

	@Autowired
	private EvalInstanceStepMapper evalInstanceStepMapper;

	@Autowired
	private EvalGuideService evalGuideService;

	@Autowired
	private EvalGuideMapper evalGuideMapper;

	@Scheduled(fixedRate = 30 * 1000)
	public void pushTaskGuide(){
		List<String> guideIds = evalInstanceStepMapper.selectRunningGuideIds();
		if (CollectionUtils.isEmpty(guideIds)) {
			return;
		}
		List<EvalGuideScheme> schemes = evalGuideMapper.selectBatchIds(guideIds);
		List<EvalGuideSceneContext> contexts = evalGuideMapper.batchSelectSceneIdByGuideId(guideIds);
		Map<String, String> idMap = contexts.stream().collect(Collectors.toMap(EvalGuideSceneContext::getGuideId, EvalGuideSceneContext::getSceneId));
		for (EvalGuideScheme guideScheme : schemes) {
			evalGuideService.pushDataToRoomByScheme(true, idMap.get(guideScheme.getResourceId()), guideScheme);
		}
		schemes.forEach(evalGuideScheme -> evalGuideScheme.setIsEditable(0));
		evalGuideService.updateBatchById(schemes);
	}
}
