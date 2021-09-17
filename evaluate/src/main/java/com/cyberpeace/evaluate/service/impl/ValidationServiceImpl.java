package com.cyberpeace.evaluate.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.evaluate.service.bo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.evaluate.dao.TestInfoDao;
import com.cyberpeace.evaluate.dao.TestInstanceDao;
import com.cyberpeace.evaluate.dao.entity.TestInfo;
import com.cyberpeace.evaluate.dao.entity.TestInstance;
import com.cyberpeace.evaluate.service.ValidationService;
import com.cyberpeace.evaluate.service.context.TestResultContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private TestInfoDao testInfoDao;

	@Autowired
	private TestInstanceDao testInstanceDao;

	@Override
	public ListRsp<TestInfo> pageInfo(String userId, Page pageBase) {

		IPage<TestInfo> data = testInfoDao.pageByUserId(userId, pageBase);
		return PageUtil.getListResult(data);
	}

	@Override
	public void addTestInfo(TestInfo testInfo) {
		testInfoDao.add(testInfo);
	}

	@Override
	public void deleteTestInfo(String infoId) {
		testInfoDao.deleteByInfoId(infoId);
	}

	@Override
	public TestRecordBO getTestRecord(String infoId) {
		TestRecordBO testRecordBO = new TestRecordBO();
		// 查询测评信息
		TestInfo testInfo = testInfoDao.selectByInfoId(infoId);
		if (null == testInfo) {
			return testRecordBO;
		}

		// 查询对应测评下的测评实例 每个测评实例对应一个测评结果
		List<TestInstance> allInstance = testInstanceDao.selectAllInstance(infoId);
		List<TestInstance> testInstances = allInstance.stream().filter(testInstance -> {
			if (testInstance.getStatus().intValue() == TestInstance.STATUS_FINISH) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(testInstances)) {
			return testRecordBO;
		}

		List<TestHistoryBO> historyBOS = Lists.newArrayList();
		Map<String, List<String>> indexValue = Maps.newHashMap();
		// 遍历测评实例
		for (int i = 0; i < testInstances.size(); i++) {

			List<HistoryDataBO> dataBOS = Lists.newArrayList();
			// 序列化测试结果对象
			TestResultContext resultContext = JSON.parseObject(testInstances.get(i).getResult(),
					TestResultContext.class);
			if (null == resultContext) {
				break;
			}

			// 构建测试历史对象
			TestHistoryBO historyBO = new TestHistoryBO();
			historyBO.setName(resultContext.getName());
			historyBO.setDesc(resultContext.getDesc());
			historyBO.setIndex(i);
			historyBO.setStartTime(resultContext.getStartTime());
			// 添加到测试历史的集合中
			historyBOS.add(historyBO);

			// 遍历测试项
			List<TestItemBO> items = resultContext.getItems();
			items.forEach(testItemBO -> {
				// 获取指标项列表
				List<TestIndexBO> indices = testItemBO.getIndices();
				indices.forEach(testIndexBO -> {
					HistoryDataBO dataBO = new HistoryDataBO();
					dataBO.setItemId(testItemBO.getItemId());
					dataBO.setIndexName(testIndexBO.getIndexName());
					dataBO.setItemName(testItemBO.getItemName());
					dataBO.setIndexId(testIndexBO.getIndexId());
					// 将值放入map中 后续获取
					if (indexValue.containsKey(testIndexBO.getIndexId())) {
						indexValue.get(testIndexBO.getIndexId())
								.add(null != testIndexBO.getValue() ? testIndexBO.getValue() : "");
					} else {
						List<String> list = Lists.newArrayList();
						String value = testIndexBO.getValue();
						list.add(null != value ? value : "");
						indexValue.put(testIndexBO.getIndexId(), list);
					}
					dataBOS.add(dataBO);
				});
			});
			testRecordBO.setTestData(dataBOS);
		}
		// 设置每次测评的结果数据
		testRecordBO.getTestData().forEach(historyDataBO -> {
			historyDataBO.setValues(indexValue.get(historyDataBO.getIndexId()));
		});
		testRecordBO.setTests(historyBOS);

		return testRecordBO;
	}
}
