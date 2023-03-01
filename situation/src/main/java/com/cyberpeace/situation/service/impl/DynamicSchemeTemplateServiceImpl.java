package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.DefenseType;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.req.AttackMessageTemplateReq;
import com.cyberpeace.situation.controller.req.UpdateDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.res.OptionTemplateRes;
import com.cyberpeace.situation.dao.entity.AttackMessageTemplate;
import com.cyberpeace.situation.dao.entity.DynamicSchemeTemplate;
import com.cyberpeace.situation.dao.mapper.AttackMessageTemplateMapper;
import com.cyberpeace.situation.dao.mapper.DynamicSchemeTemplateMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.AttackMessageTemplateService;
import com.cyberpeace.situation.service.DynamicSchemeTemplateService;
import com.google.common.collect.Lists;
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
		DynamicSchemeTemplate oldDynamicSchemeTemplate = dynamicSchemeTemplateMapper.selectById(updateDynamicSchemeTemplateReq.getResourceId());
		List<AttackMessageTemplateReq> updateAttackMessageTemplateReqList = new ArrayList<>();
		List<AttackMessageTemplateReq> addAttackMessageTemplateReqList = new ArrayList<>();
		List<DynamicStageBo> dynamicStageBoList = new ArrayList<>();
		for (DynamicTemplateStage dynamicTemplateStage : updateDynamicSchemeTemplateReq.getSchemeData()) {
			for (AttackMessageTemplateReq attackMessageTemplateReq : dynamicTemplateStage.getStageData()) {
				if (StringUtils.isBlank(attackMessageTemplateReq.getResourceId())) {
					addAttackMessageTemplateReqList.add(attackMessageTemplateReq);
				} else {
					updateAttackMessageTemplateReqList.add(attackMessageTemplateReq);
				}
			}
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				attackMessageTemplateService.bashUpdateAttackMessageTemplate(updateAttackMessageTemplateReqList);
				attackMessageTemplateService.bashAddAttackMessageTemplate(addAttackMessageTemplateReqList);
				List<String> oldAttackMessageIds = new ArrayList<>();
				List<String> newAttackMessageIds = new ArrayList<>();

				List<DynamicStageBo> oldDynamicStageBoList = JsonUtil.toJsonObjectArray(oldDynamicSchemeTemplate.getSchemeData()
						, DynamicStageBo.class);
				oldDynamicStageBoList.forEach(dynamicStageBo ->  oldAttackMessageIds.addAll(dynamicStageBo.getAttackMessageIds()));
				DynamicSchemeTemplate dynamicSchemeTemplate = BeanUtils.copy(updateDynamicSchemeTemplateReq, DynamicSchemeTemplate.class);
				for (DynamicTemplateStage dynamicTemplateStage: updateDynamicSchemeTemplateReq.getSchemeData()) {
					DynamicStageBo dynamicStageBo = new DynamicStageBo();
					dynamicStageBo.setName(dynamicTemplateStage.getStageName());
					List<String> attackMessageIds = StreamApiUtils.map(dynamicTemplateStage.getStageData(),AttackMessageTemplateReq::getResourceId);
					newAttackMessageIds.addAll(attackMessageIds);
					dynamicStageBo.setAttackMessageIds(attackMessageIds);
					dynamicStageBoList.add(dynamicStageBo);
				}
				dynamicSchemeTemplate.setSchemeData(JSON.toJSONString(dynamicStageBoList));
				dynamicSchemeTemplateMapper.updateById(dynamicSchemeTemplate);
				oldAttackMessageIds.removeAll(newAttackMessageIds);
				if (oldAttackMessageIds.size() > 0) {
					attackMessageTemplateMapper.deleteBatchIds(oldAttackMessageIds);
				}
			}
		});
	}

	@Override
	public UpdateDynamicSchemeTemplateReq getDynamicSchemeTemplate(String resourceId) {
		UpdateDynamicSchemeTemplateReq updateDynamicSchemeTemplateReq = new UpdateDynamicSchemeTemplateReq();
		DynamicSchemeTemplate dynamicSchemeTemplate = dynamicSchemeTemplateMapper.selectById(resourceId);
		List<DynamicStageBo> dynamicStageBoList = JsonUtil.toJsonObjectArray(dynamicSchemeTemplate.getSchemeData(),
				DynamicStageBo.class);
		updateDynamicSchemeTemplateReq.setSchemeName(dynamicSchemeTemplate.getName());
		updateDynamicSchemeTemplateReq.setResourceId(resourceId);
		List<String> resourceIds = new ArrayList<>();
		dynamicStageBoList.forEach(dynamicStageBo -> resourceIds.addAll(dynamicStageBo.getAttackMessageIds()));
		List<AttackMessageTemplate> attackMessageTemplateList = new ArrayList<>();
		if (resourceIds.size() > 0) {
			attackMessageTemplateList = attackMessageTemplateMapper.selectBatchIds(resourceIds);
		}
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
	public ListRsp<SimpleDynamicSchemeTemplate> getListDynamicSchemeTemplate(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<DynamicSchemeTemplate> dynamicSchemeTemplateList = dynamicSchemeTemplateMapper.selectAll(page, pageReqBase.getPattern());
		return PageUtil.getListResult(dynamicSchemeTemplateList.convert(this::dynamicSchemeTemplateToSimpleDynamicSchemeTemplate));
	}

	@Override
	public OptionTemplateRes getOptions() {
		OptionTemplateRes optionTemplateRes = new OptionTemplateRes();
		//获取动效类型
		optionTemplateRes.setAttackTypeList(StreamApiUtils.map(Lists.newArrayList(AttackType.values()),
				attackType -> new WebAttackType(attackType.getKeyWord(), attackType.getMsg(),
						attackType.getActionType().getType(), attackType.getActionType().isSource(),
						attackType.getActionType().isDurationTime())));
		//获取线型
		optionTemplateRes.setLineTypeList(StreamApiUtils.map(Lists.newArrayList(LineType.values()),
				lineType -> new LocalType(lineType.getCode(), lineType.getMsg())));
		//获取颜色
		optionTemplateRes.setColorList(StreamApiUtils.map(Lists.newArrayList(Color.values()),
				color -> new LocalType(color.getCode(), color.getMsg())));
		//获取防御类型
		optionTemplateRes.setDefenseList(StreamApiUtils.map(Lists.newArrayList(DefenseType.values()),
				defenseType -> new LocalType(defenseType.getMsg(), defenseType.getKeyWord())));
		return optionTemplateRes;
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
			if (StringUtils.isBlank(dynamicTemplateStage.getStageName())) {
				throw new BusinessException(SituationErrorCode.EMPTY_STAGE_NAME);
			}
			for (AttackMessageTemplateReq attackMessageTemplateReq: dynamicTemplateStage.getStageData()) {
				if (StringUtils.isBlank(attackMessageTemplateReq.getDescription())) {
					throw new BusinessException(SituationErrorCode.EMPTY_SCHEME_NAME);
				}
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
