package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemTemplatePageReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalTemTemplateService;
import com.cyberpeace.cr.evaluation.service.impl.bo.*;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvalTemTemplateServiceImpl extends ServiceImpl<EvalTemTemplateMapper, EvalTemTemplate> implements EvalTemTemplateService {

	@Autowired
	private EvalTemTemplateMapper evalTemTemplateMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private EvalMissionTemplateService evalMissionTemplateService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public String createTemplate(EvalTemTemplateReq evalTemTemplateReq) {
		EvalTemTemplate evalTemTemplate = BeanUtils.copy(evalTemTemplateReq.getBaseData(), EvalTemTemplate.class);
		evalTemTemplate.setTechniqueWarfare(JsonUtil.toJsonString(evalTemTemplateReq.getMissionsScheme()));
		evalTemTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalTemTemplate.setTopoId(saveTopo(evalTemTemplateReq.getSceneConfigData()));
		evalTemTemplateMapper.insert(evalTemTemplate);
		return evalTemTemplate.getResourceId();
	}

	@Override
	public void updateTemplate(UpdateEvalTemTemplateReq updateEvalTemTemplateReq) {
		EvalTemTemplate oldEvalTemTemplate = evalTemTemplateMapper.selectById(updateEvalTemTemplateReq.getResourceId());
		String topoId = oldEvalTemTemplate.getTopoId();
		MissionScheme oldScheme = JsonUtil.toJsonObject(oldEvalTemTemplate.getTechniqueWarfare(), MissionScheme.class);
		List<String> oldResourceIds = evalMissionTemplateService.getTaskIds(oldScheme);
		List<String> newResourceIds = evalMissionTemplateService.getTaskIds(updateEvalTemTemplateReq.getMissionsScheme());
		oldResourceIds.removeAll(newResourceIds);
		EvalTemTemplate evalTemTemplate = BeanUtils.copy(updateEvalTemTemplateReq, EvalTemTemplate.class);
		evalTemTemplate.setTechniqueWarfare(JsonUtil.toJsonString(updateEvalTemTemplateReq.getMissionsScheme()));
		JSONObject topoJson = new JSONObject();
		SceneConfigData sceneConfigData = updateEvalTemTemplateReq.getSceneConfigData();
		topoJson.put("scene_config", sceneConfigData.getSceneConfig());
		topoJson.put("logo", sceneConfigData.getLogo());
		topoJson.put("support_partial_start", sceneConfigData.getSupportPartialStart());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (!oldResourceIds.isEmpty()) {
					evalMissionTemplateService.deleteMissionTemplate(oldResourceIds);
				}
				localSceneService.saveTopo(topoJson, topoId);
				evalTemTemplateMapper.updateById(evalTemTemplate);

			}
		});
	}

	@Override
	public void deleteTemplates(List<String> resourceIds) {
		List<EvalTemTemplate> evalTemTemplateList = evalTemTemplateMapper.selectBatchIds(resourceIds);
		List<String> topoIds = evalTemTemplateList.stream().map(EvalTemTemplate::getTopoId).collect(Collectors.toList());


		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				remoteTopoService.batchDeleteTopo(topoIds);
				evalTemTemplateMapper.deleteBatchIds(resourceIds);
			}
		});
	}

	@Override
	public EvalTemTemplateRsp getTemplate(String resourceId) {
		EvalTemTemplate evalTemTemplate = evalTemTemplateMapper.selectById(resourceId);
		BaseTemplateData baseTemplateData = BeanUtils.copy(evalTemTemplate, BaseTemplateData.class);
		EvalTemTemplateRsp evalTemTemplateRsp = new EvalTemTemplateRsp();
		evalTemTemplateRsp.setBaseData(baseTemplateData);
		evalTemTemplateRsp.setResourceId(resourceId);
		evalTemTemplateRsp.setMissionScheme(JsonUtil.toJsonObject(evalTemTemplate.getTechniqueWarfare(), MissionScheme.class));
		evalTemTemplateRsp.setSceneConfigData(remoteTopoService.getTopoById(evalTemTemplate.getTopoId()));
		return evalTemTemplateRsp;
	}

	@Override
	public ListRsp<EvalTemTemplateListRsp> getTemplateList(EvalTemTemplatePageReq pageReqBase) {
		Page page = pageReqBase.toPageParam();
		List<EvalTemTemplateListRsp> rspList = new ArrayList<>();
		IPage<EvalTemTemplate> iPage = evalTemTemplateMapper.selectPage(page, pageReqBase.getType(), pageReqBase.getPattern());
		List<EvalTemTemplate> evalTemTemplateList = iPage.getRecords();
		if(evalTemTemplateList.isEmpty()){
			return PageUtil.getListResult(rspList, pageReqBase.toPageParam(), iPage.getTotal());
		}
		List<String> topoIds = evalTemTemplateList.stream().map(EvalTemTemplate::getTopoId).collect(Collectors.toList());
		Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
		List<String> createUserIds = evalTemTemplateList.stream().map(EvalTemTemplate::getCreateUserId).distinct().collect(Collectors.toList());
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(createUserIds);
		rspList = evalTemTemplateList.stream()
				.map(evalTemTemplate -> {
					EvalTemTemplateListRsp evalTemTemplateListRsp = BeanUtils.copy(evalTemTemplate, EvalTemTemplateListRsp.class);
					MissionScheme missionScheme = JsonUtil
							.toJsonObject(evalTemTemplate.getTechniqueWarfare(), MissionScheme.class);
					evalTemTemplateListRsp.setMissionCount(0);
					evalTemTemplateListRsp.setStageCount(0);
					if(null == missionScheme && !missionScheme.getStageList().isEmpty()){
						List<StageData> stageList = missionScheme.getStageList();
						evalTemTemplateListRsp.setStageCount(stageList.size());
						List<List<TaskBo>> collect = stageList.stream().map(StageData::getTaskList).collect(Collectors.toList());
						int taskCount = 0;
						for (List<TaskBo> item : collect) {
							taskCount = taskCount + item.size();
						}
						evalTemTemplateListRsp.setMissionCount(taskCount);
					}
					evalTemTemplateListRsp.setUpdateTime(TimeUtil.format1(evalTemTemplate.getUpdateTime()));
					evalTemTemplateListRsp.setNodeCount(nodeCountMap.containsKey(evalTemTemplate.getTopoId()) ? nodeCountMap.get(evalTemTemplate.getTopoId()).getNodeCounts() : 0);
					evalTemTemplateListRsp.setCreateUser(idNameMap.get(evalTemTemplate.getCreateUserId()));
					return evalTemTemplateListRsp;
				}).collect(Collectors.toList());
		return PageUtil.getListResult(rspList, pageReqBase.toPageParam(), iPage.getTotal());
	}

	private String saveTopo(SceneConfigData sceneConfigData) {
		JSONObject topoJson = new JSONObject();
		topoJson.put("scene_config", sceneConfigData.getSceneConfig());
		topoJson.put("logo", sceneConfigData.getLogo());
		topoJson.put("support_partial_start", sceneConfigData.getSupportPartialStart());
		return localSceneService.saveTopo(topoJson, BusinessCode.RANGE_EVALUATION.getCode());
	}


}
