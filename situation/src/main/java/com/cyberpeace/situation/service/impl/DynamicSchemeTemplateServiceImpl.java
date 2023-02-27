package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.req.AttackMessageTemplateReq;
import com.cyberpeace.situation.controller.req.UpdateDynamicSchemeTemplateReq;
import com.cyberpeace.situation.dao.entity.AttackMessageTemplate;
import com.cyberpeace.situation.dao.entity.DynamicSchemeTemplate;
import com.cyberpeace.situation.dao.mapper.AttackMessageTemplateMapper;
import com.cyberpeace.situation.dao.mapper.DynamicSchemeTemplateMapper;
import com.cyberpeace.situation.model.DynamicStageBo;
import com.cyberpeace.situation.model.DynamicTemplateStage;
import com.cyberpeace.situation.model.SimpleDynamicSchemeTemplate;
import com.cyberpeace.situation.service.AttackMessageTemplateService;
import com.cyberpeace.situation.service.DynamicSchemeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DynamicSchemeTemplateServiceImpl extends ServiceImpl<DynamicSchemeTemplateMapper, DynamicSchemeTemplate> implements DynamicSchemeTemplateService {

	@Autowired
	private DynamicSchemeTemplateMapper dynamicSchemeTemplateMapper;

	@Autowired
	private AttackMessageTemplateService attackMessageTemplateService;

	@Autowired
	private AttackMessageTemplateMapper attackMessageTemplateMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public String addDynamicSchemeTemplate(AddDynamicSchemeTemplateReq addDynamicSchemeTemplateReq) {
		String resourceId = IDGeneratorUtil.getID();
		validDynamicSchemeTemplate(addDynamicSchemeTemplateReq);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<AttackMessageTemplateReq> attackMessageTemplateReqList = new ArrayList<>();
				DynamicSchemeTemplate dynamicSchemeTemplate = new DynamicSchemeTemplate();
				dynamicSchemeTemplate.setName(addDynamicSchemeTemplateReq.getSchemeName());
				dynamicSchemeTemplate.setResourceId(resourceId);
				for (DynamicTemplateStage dynamicTemplateStage : addDynamicSchemeTemplateReq.getSchemeData()) {
					attackMessageTemplateReqList.addAll(dynamicTemplateStage.getStageData());
				}
				attackMessageTemplateService.bashAddAttackMessageTemplate(attackMessageTemplateReqList);
				List<DynamicStageBo> dynamicStageBoList = new ArrayList<>();
				for (DynamicTemplateStage dynamicTemplateStage : addDynamicSchemeTemplateReq.getSchemeData()) {
					DynamicStageBo dynamicStageBo = new DynamicStageBo();
					dynamicStageBo.setName(dynamicTemplateStage.getStageName());
					dynamicStageBo.setAttackMessageIds(dynamicTemplateStage.getStageData()
																		   .stream()
																		   .map(AttackMessageTemplateReq::getResourceId)
																		   .collect(Collectors.toList()));
					dynamicStageBoList.add(dynamicStageBo);
				}
				dynamicSchemeTemplate.setSchemeData(JSON.toJSONString(dynamicStageBoList));
				save(dynamicSchemeTemplate);
			}
		});
		return resourceId;
	}

	@Override
	public void bashDeleteDynamicSchemeTemplate(List<String> resourceIds) {

		List<String> attackMessageIds = new ArrayList<>();
		List<DynamicSchemeTemplate> dynamicSchemeTemplateList =
				dynamicSchemeTemplateMapper.selectBatchIds(resourceIds);
		for (DynamicSchemeTemplate dynamicSchemeTemplate : dynamicSchemeTemplateList) {
			List<DynamicStageBo> dynamicStageBoList = JsonUtil.toJsonObjectArray(dynamicSchemeTemplate.getSchemeData()
					, DynamicStageBo.class);
			for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
				attackMessageIds.addAll(dynamicStageBo.getAttackMessageIds());
			}
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				attackMessageTemplateMapper.deleteBatchIds(attackMessageIds);
				dynamicSchemeTemplateMapper.deleteBatchIds(resourceIds);
			}
		});
	}

	@Override
	public void updateDynamicSchemeTemplate(UpdateDynamicSchemeTemplateReq updateDynamicSchemeTemplateReq) {
		validDynamicSchemeTemplate(updateDynamicSchemeTemplateReq);
		List<AttackMessageTemplateReq> updateAttackMessageTemplateReqList = new ArrayList<>();
		List<AttackMessageTemplateReq> addAttackMessageTemplateReqList = new ArrayList<>();
		for (DynamicTemplateStage dynamicTemplateStage : updateDynamicSchemeTemplateReq.getSchemeData()) {
			for (AttackMessageTemplateReq attackMessageTemplateReq : dynamicTemplateStage.getStageData()) {
				if (attackMessageTemplateReq.getResourceId() == null) {
					addAttackMessageTemplateReqList.add(attackMessageTemplateReq);
				} else {
					updateAttackMessageTemplateReqList.add(attackMessageTemplateReq);
				}
			}
		}
		attackMessageTemplateService.bashUpdateAttackMessageTemplate(updateAttackMessageTemplateReqList);
		attackMessageTemplateService.bashAddAttackMessageTemplate(addAttackMessageTemplateReqList);
	}

	@Override
	public UpdateDynamicSchemeTemplateReq getDynamicSchemeTemplate(String resourceId) {
		UpdateDynamicSchemeTemplateReq updateDynamicSchemeTemplateReq = new UpdateDynamicSchemeTemplateReq();
		DynamicSchemeTemplate dynamicSchemeTemplate = dynamicSchemeTemplateMapper.selectById(resourceId);
		List<DynamicStageBo> dynamicStageBoList = JsonUtil.toJsonObjectArray(dynamicSchemeTemplate.getSchemeData(),
				DynamicStageBo.class);
		updateDynamicSchemeTemplateReq.setSchemeName(dynamicSchemeTemplate.getName());
		List<String> resourceIds = new ArrayList<>();
		dynamicStageBoList.forEach(dynamicStageBo -> resourceIds.addAll(dynamicStageBo.getAttackMessageIds()));
		List<AttackMessageTemplate> attackMessageTemplateList =
				attackMessageTemplateMapper.selectBatchIds(resourceIds);
		if (attackMessageTemplateList.size() < resourceIds.size()) {
			throw new BusinessException(SituationErrorCode.CANNOT_FIND_DYNAMIC);
		}
		Map<String, AttackMessageTemplate> attackMessageTemplateMap = StreamApiUtils.toMap(attackMessageTemplateList,
				AttackMessageTemplate::getResourceId, attackMessageTemplate -> attackMessageTemplate);
		List<DynamicTemplateStage> dynamicTemplateStageList = new ArrayList<>();
		for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
			DynamicTemplateStage dynamicTemplateStage = new DynamicTemplateStage();
			dynamicTemplateStage.setStageName(dynamicStageBo.getName());
			List<AttackMessageTemplateReq> attackMessageTemplateReqList = new ArrayList<>();
			dynamicStageBo.getAttackMessageIds()
						  .forEach(attackMessageId -> attackMessageTemplateReqList.add(BeanUtils.copy(attackMessageTemplateMap.get(attackMessageId), AttackMessageTemplateReq.class)));
			dynamicTemplateStage.setStageData(attackMessageTemplateReqList);
			dynamicTemplateStageList.add(dynamicTemplateStage);
		}
		updateDynamicSchemeTemplateReq.setSchemeData(dynamicTemplateStageList);
		return updateDynamicSchemeTemplateReq;
	}

	@Override
	public List<SimpleDynamicSchemeTemplate> getListDynamicSchemeTemplate() {
		List<DynamicSchemeTemplate> dynamicSchemeTemplateList = dynamicSchemeTemplateMapper.selectAll();
		return dynamicSchemeTemplateList.stream().map(this::dynamicSchemeTemplateToSimpleDynamicSchemeTemplate).collect(Collectors.toList());
	}

	public void validDynamicSchemeTemplate(AddDynamicSchemeTemplateReq addDynamicSchemeTemplateReq) {
		for (DynamicTemplateStage dynamicTemplateStage : addDynamicSchemeTemplateReq.getSchemeData()) {
			List<String> names = dynamicTemplateStage.getStageData()
													 .stream()
													 .map(AttackMessageTemplateReq::getDescription)
													 .collect(Collectors.toList());
			if (new HashSet<>(names).size() < names.size()) {
				throw new BusinessException(SituationErrorCode.DUPLICATED_DYNAMIC_DESCRIPTION);
			}
		}
	}

	public SimpleDynamicSchemeTemplate dynamicSchemeTemplateToSimpleDynamicSchemeTemplate(DynamicSchemeTemplate dynamicSchemeTemplate) {
		SimpleDynamicSchemeTemplate simpleDynamicSchemeTemplate = new SimpleDynamicSchemeTemplate();
		simpleDynamicSchemeTemplate.setResourceId(dynamicSchemeTemplate.getResourceId());
		simpleDynamicSchemeTemplate.setSchemeName(dynamicSchemeTemplate.getName());
		List<DynamicStageBo> dynamicStageBoList = JsonUtil.toJsonObjectArray(dynamicSchemeTemplate.getSchemeData(), DynamicStageBo.class);
		simpleDynamicSchemeTemplate.setStageCount(dynamicStageBoList.size());
		int messageCount = 0;
		for (DynamicStageBo dynamicStageBo: dynamicStageBoList) {
			messageCount += dynamicStageBo.getAttackMessageIds().size();
		}
		simpleDynamicSchemeTemplate.setMessageCount(messageCount);
		return simpleDynamicSchemeTemplate;
	}

}
