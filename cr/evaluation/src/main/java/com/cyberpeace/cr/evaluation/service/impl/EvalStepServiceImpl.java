package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepLoadDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.LoadSetReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestStepListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalInstanceStep;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStep;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStepTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalStepTemplateMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestInstanceMapper;
import com.cyberpeace.cr.evaluation.service.EvalStepService;
import com.cyberpeace.integratation.service.RemoteLoadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalStepServiceImpl extends ServiceImpl<EvalStepMapper, EvalStep> implements EvalStepService {

	@Autowired
	private EvalStepMapper evalStepMapper;

	@Autowired
	private EvalTestInstanceMapper evalTestInstanceMapper;

	@Autowired
	private EvalStepTemplateMapper evalStepTemplateMapper;

	@Autowired
	private EvalInstanceStepMapper evalInstanceStepMapper;

	@Autowired
	private RemoteLoadService remoteLoadService;

	@Override
	public Map<String, String> createByTemplate(List<String> templateIdList, String testId) {
		if (CollectionUtils.isEmpty(templateIdList)) {
			return new HashMap<>();
		}
		List<EvalStepTemplate> stepTemplateList = evalStepTemplateMapper.selectBatchIds(templateIdList);
		Map<String, EvalStepTemplate> stepTemplateMap = stepTemplateList.stream()
																		.collect(Collectors.toMap(EvalStepTemplate::getResourceId, evalStepTemplate -> evalStepTemplate));
		List<EvalStep> stepList = new ArrayList<>();
		Map<String, String> stepIdMap = new HashMap<>();
		for (int i = 0; i < templateIdList.size() ; i++) {
			String resourceId = templateIdList.get(i);
			EvalStepTemplate evalStepTemplate = stepTemplateMap.get(resourceId);
			EvalStep evalStep = new EvalStep();
			evalStep.setTestId(testId);
			evalStep.setName(evalStepTemplate.getName());
			evalStep.setProcess(evalStepTemplate.getProcess());
			evalStep.setSetting(evalStepTemplate.getSetting());
			evalStep.setStage(i);
			stepList.add(evalStep);
		}
		saveBatch(stepList);
		return stepIdMap;
	}

	@Override
	public List<EvalStep> selectByTestId(String testId) {
		return evalStepMapper.selectAllByTestId(testId);
	}

	@Override
	public List<EvalTestStepListRsp> getSteps(String testId) {
		List<EvalStep> stepList = evalStepMapper.selectAllByTestId(testId);
		List<EvalTestStepListRsp> stepListRsps = new ArrayList<>();
		for (EvalStep evalStep : stepList) {
			EvalTestStepListRsp stepListRsp = new EvalTestStepListRsp();
			stepListRsp.setStepId(evalStep.getResourceId());
			stepListRsp.setName(evalStep.getName());
			stepListRsp.setProgress(evalStep.getProcess());
			stepListRsp.setStage(evalStep.getStage());

			List<String> loadIds = evalStep.getLoadList().stream().map(LoadSetReq::getLoadId).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(loadIds)) {
				stepListRsp.setLoadList(remoteLoadService.getLoadByIds(loadIds));
			} else {
				stepListRsp.setLoadList(new JSONArray());
			}
			stepListRsps.add(stepListRsp);
		}
		return stepListRsps;
	}

	@Override
	public List<EvalTestStepListRsp> getInstanceSteps(String instanceId) {
		EvalTestInstance testInstance = evalTestInstanceMapper.selectById(instanceId);
		List<EvalTestStepListRsp> evalTestStepListRspList = new ArrayList<>();
		List<EvalInstanceStep> instanceStepList = evalInstanceStepMapper.selectByInstanceId(instanceId);
		List<String> stepIds = instanceStepList.stream().map(EvalInstanceStep::getStepId).collect(Collectors.toList());
		List<EvalStep> stepList = evalStepMapper.selectBatchIds(stepIds);
		Map<String, EvalStep> stepMap = stepList.stream().collect(Collectors.toMap(EvalStep::getResourceId, e -> e));
		for (int i = 0; i < instanceStepList.size(); i++ ) {
			EvalInstanceStep instanceStep = instanceStepList.get(i);
			EvalStep step = stepMap.get(instanceStep.getStepId());
			EvalTestStepListRsp evalTestStepListRsp = BeanUtils.copy(step, EvalTestStepListRsp.class);
			evalTestStepListRsp.setStepId(instanceStep.getResourceId());
			evalTestStepListRsp.setStartTime(TimeUtil.format1(instanceStep.getStartTime()));
			evalTestStepListRsp.setEndTime(TimeUtil.format1(instanceStep.getEndTime()));
			if (testInstance.getInstanceName() != null) {
				evalTestStepListRsp.setPreStepStatus(EvalTestStepListRsp.Status.INIT.getCode());
				evalTestStepListRsp.mergeStatus();
			} else if (i == 0) {
				evalTestStepListRsp.setPreStepStatus(EvalTestStepListRsp.Status.FINISHED.getCode());
			} else {
				evalTestStepListRsp.setPreStepStatus(evalTestStepListRspList.get(i - 1).getStatus());
			}

			List<String> loadIds = step.getLoadList().stream().map(LoadSetReq::getLoadId).collect(Collectors.toList());
			evalTestStepListRsp.setLoadList(remoteLoadService.getLoadByIds(loadIds));
			evalTestStepListRspList.add(evalTestStepListRsp);
		}
		Collections.sort(evalTestStepListRspList, Comparator.comparing(EvalTestStepListRsp::getStage));
		return evalTestStepListRspList;
	}

	@Override
	public Boolean startStep(String stepId) {
		EvalInstanceStep evalInstanceStep = evalInstanceStepMapper.selectById(stepId);
		evalInstanceStep.setStartTime(new Date());
		evalInstanceStepMapper.updateById(evalInstanceStep);
		return true;
	}

	@Override
	public Boolean finishStep(String stepId) {
		EvalInstanceStep evalInstanceStep = evalInstanceStepMapper.selectById(stepId);
		evalInstanceStep.setEndTime(new Date());
		evalInstanceStepMapper.updateById(evalInstanceStep);
		return true;
	}

	@Override
	public void deleteLoad(EvalStepLoadDeleteReq deleteReq) {
		String stepId = deleteReq.getStepId();
		EvalInstanceStep evalInstanceStep = evalInstanceStepMapper.selectByStepId(stepId);
		if (evalInstanceStep != null) {
			stepId = evalInstanceStep.getStepId();
		}
		EvalStep evalStep = evalStepMapper.selectById(stepId);
		List<LoadSetReq> loadList = evalStep.getLoadList();
		loadList.stream().filter(load -> !load.getLoadId().equals(deleteReq.getLoadId())).collect(Collectors.toList());
		evalStep.setSettingList(loadList);
		evalStepMapper.updateById(evalStep);
	}

	@Override
	public void addLoad(EvalStepLoadDeleteReq addReq) {
		EvalStep evalStep = evalStepMapper.selectById(addReq.getLoadId());
		List<LoadSetReq> loadList =  evalStep.getLoadList();
		LoadSetReq loadSetReq = new LoadSetReq();
		loadSetReq.setLoadId(addReq.getLoadId());
		loadList.add(loadSetReq);
		evalStep.setSettingList(loadList);
		evalStepMapper.updateById(evalStep);
	}


}
