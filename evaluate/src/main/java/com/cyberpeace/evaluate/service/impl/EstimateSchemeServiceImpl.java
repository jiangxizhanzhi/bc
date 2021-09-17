package com.cyberpeace.evaluate.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.evaluate.dao.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.evaluate.dao.entity.Appendix;
import com.cyberpeace.evaluate.dao.entity.EstimateIndexLinks;
import com.cyberpeace.evaluate.dao.entity.EstimateScheme;
import com.cyberpeace.evaluate.dao.entity.IndexVariableLinks;
import com.cyberpeace.evaluate.service.EstimateSchemeService;
import com.cyberpeace.evaluate.service.context.EstimateSchemeContext;
import com.google.common.collect.Lists;

@Service
public class EstimateSchemeServiceImpl implements EstimateSchemeService {

	@Autowired
	private EstimateSchemeDao estimateSchemeDao;

	@Autowired
	private EstimateVariableDao estimateVariableDao;

	@Autowired
	private EstimateIndexDao estimateIndexDao;

	@Autowired
	private EstimateItemsDao estimateItemsDao;

	@Autowired
	private IndexVariableLinksDao indexVariableLinksDao;

	@Autowired
	private EstimateIndexLinksDao estimateIndexLinksDao;

	@Autowired
	@Qualifier("rtksTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AppendixDao appendixDao;

	@Override
	public ListRsp<EstimateScheme> selectList(Page pageBase) {

		return PageUtil.getListResult(estimateSchemeDao.selectList(pageBase));
	}

	@Override
	public EstimateSchemeContext selectById(String schemeId) {
		EstimateScheme estimateScheme = estimateSchemeDao.selectById(schemeId);
		if (null != estimateScheme && StringUtils.isNotBlank(estimateScheme.getContent())) {
			EstimateSchemeContext estimateSchemeContext = JSON
					.parseObject(estimateScheme.getContent(), EstimateSchemeContext.class);
			Appendix appendix = appendixDao.selectById(estimateScheme.getLogo());
			if (null != appendix){
				estimateSchemeContext.setLogo(appendix.getAppendixUrl());
			}
			return estimateSchemeContext;
		}
		return null;
	}

	@Override
	public String add(EstimateSchemeContext context) {

		EstimateScheme scheme = estimateSchemeDao.selectById(context.getEstimateSchemeId());
		if (null == scheme) {
			addNew(context);
		} else {
			modifyScheme(context);
		}

		return context.getEstimateSchemeId();
	}

	@Override
	public void deleteById(String schemeId) {
		estimateSchemeDao.delete(schemeId);
	}

	/**
	 * 新增方案及相关数据
	 * @param context
	 */
	private void addNew(EstimateSchemeContext context) {
		// 新增方案信息
		EstimateScheme estimateScheme = new EstimateScheme();
		BeanUtils.copyProperties(context, estimateScheme);

		List<IndexVariableLinks> linksList = Lists.newArrayList();
		// 新增变量项和指标项关联信息
		context.getEstimateIndexes().forEach(estimateIndexContext -> {
			// 获取指标项编号
			String indexId = estimateIndexContext.getEstimateIndexId();

			// 获取关联的所有变量项编号
			List<String> variableIds = estimateIndexContext.getAggregation();
			if (CollectionUtils.isNotEmpty(variableIds)) {
				for (int i = 0; i < variableIds.size(); i++) {
					// 设置数据关联关系
					IndexVariableLinks indexVariableLinks = new IndexVariableLinks();
					indexVariableLinks.setIndexItemsId(indexId);
					indexVariableLinks.setVariableItemsId(variableIds.get(i));
					indexVariableLinks.setSerialNumber(i);
					indexVariableLinks.setIndexVariableLinksId(IDGeneratorUtil.getID());
					indexVariableLinks.setEstimateSchemeId(context.getEstimateSchemeId());
					linksList.add(indexVariableLinks);
				}
			}

		});

		List<EstimateIndexLinks> indexLinks = Lists.newArrayList();
		// 新增指标项和评估项关联信息
		context.getEstimateItems().forEach(estimateItemsContext -> {
			// 获取评估项编号
			String itemId = estimateItemsContext.getEstimateItemsId();
			// 获取关联的所有指标项编号
			List<String> indexIds = estimateItemsContext.getAggregation();
			if (CollectionUtils.isNotEmpty(indexIds)) {
				for (int i = 0; i < indexIds.size(); i++) {
					// 设置数据关联关系
					EstimateIndexLinks estimateIndexLinks = new EstimateIndexLinks();
					estimateIndexLinks.setEstimateItemsId(itemId);
					estimateIndexLinks.setEstimateIndexId(indexIds.get(i));
					estimateIndexLinks.setSerialNumber(i);
					estimateIndexLinks.setEstimateSchemeId(context.getEstimateSchemeId());
					estimateIndexLinks.setEstimateIndexLinkId(IDGeneratorUtil.getID());
					indexLinks.add(estimateIndexLinks);
				}
			}
		});

		// 事务执行新增方案
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				// 新增变量项
				context.getEstimateVariables().forEach(estimateVariable -> {
					estimateVariable.setEstimateSchemeId(context.getEstimateSchemeId());
					estimateVariableDao.insert(estimateVariable);
				});
				// 新增指标项
				context.getEstimateIndexes().forEach(estimateIndex -> {
					estimateIndex.setEstimateSchemeId(context.getEstimateSchemeId());
					estimateIndexDao.insert(estimateIndex);
				});

				// 新增评估项
				context.getEstimateItems().forEach(estimateItem -> {
					estimateItem.setEstimateSchemeId(context.getEstimateSchemeId());
					estimateItemsDao.add(estimateItem);
				});

				// 新增指标项和评估项关联信息
				estimateIndexLinksDao.batchAdd(indexLinks);
				// 新增变量项和指标项关联信息
				indexVariableLinksDao.batchAdd(linksList);

				// 新增方案
				estimateScheme.setContent(JSON.toJSONString(context));
				estimateSchemeDao.insert(estimateScheme);
			}
		});
	}

	/**
	 * 修改评估方案
	 * @param context
	 */
	private void modifyScheme(EstimateSchemeContext context) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				deleteScheme(context.getEstimateSchemeId());
				addNew(context);
			}
		});

	}

	/**
	 * 删除装备评估方案
	 * @param schemeId
	 */
	private void deleteScheme(String schemeId) {

		// 删除新增变量项和指标项关联信息
		indexVariableLinksDao.deleteByScheme(schemeId);
		// 删除指标项和评估项关联信息
		estimateIndexLinksDao.deleteByScheme(schemeId);
		// 删除评估项
		estimateItemsDao.deleteByScheme(schemeId);
		// 删除指标项
		estimateIndexDao.deleteByScheme(schemeId);
		// 删除变量项
		estimateVariableDao.deleteByScheme(schemeId);
		// 删除方案
		estimateSchemeDao.delete(schemeId);
	}
}
