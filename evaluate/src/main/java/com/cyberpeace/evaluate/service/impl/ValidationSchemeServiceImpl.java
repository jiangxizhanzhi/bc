package com.cyberpeace.evaluate.service.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.evaluate.dao.*;
import com.cyberpeace.evaluate.service.bo.TestSchemeBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.evaluate.dao.entity.*;
import com.cyberpeace.evaluate.service.ValidationSchemeService;
import com.cyberpeace.evaluate.service.context.TestSchemeContext;
import com.google.common.collect.Lists;

@Service
public class ValidationSchemeServiceImpl implements ValidationSchemeService {

	@Autowired
	@Qualifier("rtksTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Autowired
	private TestSchemeDao testSchemeDao;

	@Autowired
	private TestItemDao testItemDao;

	@Autowired
	private TestIndexDao testIndexDao;

	@Autowired
	private TestItemIndexLinkDao testItemIndexLinkDao;

	@Autowired
	private TestItemLinkDao testItemLinkDao;

	@Override
	public ListRsp<TestSchemeBO> schemeList(Page pageBase) {

		IPage<TestScheme> result = testSchemeDao.selectByPage(pageBase);

		result.convert(testScheme -> {
			TestSchemeBO bo = new TestSchemeBO();
			bo.setDesc(testScheme.getSchemeDesc());
			bo.setName(testScheme.getSchemeName());
			bo.setLogo(testScheme.getLogoId());
			bo.setTestSchemeId(testScheme.getTestSchemeId());
			return bo;
		});

		return PageUtil.getListResult(result);
	}

	@Override
	public String addScheme(TestSchemeContext testSchemeAddContext) {
		return addInternal(testSchemeAddContext, false);
	}

	@Override
	public TestSchemeContext getBySchemeId(String schemeId) {
		TestScheme testScheme = testSchemeDao.selectBySchemeId(schemeId);

		if (null != testScheme && StringUtils.isNotBlank(testScheme.getContent())) {
			return JSON.parseObject(testScheme.getContent(), TestSchemeContext.class);
		}
		return null;
	}

	@Override
	public void deleteScheme(String testSchemeId) {
		List<TestItem> testItems = testItemDao.selectBySchemeId(testSchemeId);
		List<String> items = Lists.newArrayList();
		testItems.forEach(testItem -> items.add(testItem.getItemId()));

		// 执行删除
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				testSchemeDao.deleteBySchemeId(testSchemeId);
				testItemDao.deleteBySchemeId(testSchemeId);
				testItemLinkDao.deleteBySchemeId(testSchemeId);
				testIndexDao.batchDelete(items);
				testItemIndexLinkDao.batchDelete(items);
			}
		});
	}

	@Override
	public void modifyScheme(TestSchemeContext testSchemeAddContext) {
		addInternal(testSchemeAddContext, true);
	}

	private String addInternal(TestSchemeContext testSchemeAddContext, boolean modify) {
		// 1、生成测试方案
		TestScheme testScheme = new TestScheme();
		BeanUtils.copyProperties(testSchemeAddContext, testScheme);
		if (!modify) {
			testScheme.setTestSchemeId(IDGeneratorUtil.getID());
			testSchemeAddContext.setTestSchemeId(testScheme.getTestSchemeId());
		}

		// 2、逐个生成测试项、测试指标数据
		List<TestItem> testItems = Lists.newArrayList();
		List<TestIndex> testIndices = Lists.newArrayList();
		List<TestItemIndexLink> testItemIndexLinks = Lists.newArrayList();
		List<TestItemLink> testItemLinks = Lists.newArrayList();

		AtomicReference<Integer> itemIndex = new AtomicReference<>(0);

		// 3、逐个遍历，生成2中需要的数据
		testSchemeAddContext.getItems().forEach(item -> {
			// 3.1、生成测试项数据
			TestItem testItem = new TestItem();
			BeanUtils.copyProperties(item, testItem);
			if (!modify) {
				testItem.setItemId(IDGeneratorUtil.getID());
				item.setItemId(testItem.getItemId());
			}
			testItem.setSchemeId(testScheme.getTestSchemeId());

			testItems.add(testItem);

			// 3.2、测试量偏移项加1
			itemIndex.getAndSet(itemIndex.get() + 1);

			// 3.3、生成测试项和测试方案关联数据
			TestItemLink testItemLink = new TestItemLink();
			testItemLink.setIdx(itemIndex.get());
			testItemLink.setItemId(testItem.getItemId());
			testItemLink.setSchemeId(testScheme.getTestSchemeId());
			testItemLinks.add(testItemLink);

			// 3.4、测试指标偏移量
			AtomicReference<Integer> idx = new AtomicReference<>(0);

			item.getIndexes().forEach(index -> {
				// 3.5、生成测试指标数据
				TestIndex testIndex = new TestIndex();
				BeanUtils.copyProperties(index, testIndex);
				if (!modify) {
					testIndex.setIndexId(IDGeneratorUtil.getID());
					index.setIndexId(testIndex.getIndexId());
				}
				testIndex.setItemId(testItem.getItemId());

				testIndices.add(testIndex);

				// 3.6、测试指标偏移量加1
				idx.getAndSet(idx.get() + 1);

				// 3.7、生成测试项、测试指标关联数据
				TestItemIndexLink testItemIndexLink = new TestItemIndexLink();
				testItemIndexLink.setIdx(idx.get());
				testItemIndexLink.setIndexId(testIndex.getIndexId());
				testItemIndexLink.setItemId(testItem.getItemId());

				testItemIndexLinks.add(testItemIndexLink);
			});
		});

		if (modify) {
			deleteScheme(testScheme.getTestSchemeId());
		}

		testScheme.setContent(JSON.toJSONString(testSchemeAddContext));

		// 4、保存数据
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				testSchemeDao.add(testScheme);
				testItems.forEach(testItem -> testItemDao.add(testItem));
				testIndices.forEach(testIndex -> testIndexDao.add(testIndex));
				testItemIndexLinks
						.forEach(testItemIndexLink -> testItemIndexLinkDao.add(testItemIndexLink));
				testItemLinks.forEach(testItemLink -> testItemLinkDao.add(testItemLink));
			}
		});

		return testScheme.getTestSchemeId();
	}
}
