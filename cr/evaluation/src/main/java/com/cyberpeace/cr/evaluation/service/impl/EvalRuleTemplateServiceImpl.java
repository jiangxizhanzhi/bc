package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateDetailRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalDimensionTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalHostTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRuleTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalRuleTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalDimensionContext;
import com.cyberpeace.cr.evaluation.service.context.EvalHostContext;
import com.cyberpeace.cr.evaluation.service.context.EvalRuleTemplateCount;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskTemplateContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalRuleTemplateServiceImpl extends ServiceImpl<EvalRuleTemplateMapper, EvalRuleTemplate> implements EvalRuleTemplateService {

	@Autowired
	private EvalRuleTemplateMapper evalRuleTemplateMapper;

	@Autowired
	private EvalDimensionTemplateMapper evalDimensionTemplateMapper;

	@Autowired
	private EvalActionTemplateMapper evalActionTemplateMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private EvalHostTemplateMapper evalHostTemplateMapper;

	@Autowired
	private EvalCloudTemplateMapper evalCloudTemplateMapper;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private EvalStepTemplateMapper evalStepTemplateMapper;

	@Override
	public String addRuleTemplate(EvalRuleTemplateAddReq evalRuleTemplateAddReq) {
		EvalRuleTemplate evalRuleTemplate = new EvalRuleTemplate();
		evalRuleTemplate.setName(evalRuleTemplateAddReq.getRuleName());
		evalRuleTemplate.setDescription(evalRuleTemplateAddReq.getDescription());
		evalRuleTemplate.setCloudTemplateId(evalRuleTemplateAddReq.getCloudTemplateId());
		evalRuleTemplate.setStatus(EvalRuleTemplate.Status.CLOSE.getCode());
		evalRuleTemplate.setGrade(EvalRuleTemplate.Grade.LOW.getCode());
		evalRuleTemplate.setBooleanRelation(EvalRuleTemplate.BooleanRelation.OR.getCode());
		evalRuleTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalRuleTemplate.setLabelList(new ArrayList<>());
		evalRuleTemplate.setTaskList(new ArrayList<>());
		evalRuleTemplateMapper.insert(evalRuleTemplate);
		return evalRuleTemplate.getResourceId();
	}

	@Override
	public List<EvalRuleTemplateListRsp> getRuleTemplateList(EvalRuleTemplateListReq evalRuleTemplateListReq) {
		List<EvalRuleTemplate> evalRuleTemplateList = evalRuleTemplateMapper.listEvalRuleTemplate(evalRuleTemplateListReq);
		if (CollectionUtils.isEmpty(evalRuleTemplateList)) {
			return new ArrayList<>();
		}
		List<String> ruleIds = evalRuleTemplateList.stream().map(EvalRuleTemplate::getResourceId).collect(Collectors.toList());
		List<EvalRuleTemplateCount> dimensionCounts = evalDimensionTemplateMapper.selectCountByRuleIds(ruleIds);
		Map<String, Integer> dimensionCountMap = StreamApiUtils.toMap(dimensionCounts,
				EvalRuleTemplateCount::getRuleId, EvalRuleTemplateCount::getCount);
		List<EvalRuleTemplateCount> actionCounts = evalActionTemplateMapper.selectCountByRuleIds(ruleIds);
		Map<String, Integer> actionCountMap = StreamApiUtils.toMap(actionCounts,
				EvalRuleTemplateCount::getRuleId, EvalRuleTemplateCount::getCount);

		List<EvalRuleTemplateListRsp> evalRuleTemplateListRspList = new ArrayList<>();
		for (EvalRuleTemplate evalRuleTemplate : evalRuleTemplateList) {
			EvalRuleTemplateListRsp evalRuleTemplateListRsp = BeanUtils.copy(evalRuleTemplate, EvalRuleTemplateListRsp.class);
			evalRuleTemplateListRsp.setRuleId(evalRuleTemplate.getResourceId());
			evalRuleTemplateListRsp.setRuleName(evalRuleTemplate.getName());
			evalRuleTemplateListRsp.setDimensionCount(dimensionCountMap.getOrDefault(evalRuleTemplate.getResourceId(), 0));
			evalRuleTemplateListRsp.setActionCount(actionCountMap.getOrDefault(evalRuleTemplate.getResourceId(), 0));
			evalRuleTemplateListRsp.setStatus(EnumUtils.getEnumDescByCode(evalRuleTemplate.getStatus(), EvalRuleTemplate.Status.class));
			evalRuleTemplateListRsp.setGrade(EnumUtils.getEnumDescByCode(evalRuleTemplate.getGrade(), EvalRuleTemplate.Grade.class));
			evalRuleTemplateListRsp.setBooleanRelation(
					EnumUtils.getEnumDescByCode(evalRuleTemplate.getBooleanRelation(), EvalRuleTemplate.BooleanRelation.class));
			evalRuleTemplateListRsp.setCreateTime(TimeUtil.format1(evalRuleTemplate.getCreateTime()));
			evalRuleTemplateListRsp.setLabelList(evalRuleTemplate.getLabelList());
			evalRuleTemplateListRspList.add(evalRuleTemplateListRsp);
		}
		return evalRuleTemplateListRspList;
	}

	@Override
	public void deleteRuleTemplate(DeleteReq deleteReq) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				evalRuleTemplateMapper.deleteBatchIds(deleteReq.getIds());
				evalActionTemplateMapper.deleteByRuleIds(deleteReq.getIds());
				evalDimensionTemplateMapper.deleteByRuleIds(deleteReq.getIds());
			}
		});
	}

	@Override
	public void addTaskTemplate(EvalTaskTemplateAddReq evalTaskTemplateAddReq) {
		EvalTaskTemplateContext evalTaskTemplateContext = BeanUtils.copy(evalTaskTemplateAddReq, EvalTaskTemplateContext.class);
		EvalRuleTemplate evalRuleTemplate = evalRuleTemplateMapper.selectById(evalTaskTemplateAddReq.getRuleId());
		evalRuleTemplate.addTask(evalTaskTemplateContext);
		evalRuleTemplateMapper.updateById(evalRuleTemplate);
	}

	@Override
	public void deleteTaskTemplate(EvalTaskTemplateDelReq evalTaskTemplateDelReq) {
		EvalRuleTemplate evalRuleTemplate = evalRuleTemplateMapper.selectById(evalTaskTemplateDelReq.getRuleId());
		List<EvalTaskTemplateContext> taskList = evalRuleTemplate.getTaskList();
		taskList = taskList.stream()
						   .filter(evalTaskTemplateContext -> !evalTaskTemplateDelReq.getTaskIds().contains(evalTaskTemplateContext.getTaskId()))
						   .collect(Collectors.toList());
		evalRuleTemplate.setTaskList(taskList);
		evalRuleTemplateMapper.updateById(evalRuleTemplate);
	}

	@Override
	public EvalRuleTemplateDetailRsp getRuleTemplate(String id) {
		EvalRuleTemplate evalRuleTemplate = evalRuleTemplateMapper.selectById(id);
		if (evalRuleTemplate == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		EvalRuleTemplateDetailRsp evalRuleTemplateDetail = BeanUtils.copy(evalRuleTemplate, EvalRuleTemplateDetailRsp.class);
		evalRuleTemplateDetail.setRuleId(evalRuleTemplate.getResourceId());
		evalRuleTemplateDetail.setRuleName(evalRuleTemplate.getName());
		evalRuleTemplateDetail.setCreateUser(remoteUserService.getById(evalRuleTemplate.getCreateUserId()).getUserName());
		evalRuleTemplateDetail.setCreateTime(TimeUtil.format1(evalRuleTemplate.getCreateTime()));
		evalRuleTemplateDetail.setLabels(evalRuleTemplate.getLabelList());
		List<EvalDimensionTemplate> evalDimensionTemplateList = evalDimensionTemplateMapper.selectByRuleId(evalRuleTemplate.getResourceId());
		evalRuleTemplateDetail.setDimensions(
				evalDimensionTemplateList.stream()
										 .map(evalDimensionTemplate -> {
										 	EvalDimensionContext evalDimensionContext = BeanUtils.copy(evalDimensionTemplate, EvalDimensionContext.class);
											evalDimensionContext.setDimensionId(evalDimensionTemplate.getResourceId());
											return evalDimensionContext;
										 })
										 .collect(Collectors.toList())
		);
		List<EvalHostTemplate> evalHostTemplateList = evalHostTemplateMapper.selectByRuleId(evalRuleTemplate.getResourceId());
		if (evalCloudTemplateMapper.selectById(evalRuleTemplate.getCloudTemplateId()) == null) {
			evalRuleTemplateDetail.setHosts(
					evalHostTemplateList.stream()
										.map(evalHostTemplate -> BeanUtils.copy(evalHostTemplate, EvalHostContext.class))
										.collect(Collectors.toList())
			);
		} else {
			SceneData sceneData = topoHandlerService.getSceneData(null, evalCloudTemplateMapper.getTopoIdById(evalRuleTemplate.getCloudTemplateId()));
			SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
			List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements().values());
			Map<String, String> idNameMap = elementInfoList.stream().collect(Collectors.toMap(ElementInfo::getElementId, ElementInfo::getName));
			evalRuleTemplateDetail.setHosts(
					evalHostTemplateList.stream()
										.map(evalHostTemplate -> {
											EvalHostContext evalHostContext = BeanUtils.copy(evalHostTemplate, EvalHostContext.class);
											evalHostContext.setHostName(idNameMap.get(evalHostContext.getHostId()));
											return evalHostContext;
										})
										.collect(Collectors.toList()));

		}

		evalRuleTemplateDetail.setTasks(evalRuleTemplate.getTaskList());

		List<EvalTaskTemplateContext> evalTaskTemplateContexts = evalStepTemplateMapper.selectIdNameByCloudTemplateId(evalRuleTemplate.getCloudTemplateId());
		evalRuleTemplateDetail.setActions(evalActionTemplateMapper.selectByRuleId(evalRuleTemplate.getResourceId()));
		return evalRuleTemplateDetail;
	}

	@Override
	public void modifyRuleTemplate(EvalRuleTemplateModifyReq evalRuleTemplateModifyReq) {
		EvalRuleTemplate evalRuleTemplate = BeanUtils.copy(evalRuleTemplateModifyReq, EvalRuleTemplate.class);
		evalRuleTemplate.setResourceId(evalRuleTemplateModifyReq.getRuleId());
		evalRuleTemplate.setName(evalRuleTemplateModifyReq.getRuleName());
		evalRuleTemplate.setLabelList(evalRuleTemplateModifyReq.getLabels());
		evalRuleTemplateMapper.updateById(evalRuleTemplate);
	}

	@Override
	public EvalRuleTemplateEnumRsp getRuleEnum() {
		EvalRuleTemplateEnumRsp evalRuleTemplateEnumRsp = new EvalRuleTemplateEnumRsp();
		evalRuleTemplateEnumRsp.setBooleanRelation(Arrays.stream(EvalRuleTemplate.BooleanRelation.values())
														 .map(booleanRelation -> new CodeNameRsp(booleanRelation.getCode(), booleanRelation.getDesc())).collect(Collectors.toList()));
		evalRuleTemplateEnumRsp.setStatus(Arrays.stream(EvalRuleTemplate.Status.values())
												.map(status -> new CodeNameRsp(status.getCode(), status.getDesc())).collect(Collectors.toList()));
		evalRuleTemplateEnumRsp.setGrade(Arrays.stream(EvalRuleTemplate.Grade.values())
											   .map(grade -> new CodeNameRsp(grade.getCode(), grade.getDesc())).collect(Collectors.toList()));
		return evalRuleTemplateEnumRsp;
	}


}
