package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.rtks.dao.mapper.AttackIcsMapper;
import com.cyberpeace.rtks.service.AttackIcsService;
import com.cyberpeace.rtks.service.bo.career.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttackIcsServiceImpl implements AttackIcsService {

	private final Object lock = new Object();

	@Autowired
	private AttackIcsMapper attackIcsMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	private AttackCache cachedAttacks;

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
		List<RelCount> countList = attackIcsMapper.countRelsByAttackId();
		Map<String, Integer> countMap = Maps.newHashMap();
		countList.forEach(count -> countMap.put(count.getResourceId(), count.getCount()));
		// set count
		res.forEach((category -> category.getAttacks().forEach((attackBackBo -> {
			if (countMap.containsKey(attackBackBo.getResourceId())) {
				attackBackBo.setCount(countMap.get(attackBackBo.getResourceId()));
			}
		}))));

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
		List<String> relatedAttackIds = attackIcsMapper
				.getRelByBusinessTemplateId(businessTemplateId);
		// set whether related
		res.forEach((category -> category.getAttacks().forEach((attackFrontBo -> {
			if (relatedAttackIds.contains(attackFrontBo.getResourceId())) {
				attackFrontBo.setRelated(true);
			}
		}))));
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
		cache.forEach(category -> category.getAttacks().forEach(attack -> {
			if (attack.getResourceId().equals(resourceId)) {
				detail.setCategoryName(category.getCategoryName());
				detail.setResourceId(attack.getResourceId());
				detail.setName(attack.getName());
			}
		}));
		// get related business templates
		List<BusinessTemplateSimple> templates = attackIcsMapper.getRelatedTemplates(resourceId);
		detail.setTemplates(templates);
		return detail;
	}

	@Override
	public void updateRelations(String resourceId, List<String> businessTemplateIds) {
		List<String> oldRel = attackIcsMapper.getRelatedTemplateIds(resourceId);
		CrCommonUtil.differ2List(oldRel, businessTemplateIds);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (!oldRel.isEmpty()) {
					attackIcsMapper.deleteRel(resourceId, oldRel);
				}
				if (!businessTemplateIds.isEmpty()) {
					attackIcsMapper.addRel(resourceId, businessTemplateIds);
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
		List<AttackContext> attackContextList = attackIcsMapper.getAll();
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
class AttackIcsCache {

	private List<AttackCategoryBo<FrameItemBase>> zh = Lists.newArrayList();

	private List<AttackCategoryBo<FrameItemBase>> en = Lists.newArrayList();
}

/**
 * Attack 前台中文
 */
@Data
class AttackIcsFrontZh {

	private List<AttackCategoryBo<AttackFrontBo>> zh;

}

/**
 * Attack 前台英文
 */
@Data
class AttackIcsFrontEn {

	private List<AttackCategoryBo<AttackFrontBo>> en;
}

/**
 * Attack 后台中文
 */
@Data
class AttackIcsBackZh {

	private List<AttackCategoryBo<AttackBackBo>> zh;

}

/**
 * Attack 后台英文
 */
@Data
class AttackIcsBackEn {

	private List<AttackCategoryBo<AttackBackBo>> en;
}