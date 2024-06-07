package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalInstanceStep;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestInstanceMapper;
import com.cyberpeace.cr.evaluation.service.EvalInstanceStepService;
import com.cyberpeace.cr.evaluation.service.context.EvalLoadLogContext;
import com.cyberpeace.integratation.model.mp.LoadHistory;
import com.cyberpeace.integratation.service.RemoteLoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalInstanceStepServiceImpl extends ServiceImpl<EvalInstanceStepMapper, EvalInstanceStep> implements EvalInstanceStepService {

	@Autowired
	private RemoteLoadService remoteLoadService;

	@Autowired
	private EvalTestInstanceMapper evalTestInstanceMapper;

	@Autowired
	private EvalInstanceStepMapper evalInstanceStepMapper;

	@Override
	public List<EvalCodeStageRsp> getAllStages(String instanceId) {
		return evalInstanceStepMapper.getAllStages(instanceId);
	}

	@Override
	public List<EvalTimeNodeRsp> getPlaybackTime(String instanceId) {

		EvalTestInstance evalTestInstance = evalTestInstanceMapper.selectById(instanceId);
		if (evalTestInstance == null) {
			return new ArrayList<>();
		}
		List<EvalInstanceStep> stepList = evalInstanceStepMapper.selectByInstanceId(instanceId);
		List<EvalLoadLogContext> logList = getLoadLogList(evalTestInstance.getMpSceneId());
		List<EvalTimeNodeRsp> timeNodes = new ArrayList<>();
		for (int i = 0; i< stepList.size(); i++) {
			EvalInstanceStep step = stepList.get(i);
			EvalTimeNodeRsp evalTimeNodeRsp = new EvalTimeNodeRsp();
			evalTimeNodeRsp.setStage(i);
			List<EvalStageTimeNodeRsp> stageNodes = new ArrayList<>();
			Date startTime = step.getStartTime();
			Date endTime = step.getEndTime();
			stageNodes.add(new EvalStageTimeNodeRsp("开始", TimeUtil.format1(startTime)));
			for (EvalLoadLogContext log : logList) {
				Date loadTime = log.getTime();
				if (loadTime.after(endTime)) {
					break;
				}else if (!loadTime.before(startTime)) {
					stageNodes.add(new EvalStageTimeNodeRsp(log.getLoadName() + " 执行", TimeUtil.format1(loadTime)));
				}
			}
			evalTimeNodeRsp.setStageNodes(stageNodes);
			timeNodes.add(evalTimeNodeRsp);
		}
		return timeNodes;
	}

	@Override
	public EvalAllTimeLineRsp getTimeLine(String instanceId) {
		EvalTestInstance instance = evalTestInstanceMapper.selectById(instanceId);
		if (instance == null) {
			throw new BusinessException(CrBusinessErrorCode.CANNOT_FIND_TEST_INSTANCE);
		}
		EvalAllTimeLineRsp timeLineRsp = new EvalAllTimeLineRsp();
		timeLineRsp.setStartTime(instance.getStartTime());
		timeLineRsp.setEndTime(instance.getEndTime());
		List<EvalTimeLineRsp> lineRspList = new ArrayList<>();
		List<EvalInstanceStep> stepList = evalInstanceStepMapper.selectByInstanceId(instanceId);
		for (EvalInstanceStep step : stepList) {
			EvalTimeLineRsp startLineRsp = new EvalTimeLineRsp("步骤" + step.getStage() + "开始", step.getStartTime(), 1);
			lineRspList.add(startLineRsp);
			EvalTimeLineRsp endLineRsp = new EvalTimeLineRsp("步骤" + step.getStage() + "结束", step.getEndTime(), 1);
			lineRspList.add(endLineRsp);
		}
		List<EvalLoadLogContext> loadLogList = getLoadLogList(instance.getMpSceneId());
		for (EvalLoadLogContext logContext : loadLogList) {
			EvalTimeLineRsp lineRsp = new EvalTimeLineRsp(logContext.getLoadName(), logContext.getTime(), 2);
			lineRspList.add(lineRsp);
		}
		lineRspList.sort(Comparator.comparing(EvalTimeLineRsp::getTime));
		timeLineRsp.setData(lineRspList);
		return timeLineRsp;
	}

	@Override
	public EvalTaskStatisticsRes getTaskStatistics(String instanceId) {
		EvalTaskStatisticsRes evalTaskStatisticsRes = new EvalTaskStatisticsRes();
		Integer sum = evalInstanceStepMapper.selectAllStepCount(instanceId);
		evalTaskStatisticsRes.setSum(sum);
		EvalTestInstance instance = evalTestInstanceMapper.selectById(instanceId);
		String sceneId = instance.getMpSceneId();
		List<LoadHistory> histories = remoteLoadService.getLoadHistory(sceneId);
		List<String> stepIds = evalInstanceStepMapper.selectIncompleteStepCount(instanceId);
		evalTaskStatisticsRes.setInCompletion(stepIds.size());
		List<String> failStepIds = histories.stream()
											.filter(history -> !"success".equals(history.getResultStatus()))
											.map(LoadHistory::getStageId)
											.distinct().collect(Collectors.toList());
		failStepIds.removeAll(stepIds);
		evalTaskStatisticsRes.setFail(failStepIds.size());
		evalTaskStatisticsRes.setSuccess(sum - stepIds.size() - failStepIds.size());
		return evalTaskStatisticsRes;
	}

	@Override
	public List<EvalCodeStageRsp> getAllStagesBySceneId(String sceneId) {
		return evalInstanceStepMapper.getAllStagesBySceneId(sceneId);
	}

	private List<EvalLoadLogContext> getLoadLogList(String mpSceneId) {
		JSONArray logArray = remoteLoadService.getLoadLog(mpSceneId);
		List<EvalLoadLogContext> logList = new ArrayList<>();
		for (int i = 0; i < logArray.size(); i++) {
			JSONObject jsonObject = logArray.getJSONObject(i);
			EvalLoadLogContext context = new EvalLoadLogContext(jsonObject.getString("tool_name"),
					TimeUtil.toDate1(jsonObject.getString("create_time")));
			logList.add(context);
		}
		logList.sort(Comparator.comparing(EvalLoadLogContext::getTime));
		return logList;
	}

}
