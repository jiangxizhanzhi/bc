package com.cyberpeace.rtks.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.rtks.dao.mapper.AttackIcsMapper;
import com.cyberpeace.rtks.dao.mapper.AttackMapper;
import com.cyberpeace.rtks.dao.mapper.EngageMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMissionRelationMapper;
import com.cyberpeace.rtks.service.AttackIcsService;
import com.cyberpeace.rtks.service.AttackService;
import com.cyberpeace.rtks.service.EngageService;
import com.cyberpeace.rtks.service.bo.career.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * RTKS相关Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class RtksRelatedServiceImpl implements RtksRelatedService {

	/**
	 * 技能任务关系Mapper
	 */
	@Autowired
	private SkillMissionRelationMapper skillMissionRelationMapper;

	/**
	 * 攻击Mapper
	 */
	@Autowired
	private AttackMapper attackMapper;

	/**
	 * 攻击集成电路Mapper
	 */
	@Autowired
	private AttackIcsMapper attackIcsMapper;

	/**
	 * 聘Mapper
	 */
	@Autowired
	private EngageMapper engageMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 攻击Service
	 */
	@Autowired
	private AttackService attackService;

	/**
	 * 攻击集成电路Service
	 */
	@Autowired
	private AttackIcsService attackIcsService;

	/**
	 * 聘Service
	 */
	@Autowired
	private EngageService engageService;

	/**
	 * 删除技能任务关系
	 *
	 * @param missionTemplateIds 任务模板id列表
	 */
	@Override
	public void deleteSkillMissionRelation(List<String> missionTemplateIds) {
		if (!missionTemplateIds.isEmpty()) {
			skillMissionRelationMapper.batchDeleteByMissionTemplateIds(missionTemplateIds);
		}
	}

	/**
	 * 清除攻击关系
	 *
	 * @param businessTemplateIds 业务模板id列表
	 */
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

	/**
	 * 清除 Engage rel
	 *
	 * @param businessTemplateIds 业务模板id列表
	 */
	@Override
	public void clearEngageRel(List<String> businessTemplateIds) {
		if (businessTemplateIds == null || businessTemplateIds.isEmpty()) {
			return;
		}
		engageMapper.clearRel(businessTemplateIds);
	}

	/**
	 * 清除攻击参与 rel
	 *
	 * @param btId BT系列id
	 */
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

	/**
	 * 创建 Attack Engage rel
	 *
	 * @param btId         BT系列id
	 * @param relatedTechs 相关技术
	 */
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
		// 过滤可能存在的非法数据
		List<String> existingAttackIds = StreamApiUtils.distinctFlatMap(attackService.getCache(),
				category -> StreamApiUtils.map(category.getAttacks(),
						FrameItemBase::getResourceId));
		attackIds.removeIf(id -> !existingAttackIds.contains(id));
		List<String> existingEngageIds = StreamApiUtils.distinctFlatMap(engageService.getCache(),
				category -> StreamApiUtils.distinctFlatMap(category.getSubCategories(),
						subCategory -> StreamApiUtils.map(subCategory.getEngages(),
								FrameItemBase::getResourceId)));
		engageIds.removeIf(id -> !existingEngageIds.contains(id));
		List<String> existingIcsIds = StreamApiUtils.distinctFlatMap(attackIcsService.getCache(),
				category -> StreamApiUtils.map(category.getAttacks(),
						FrameItemBase::getResourceId));
		attackIcsIds.removeIf(id -> !existingIcsIds.contains(id));

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

	/**
	 * 计数攻击相对
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code Map<String, List<NameCount>> }
	 */
	@Override
	public Map<String, List<NameCount>> countAttackRel(List<String> businessTemplateIds) {
		Map<String, List<NameCount>> res = Maps.newHashMap();
		if (CollectionUtils.isEmpty(businessTemplateIds)) {
			return res;
		}
		// query relations
		List<TechRelation> relations = attackMapper.selectRelsByBtIds(businessTemplateIds);
		if (relations.isEmpty()) {
			return res;
		}
		List<AttackCategoryBo<FrameItemBase>> cache = attackService.getCache();
		countAttackRel(res, relations, cache);
		return res;
	}

	/**
	 * 计数聘相对
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code Map<String, List<NameCount>> }
	 */
	@Override
	public Map<String, List<NameCount>> countEngageRel(List<String> businessTemplateIds) {
		Map<String, List<NameCount>> res = Maps.newHashMap();
		if (CollectionUtils.isEmpty(businessTemplateIds)) {
			return res;
		}
		// query relations
		List<TechRelation> relations = engageMapper.selectRelsByBtIds(businessTemplateIds);
		if (relations.isEmpty()) {
			return res;
		}
		List<EngageCategoryBo<FrameItemBase>> cache = engageService.getCache();
		for (TechRelation relation : relations) {
			String btId = relation.getBusinessTemplateId();
			if (!res.containsKey(btId)) {
				// init attack category list
				List<NameCount> initCountList = Lists.newArrayList();
				for (EngageCategoryBo<FrameItemBase> categoryBo : cache) {
					initCountList.add(new NameCount(categoryBo.getCategoryName(), 0));
				}
				res.put(btId, initCountList);
			}
			List<NameCount> countList = res.get(btId);
			String relatedEngageId = relation.getTechId();
			// 循环每个category，不同category下可能存在同一个engage
			for (int i = 0; i < cache.size(); i++) {
				// 循环engage sub category，不同sub category下可能出现同样的engage
				for (EngageSubCategoryBo<FrameItemBase> engageSubCategoryBo : cache.get(i)
						.getSubCategories()) {
					// 循环engage，有匹配的+1
					for (FrameItemBase engage : engageSubCategoryBo.getEngages()) {
						if (engage.getResourceId().equals(relatedEngageId)) {
							countList.get(i).add();
							break;
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * 计数集成电路相对
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code Map<String, List<NameCount>> }
	 */
	@Override
	public Map<String, List<NameCount>> countIcsRel(List<String> businessTemplateIds) {
		Map<String, List<NameCount>> res = Maps.newHashMap();
		if (CollectionUtils.isEmpty(businessTemplateIds)) {
			return res;
		}
		// query relations
		List<TechRelation> relations = attackIcsMapper.selectRelsByBtIds(businessTemplateIds);
		if (relations.isEmpty()) {
			return res;
		}
		List<AttackCategoryBo<FrameItemBase>> cache = attackIcsService.getCache();
		countAttackRel(res, relations, cache);
		return res;
	}

	/**
	 * 计数攻击相对
	 *
	 * @param res       结果
	 * @param relations 关系
	 * @param cache     缓存
	 */
	private void countAttackRel(Map<String, List<NameCount>> res, List<TechRelation> relations,
			List<AttackCategoryBo<FrameItemBase>> cache) {
		for (TechRelation relation : relations) {
			String btId = relation.getBusinessTemplateId();
			if (!res.containsKey(btId)) {
				// init attack category list
				List<NameCount> initCountList = Lists.newArrayList();
				for (AttackCategoryBo<FrameItemBase> categoryBo : cache) {
					initCountList.add(new NameCount(categoryBo.getCategoryName(), 0));
				}
				res.put(btId, initCountList);
			}
			List<NameCount> countList = res.get(btId);
			String relatedAttackId = relation.getTechId();
			// 循环每个category，不同category下可能存在同一个attack
			for (int i = 0; i < cache.size(); i++) {
				// 循环attack技术，有匹配的+1计数
				for (FrameItemBase attack : cache.get(i).getAttacks()) {
					if (attack.getResourceId().equals(relatedAttackId)) {
						countList.get(i).add();
						break;
					}
				}
			}
		}
	}
}
