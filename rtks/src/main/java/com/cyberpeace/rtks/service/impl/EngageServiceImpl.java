package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.rtks.dao.entity.EngageTrainRelation;
import com.cyberpeace.rtks.dao.mapper.EngageMapper;
import com.cyberpeace.rtks.service.EngageService;
import com.cyberpeace.rtks.service.bo.EngageDetail;
import com.cyberpeace.rtks.service.bo.career.*;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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

		// get related business templates
		List<BusinessTemplateSimple> templates = engageMapper.getRelatedTemplates(resourceId);
		detail.setTemplates(templates);
		return detail;
	}

	@Override
	public void updateRelations(String resourceId, List<String> businessTemplateIds) {
		List<String> oldRel = engageMapper.getRelatedTemplateIds(resourceId);
		CrCommonUtil.differ2List(oldRel, businessTemplateIds);

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

	private Map<String, String> getOldNewMap() {
		Map<String, String> oldNewMap = Maps.newHashMap();
		oldNewMap.put("bd4817120ccc4882a76f432bb27f08f1", "SAC0004");
		oldNewMap.put("f35db4d2e8ad4587bf80ed90f7cb4218", "SAC0012");
		oldNewMap.put("f8ff871e12144280ae8698c0500be293", "SAC0005");
		oldNewMap.put("07e2bce31e0a4115a719aa565c1339c7", "SAC0001");
		oldNewMap.put("2a9894027add49ae8cfca2285b08c1fb", "SAC0002");
		oldNewMap.put("6b494634bbab45de9214e3296b46d532", "SAC0003");
		oldNewMap.put("96dc5a77224c40df8659218295eda5d0", "SAC0009");
		oldNewMap.put("7242633569774cf89c1f6110267b657e", "EAC0001");
		oldNewMap.put("5dbe69a917af47f9a83e559bbc8f368b", "EAC0002");
		oldNewMap.put("0a745b34e3de451e866180f369fe9a5d", "EAC0014");
		oldNewMap.put("79c8ec239e8644fc8b54967ca128184c", "EAC0003");
		oldNewMap.put("fe5ef1debcc14f60a80c6e2c0db84a83", "EAC0023");
		oldNewMap.put("e949fee196004dfeafa9da76ab78c461", "EAC0005");
		oldNewMap.put("4f6562ab745144a4b15c584eecee628f", "EAC0013");
		oldNewMap.put("35cec6b48d374455979016b95633c527", "EAC0004");
		oldNewMap.put("0ad5f0e22f7f4fd2b70c06d17b1131a6", "EAC0019");
		oldNewMap.put("e9eb6cc4d40846358602a13332cb743d", "EAC0017");
		oldNewMap.put("9a019500e2f84696b50a5d09d7d86cf9", "EAC0020");
		oldNewMap.put("b08fbeeb1737433d8bbac92b21eae3b9", "EAC0016");
		oldNewMap.put("2f9a6ffc48d6492caee6b4227fa8a691", "EAC0018");
		oldNewMap.put("1c46d87547f845b2829f74f99825ddd6", "EAC0021");
		oldNewMap.put("c20cbf2a6d5d4c0a88dffeb978e7fe40", "EAC0009");
		oldNewMap.put("6a3a4ee18ce6497ab740954733fdeefc", "EAC0023");
		oldNewMap.put("c0111e6692c645eaa738e47c92cc07a4", "EAC0005");
		oldNewMap.put("e00a01097edb49f48bf532c2e484e35e", "EAC0013");
		oldNewMap.put("003627bbb3974574bfe7d307a6774d4e", "EAC0016");
		oldNewMap.put("fb2a3b31076049f69b2b57ce1c02d597", "EAC0010");
		oldNewMap.put("45628f9c8c5d424a8718df641cf4cfa9", "EAC0018");
		oldNewMap.put("f0b0cf15f8964012ad674a25349194cb", "EAC0014");
		oldNewMap.put("ae4800e238a940b39d5701e85f6a18ae", "EAC0020");
		oldNewMap.put("3fc4cea17bf14d81959def65004659f8", "EAC0005");
		oldNewMap.put("8fade69b3b6c495aa37ef7596cc62e8a", "EAC0016");
		oldNewMap.put("31563ec6874b47578aa9d7fe8a1ed04a", "EAC0014");
		oldNewMap.put("0efd3406d1a1491cbd8a2dae26533f6a", "EAC0006");
		oldNewMap.put("b6c7b5b5d5744a94ad44ee25e0231ebd", "EAC0022");
		oldNewMap.put("77fe17e4461c4229814a36740da02c88", "EAC0008");
		oldNewMap.put("a335eddccea94322b50a00b71fe7d95e", "EAC0009");
		oldNewMap.put("4ddca25c7cd94c8faeddbbc8641f4068", "EAC0015");
		oldNewMap.put("b7bd81ae933f44e7b9b23191e635c615", "EAC0007");
		oldNewMap.put("c151b3dc3bea4dc0902d2163a3e8f38a", "EAC0010");
		oldNewMap.put("3dca2ed26184431f96be878949acdf7e", "EAC0011");
		oldNewMap.put("b1f09aab975f40c9977bf37ac88fd1e8", "EAC0006");
		oldNewMap.put("e88508bd7a064e93a9e1615ed9800b8d", "EAC0022");
		oldNewMap.put("183c250bb395489d8decffb3fb4aa289", "EAC0015");
		oldNewMap.put("2ef10e0904fb49d7b33b3f11fa441c89", "EAC0023");
		oldNewMap.put("c4b241fdaaec41b99465ace534f712a0", "EAC0013");
		oldNewMap.put("5a1fa286abfb48fd86c6584edb44d9ee", "EAC0007");
		oldNewMap.put("d42084cac51649538e45f05f807985c9", "EAC0012");
		oldNewMap.put("34436532322d404798fa4b6470fc9db9", "SAC0006");
		oldNewMap.put("155ca8ba9b5f4b4f960d1fdcddd4634d", "SAC0004");
		oldNewMap.put("f8594529332a4e67bd378f5a867f1925", "SAC0009");
		return oldNewMap;
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