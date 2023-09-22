package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.rtks.dao.entity.AttackTrainRelation;
import com.cyberpeace.rtks.dao.mapper.AttackMapper;
import com.cyberpeace.rtks.service.AttackService;
import com.cyberpeace.rtks.service.bo.career.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttackServiceImpl implements AttackService {

	private final Object lock = new Object();

	@Autowired
	private AttackMapper attackMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	private volatile AttackCache cachedAttacks;

	@Override
	public List<AttackCategoryBo<AttackBackBo>> getAttacks() {
		ensureAttackCache();
		List<AttackCategoryBo<AttackBackBo>> res;
		if (CommonContext.isChinese()) {
			res = BeanUtils.deepCopy(cachedAttacks, AttackBackZh.class).getZh();
		} else {
			res = BeanUtils.deepCopy(cachedAttacks, AttackBackEn.class).getEn();
		}
		// count relation
		List<RelCount> countList = attackMapper.countRelsByAttackId();
		Map<String, Integer> countMap = Maps.newHashMap();
		countList.forEach((count) -> {
			countMap.put(count.getResourceId(), count.getCount());
		});
		// set count
		res.forEach((category -> {
			category.getAttacks().forEach((attackBackBo -> {
				if (countMap.containsKey(attackBackBo.getResourceId())) {
					attackBackBo.setCount(countMap.get(attackBackBo.getResourceId()));
				}
			}));
		}));

		return res;
	}

	@Override
	public List<AttackCategoryBo<AttackFrontBo>> getAttacksByTraining(String businessTemplateId) {
		ensureAttackCache();
		List<AttackCategoryBo<AttackFrontBo>> res;
		if (CommonContext.isChinese()) {
			res = BeanUtils.deepCopy(cachedAttacks, AttackFrontZh.class).getZh();
		} else {
			res = BeanUtils.deepCopy(cachedAttacks, AttackFrontEn.class).getEn();
		}
		List<String> relatedAttackIds = attackMapper.getRelByBusinessTemplateId(businessTemplateId);
		// set whether related
		res.forEach((category -> {
			category.getAttacks().forEach((attackFrontBo -> {
				if (relatedAttackIds.contains(attackFrontBo.getResourceId())) {
					attackFrontBo.setRelated(true);
				}
			}));
		}));
		return res;
	}

	@Override
	public AttackDetail getDetailById(String resourceId) {
		ensureAttackCache();
		AttackDetail detail = new AttackDetail();
		List<AttackCategoryBo<FrameItemBase>> cache;
		if (CommonContext.isChinese()) {
			cache = cachedAttacks.getZh();
		} else {
			cache = cachedAttacks.getEn();
		}
		// search
		cache.forEach((category) -> {
			category.getAttacks().forEach((attack) -> {
				if (attack.getResourceId().equals(resourceId)) {
					detail.setCategoryName(category.getCategoryName());
					detail.setResourceId(attack.getResourceId());
					detail.setName(attack.getName());
				}
			});
		});
		// get related business templates
		List<BusinessTemplateSimple> templates = attackMapper.getRelatedTemplates(resourceId);
		detail.setTemplates(templates);
		return detail;
	}

	@Override
	public void updateRelations(String resourceId, List<String> businessTemplateIds) {
		List<String> oldRel = attackMapper.getRelatedTemplateIds(resourceId);
		CrCommonUtil.differ2List(oldRel, businessTemplateIds);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (!oldRel.isEmpty()) {
					attackMapper.deleteRel(resourceId, oldRel);
				}
				if (!businessTemplateIds.isEmpty()) {
					attackMapper.addRel(resourceId, businessTemplateIds);
				}
			}
		});
	}

	@Override
	public List<AttackCategoryBo<FrameItemBase>> getCache() {
		ensureAttackCache();
		if (CommonContext.isChinese()) {
			return cachedAttacks.getZh();
		} else {
			return cachedAttacks.getEn();
		}
	}

	private void ensureAttackCache() {
		if (null == cachedAttacks) {
			synchronized (lock) {
				if (null == cachedAttacks) {
					initializeAttacks();
				}
			}
		}
	}

	/**
	 * 读取 ATT&CK 缓存
	 */
	private void initializeAttacks() {
		List<AttackContext> attackContextList = attackMapper.getAll();
		cachedAttacks = new AttackCache();
		String categoryId = "";
		AttackCategoryBo<FrameItemBase> categoryBoZh = new AttackCategoryBo<>();
		AttackCategoryBo<FrameItemBase> categoryBoEn = new AttackCategoryBo<>();
		for (AttackContext attackContext : attackContextList) {
			if (!categoryId.equals(attackContext.getCategoryId())) {
				categoryId = attackContext.getCategoryId();
				categoryBoZh = new AttackCategoryBo<>();
				categoryBoEn = new AttackCategoryBo<>();
				categoryBoZh.setCategoryName(attackContext.getCategoryNameZh());
				categoryBoEn.setCategoryName(attackContext.getCategoryNameEn());
				cachedAttacks.getZh().add(categoryBoZh);
				cachedAttacks.getEn().add(categoryBoEn);
			}
			FrameItemBase itemZh = new FrameItemBase();
			FrameItemBase itemEn = new FrameItemBase();
			itemZh.setResourceId(attackContext.getAttackId());
			itemZh.setName(attackContext.getAttackNameZh());
			itemEn.setResourceId(attackContext.getAttackId());
			itemEn.setName(attackContext.getAttackNameEn());

			categoryBoZh.getAttacks().add(itemZh);
			categoryBoEn.getAttacks().add(itemEn);
		}
	}
}

/**
 * Attack 缓存
 */
@Data
class AttackCache {

	private List<AttackCategoryBo<FrameItemBase>> zh = Lists.newArrayList();

	private List<AttackCategoryBo<FrameItemBase>> en = Lists.newArrayList();
}

/**
 * Attack 前台中文
 */
@Data
class AttackFrontZh {

	private List<AttackCategoryBo<AttackFrontBo>> zh;

}

/**
 * Attack 前台英文
 */
@Data
class AttackFrontEn {

	private List<AttackCategoryBo<AttackFrontBo>> en;
}

/**
 * Attack 后台中文
 */
@Data
class AttackBackZh {

	private List<AttackCategoryBo<AttackBackBo>> zh;

}

/**
 * Attack 后台英文
 */
@Data
class AttackBackEn {

	private List<AttackCategoryBo<AttackBackBo>> en;
}
