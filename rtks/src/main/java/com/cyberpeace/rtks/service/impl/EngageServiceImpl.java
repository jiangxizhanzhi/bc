package com.cyberpeace.rtks.service.impl;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.dao.mapper.EngageMapper;
import com.cyberpeace.rtks.service.EngageService;
import com.cyberpeace.rtks.service.bo.EngageDetail;
import com.cyberpeace.rtks.service.bo.career.*;
import com.cyberpeace.rtks.util.RtksCommonUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EngageServiceImpl implements EngageService {

	private final Object lock = new Object();

	@Autowired
	private EngageMapper engageMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	private volatile EngageCache cachedEngages;

	@Override
	public List<EngageCategoryBo<EngageBackBo>> getEngages() {
		ensureEngageCache();
		List<EngageCategoryBo<EngageBackBo>> res;
		if (CommonContext.isChinese()) {
			res = JsonUtil.toJsonObject(cachedEngages.getJson(), EngageBackZh.class).getZh();
		} else {
			res = JsonUtil.toJsonObject(cachedEngages.getJson(), EngageBackEn.class).getEn();
		}
		// count relation
		List<RelCount> countList = engageMapper.countRelsByEngageId();
		Map<String, Integer> countMap = Maps.newHashMap();
		countList.forEach((count) -> {
			countMap.put(count.getResourceId(), count.getCount());
		});
		// set count
		res.forEach((category -> {
			category.getSubCategories().forEach((subCategoryBo) -> {
				subCategoryBo.getEngages().forEach((engageBackBo) -> {
					if (countMap.containsKey(engageBackBo.getResourceId())) {
						engageBackBo.setCount(countMap.get(engageBackBo.getResourceId()));
					}
				});
			});
		}));
		return res;
	}

	@Override
	public EngageDetail getDetailById(String resourceId) {
		ensureEngageCache();
		EngageDetail detail = new EngageDetail();
		List<EngageCategoryBo<FrameItemBase>> cache;
		if (CommonContext.isChinese()) {
			cache = cachedEngages.getZh();
		} else {
			cache = cachedEngages.getEn();
		}
		// search
		cache.forEach((category) -> {
			category.getSubCategories().forEach((subCategory) -> {
				subCategory.getEngages().forEach((engage) -> {
					if (engage.getResourceId().equals(resourceId)) {
						detail.setCategoryName(category.getCategoryName());
						detail.setSubCategoryName(subCategory.getSubCategoryName());
						detail.setResourceId(engage.getResourceId());
						detail.setName(engage.getName());
					}
				});
			});
		});
		return detail;
	}

	@Override
	public void updateRelations(String resourceId, List<String> businessTemplateIds) {
		List<String> oldRel = engageMapper.getRelatedTemplateIds(resourceId);
		RtksCommonUtil.differ2List(oldRel, businessTemplateIds);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				engageMapper.lockRel();
				if (!oldRel.isEmpty()) {
					engageMapper.deleteRel(resourceId, oldRel);
				}
				if (!businessTemplateIds.isEmpty()) {
					engageMapper.addRel(resourceId, businessTemplateIds);
				}
			}
		});
	}

	@Override
	public List<EngageCategoryBo<EngageFrontBo>> getEngagesByTraining(String businessTemplateId) {
		ensureEngageCache();
		List<EngageCategoryBo<EngageFrontBo>> res;
		if (CommonContext.isChinese()) {
			res = JsonUtil.toJsonObject(cachedEngages.getJson(), EngageFrontZh.class).getZh();
		} else {
			res = JsonUtil.toJsonObject(cachedEngages.getJson(), EngageFrontEn.class).getEn();
		}
		List<String> relatedEngageIds = engageMapper.getRelByBusinessTemplateId(businessTemplateId);
		// set whether related
		res.forEach((category -> {
			category.getSubCategories().forEach((subCategoryBo -> {
				subCategoryBo.getEngages().forEach((engageFrontBo -> {
					if (relatedEngageIds.contains(engageFrontBo.getResourceId())) {
						engageFrontBo.setRelated(true);
					}
				}));
			}));
		}));
		return res;
	}

	@Override
	public List<EngageCategoryBo<FrameItemBase>> getCache() {
		ensureEngageCache();
		if (CommonContext.isChinese()) {
			return cachedEngages.getZh();
		}
		return cachedEngages.getEn();
	}

	private void ensureEngageCache() {
		if (null == cachedEngages) {
			synchronized (lock) {
				if (null == cachedEngages) {
					initializeEngages();
				}
			}
		}
	}

	/**
	 * 读取 Engage 缓存
	 */
	private void initializeEngages() {
		List<EngageContext> engageContextList = engageMapper.getAll();
		cachedEngages = new EngageCache();
		String categoryId = "";
		String subCategoryId = "";
		EngageCategoryBo<FrameItemBase> categoryBoZh = new EngageCategoryBo<>();
		EngageCategoryBo<FrameItemBase> categoryBoEn = new EngageCategoryBo<>();
		EngageSubCategoryBo<FrameItemBase> subCategoryBoZh = new EngageSubCategoryBo<>();
		EngageSubCategoryBo<FrameItemBase> subCategoryBoEn = new EngageSubCategoryBo<>();
		for (EngageContext engageContext : engageContextList) {
			if (!categoryId.equals(engageContext.getCategoryId())) {
				categoryId = engageContext.getCategoryId();
				categoryBoZh = new EngageCategoryBo<>();
				categoryBoEn = new EngageCategoryBo<>();
				categoryBoZh.setCategoryName(engageContext.getCategoryNameZh());
				categoryBoEn.setCategoryName(engageContext.getCategoryNameEn());
				cachedEngages.getZh().add(categoryBoZh);
				cachedEngages.getEn().add(categoryBoEn);
			}
			if (!subCategoryId.equals(engageContext.getSubCategoryId())) {
				subCategoryId = engageContext.getSubCategoryId();
				subCategoryBoZh = new EngageSubCategoryBo<>();
				subCategoryBoEn = new EngageSubCategoryBo<>();
				subCategoryBoZh.setSubCategoryName(engageContext.getSubCategoryNameZh());
				subCategoryBoEn.setSubCategoryName(engageContext.getSubCategoryNameEn());
				categoryBoZh.getSubCategories().add(subCategoryBoZh);
				categoryBoEn.getSubCategories().add(subCategoryBoEn);
			}
			FrameItemBase itemZh = new FrameItemBase();
			FrameItemBase itemEn = new FrameItemBase();
			itemZh.setResourceId(engageContext.getEngageId());
			itemZh.setName(engageContext.getEngageNameZh());
			itemEn.setResourceId(engageContext.getEngageId());
			itemEn.setName(engageContext.getEngageNameEn());

			subCategoryBoZh.getEngages().add(itemZh);
			subCategoryBoEn.getEngages().add(itemEn);
		}

		cachedEngages.setJson(JsonUtil.toJsonString(cachedEngages));
	}

}

/**
 * Engage 缓存
 */
@Data
class EngageCache {

	private List<EngageCategoryBo<FrameItemBase>> zh = Lists.newArrayList();

	private List<EngageCategoryBo<FrameItemBase>> en = Lists.newArrayList();

	private String json;

}

/**
 * Engage 前台中文
 */
@Data
class EngageFrontZh {

	private List<EngageCategoryBo<EngageFrontBo>> zh;

}

/**
 * Engage 前台英文
 */
@Data
class EngageFrontEn {

	private List<EngageCategoryBo<EngageFrontBo>> en;
}

/**
 * Engage 后台中文
 */
@Data
class EngageBackZh {

	private List<EngageCategoryBo<EngageBackBo>> zh;

}

/**
 * Engage 后台英文
 */
@Data
class EngageBackEn {

	private List<EngageCategoryBo<EngageBackBo>> en;
}