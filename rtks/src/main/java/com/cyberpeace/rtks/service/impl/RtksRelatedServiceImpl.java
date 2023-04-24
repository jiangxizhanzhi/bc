package com.cyberpeace.rtks.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.rtks.dao.mapper.AttackIcsMapper;
import com.cyberpeace.rtks.dao.mapper.AttackMapper;
import com.cyberpeace.rtks.dao.mapper.EngageMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMissionRelationMapper;
import com.google.common.collect.Lists;

@Service
public class RtksRelatedServiceImpl implements RtksRelatedService {

	@Autowired
	private SkillMissionRelationMapper skillMissionRelationMapper;

	@Autowired
	private AttackMapper attackMapper;

	@Autowired
	private AttackIcsMapper attackIcsMapper;

	@Autowired
	private EngageMapper engageMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public void deleteSkillMissionRelation(List<String> missionTemplateIds) {
		if (!missionTemplateIds.isEmpty()) {
			skillMissionRelationMapper.batchDeleteByMissionTemplateIds(missionTemplateIds);
		}
	}

	@Override
	public void clearAttackRel(List<String> businessTemplateIds) {
		if (businessTemplateIds == null || businessTemplateIds.isEmpty()) {
			return;
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				attackMapper.clearRel(businessTemplateIds);
				attackIcsMapper.clearRel(businessTemplateIds);
			}
		});
	}

	@Override
	public void clearEngageRel(List<String> businessTemplateIds) {
		if (businessTemplateIds == null || businessTemplateIds.isEmpty()) {
			return;
		}
		engageMapper.clearRel(businessTemplateIds);
	}

	@Override
	public void clearAttackEngageRel(String btId) {
		List<String> btIds = Lists.newArrayList(btId);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				clearAttackRel(btIds);
				clearEngageRel(btIds);
			}
		});
	}

	@Override
	public void createAttackEngageRel(String btId, Collection<String> relatedTechs) {
		List<String> attackIds = Lists.newArrayList();
		List<String> attackIcsIds = Lists.newArrayList();
		List<String> engageIds = Lists.newArrayList();
		for (String relatedTech : relatedTechs) {
			String code = relatedTech.split("-")[0].trim();
			if (code.startsWith("T0")) {
				attackIcsIds.add(code);
			} else if (code.startsWith("T1")) {
				attackIds.add(code);
			} else if (code.startsWith("EAC") || code.startsWith("SAC")) {
				engageIds.add(code);
			}
		}
		// 忽略已存在关联的可能
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (!attackIds.isEmpty()) {
					attackMapper.loadRel(btId, attackIds);
				}
				if (!attackIcsIds.isEmpty()) {
					attackIcsMapper.loadRel(btId, attackIcsIds);
				}
				if (!engageIds.isEmpty()) {
					engageMapper.loadRel(btId, engageIds);
				}
			}
		});
	}
}
