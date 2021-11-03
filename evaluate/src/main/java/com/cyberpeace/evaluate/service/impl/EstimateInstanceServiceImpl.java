package com.cyberpeace.evaluate.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.evaluation.CoordinateDate;
import com.cyberpeace.common.model.evaluation.ProbeOriginData;
import com.cyberpeace.common.model.evaluation.ProbeOriginPageData;
import com.cyberpeace.common.model.evaluation.TimeSequenceValue;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.evaluate.constant.EstimateConstant;
import com.cyberpeace.evaluate.dao.*;
import com.cyberpeace.evaluate.dao.entity.*;
import com.cyberpeace.evaluate.service.EstimateInstanceService;
import com.cyberpeace.evaluate.service.bo.EstimateInstanceBO;
import com.cyberpeace.evaluate.service.bo.EstimateInstanceViewBO;
import com.cyberpeace.evaluate.service.bo.EstimateReviewBo;
import com.cyberpeace.evaluate.service.context.EstimateInstanceContext;
import com.cyberpeace.integratation.service.ProbeService;
import com.cyberpeace.integratation.service.RemotePythonRuntimeService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class EstimateInstanceServiceImpl implements EstimateInstanceService {

	@Autowired
	private EstimateInstanceDao estimateInstanceDao;

	@Autowired
	private EstimateInfoDao estimateInfoDao;

	@Autowired
	private EstimateVariableLinksDao estimateVariableLinksDao;

	@Autowired
	private EstimateVariableDao estimateVariableDao;

	@Autowired
	private EstimateFormatDataDao estimateFormatDataDao;

	@Autowired
	private EstimateAlgorithmDao estimateAlgorithmDao;

	@Autowired
	private RemotePythonRuntimeService remotePythonRuntimeService;

	@Autowired
	private EstimateItemsDao estimateItemsDao;

	@Autowired
	private EstimateIndexLinksDao estimateIndexLinksDao;

	@Autowired
	private EstimateIndexDao estimateIndexDao;

	@Autowired
	private IndexVariableLinksDao indexVariableLinksDao;

	@Autowired
	@Qualifier("rtksTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EstimateVariableModelOutDao estimateVariableModelOutDao;

	@Autowired
	private EstimateVariableOutDao estimateVariableOutDao;

	@Autowired
	private EstimateIndexOutDao estimateIndexOutDao;

	@Autowired
	private EstimateItemsOutDao estimateItemsOutDao;

	@Autowired
	private ProbeService probeService;

	@Override
	public EstimateInstance
			saveEstimateInstanceResult(EstimateInstanceContext estimateInstanceContext) {
		EstimateInstance estimateInstance = new EstimateInstance();
		estimateInstance.setEstimateInstanceId(estimateInstanceContext.getEstimateInstanceId());
		estimateInstance.setSave(EstimateConstant.MANUAL_SAVE);
		estimateInstance.setStatus(EstimateConstant.ESTIMATE_CALCULATE);
		estimateInstance.setEndTime(new Date());
		estimateInstance.setInstanceName(estimateInstanceContext.getInstanceName());
		estimateInstance.setInstanceDesc(estimateInstanceContext.getInstanceDesc());
		estimateInstanceDao.update(estimateInstance);

		class CalculateThread implements Runnable {

			private String instanceId;

			public CalculateThread(String instanceId) {
				this.instanceId = instanceId;
			}

			@Override
			public void run() {
				calculateAndSave(this.instanceId);
			}
		}
		CalculateThread task = new CalculateThread(estimateInstanceContext.getEstimateInstanceId());
		Thread thread = new Thread(task);
		thread.start();
		return estimateInstance;
	}

	@Override
	public EstimateInstance
			destroyEstimateInstanceResult(EstimateInstanceContext estimateInstanceContext) {
		EstimateInstance estimateInstance = new EstimateInstance();
		estimateInstance.setEstimateInstanceId(estimateInstanceContext.getEstimateInstanceId());
		estimateInstance.setStatus(EstimateConstant.ESTIMATE_ABANDON);
		estimateInstanceDao.update(estimateInstance);
		return estimateInstance;
	}

	@Override
	public void stopEstimateInstance(String instanceId) {
		EstimateInstance estimateInstance = new EstimateInstance();
		estimateInstance.setEstimateInstanceId(instanceId);
		estimateInstance.setEndTime(new Date());
		estimateInstance.setStatus(EstimateConstant.ESTIMATE_STOP);
		estimateInstanceDao.update(estimateInstance);
	}

	@Override
	public EstimateInstanceViewBO getEstimateInstance(String estimateId) {

		EstimateInstanceViewBO estimateInstanceViewBO = new EstimateInstanceViewBO();
		// 先查询是否有进行中的实例；
		Integer status = EstimateConstant.ESTIMATE_ON;
		EstimateInstance estimateInstance = estimateInstanceDao.getByEstimateIdAndStatus(estimateId,
				status);
		// 如果没有，那么查询最近的已结束的实例
		if (null == estimateInstance) {
			status = EstimateConstant.ESTIMATE_FINISH;
			estimateInstance = estimateInstanceDao.getByEstimateIdAndStatus(estimateId, status);
		}

		if (null != estimateInstance) {
			BeanUtils.copyProperties(estimateInstance, estimateInstanceViewBO);
		}

		// 查找评估
		EstimateInfo estimateInfo = estimateInfoDao.getByEstimateId(estimateId);
		if (null != estimateInfo) {
			estimateInstanceViewBO.setEstimateId(estimateInfo.getEstimateId());
			estimateInstanceViewBO.setSceneId(estimateInfo.getTopoId());
			estimateInstanceViewBO.setEstimateDesc(estimateInfo.getDesc());
			estimateInstanceViewBO.setEstimateName(estimateInfo.getName());

			// 如果没有运行中的实例，仅展示方案结构
			if (null == estimateInstance || StringUtils.isBlank(estimateInstance.getResult())) {

				// 设置评估结果的结构（JSON）
				List<NameValueItem> estimateItemList = Lists.newArrayList();
				List<EstimateItems> estimateItemsList = estimateItemsDao
						.selectByScheme(estimateInfo.getEstimateSchemeId());
				// 每一个评估项
				estimateItemsList.forEach(estimate -> {
					NameValueItem estimateItem = new NameValueItem();

					List<EstimateIndexLinks> estimateIndexLinksList = estimateIndexLinksDao
							.selectBySchemeAndItem(estimateInfo.getEstimateSchemeId(),
									estimate.getEstimateItemsId());

					estimateIndexLinksList.forEach(indexLink -> {

						NameValueItem indexItem = new NameValueItem();
						// 每一个指标项
						List<IndexVariableLinks> indexVariableLinksList = indexVariableLinksDao
								.selectBySchemeAndIndex(estimateInfo.getEstimateSchemeId(),
										indexLink.getEstimateIndexId());

						indexVariableLinksList.forEach(variableLink -> {

							NameValueItem variableItem = new NameValueItem();
							// 填充变量信息
							EstimateVariable estimateVariable = estimateVariableDao
									.getById(variableLink.getVariableItemsId());
							variableItem.setName(estimateVariable.getName());
							indexItem.getLinks().add(variableItem);
						});

						// 填充指标信息
						EstimateIndex estimateIndex = estimateIndexDao
								.selectById(indexLink.getEstimateIndexId());
						indexItem.setName(estimateIndex.getName());
						estimateItem.getLinks().add(indexItem);
					});

					// 填充评估项信息
					EstimateItems estimateItems = estimateItemsDao
							.selectById(estimate.getEstimateItemsId());
					estimateItem.setName(estimateItems.getName());
					estimateItemList.add(estimateItem);

				});

				estimateInstanceViewBO.setResultList(estimateItemList);
			} else {
				// 结果中包含方案结构信息
				estimateInstanceViewBO.setResultList(
						JSON.parseArray(estimateInstance.getResult(), NameValueItem.class));
			}
		}

		return estimateInstanceViewBO;

	}

	@Override
	public EstimateInstance
			createEstimateInstance(EstimateInstanceContext estimateInstanceContext) {
		EstimateInstance estimateInstance = new EstimateInstance();
		BeanUtils.copyProperties(estimateInstanceContext, estimateInstance);
		String estimateInstanceId = UUID.randomUUID().toString();
		estimateInstance.setEstimateInstanceId(estimateInstanceId);
		estimateInstance.setStatus(EstimateConstant.ESTIMATE_ON);
		estimateInstance.setStartTime(new Date());

		// 场景改为保存topoid,
		EstimateInfo estimateInfo = estimateInfoDao
				.getByEstimateId(estimateInstanceContext.getEstimateId());
		if (null == estimateInfo) {
			return null;
		}
		estimateInstance.setSceneId(estimateInfo.getTopoId());
		estimateInstanceDao.insert(estimateInstance);
		return estimateInstance;
	}

	@Override
	public List<EstimateReviewBo> reviewEstimateInstanceFormalData(String instanceId) {

		// 1、查询对应的评估实例和评估
		EstimateInstance instance = estimateInstanceDao.getByInstanceId(instanceId);
		EstimateInfo estimate = estimateInfoDao.getByEstimateId(instance.getEstimateId());

		// 2、查询评估对应的变量项配置
		List<EstimateVariableLinks> estimateVariableLinks = estimateVariableLinksDao
				.getByEstimateId(estimate.getEstimateId());

		// 3、查询格式化的变量项数据
		List<EstimateReviewBo> estimateReviewBoList = Lists.newArrayList();
		if (null != estimateVariableLinks && !estimateVariableLinks.isEmpty()) {
			estimateVariableLinks.forEach(link -> {
				// 查询一项变量项
				EstimateReviewBo estimateReviewBo = new EstimateReviewBo();
				// 1、填充名称
				EstimateVariable estimateVariable = estimateVariableDao
						.getById(link.getEstimateVariableId());
				estimateReviewBo.setInstanceId(instanceId);
				estimateReviewBo.setVariableName(estimateVariable.getName());
				estimateReviewBo.setVariableId(link.getEstimateVariableId());

				// 2、根据（场景+主机+变量项+实例）查询指定长度的数据
				EstimateFormatData estimateFormatData = new EstimateFormatData();
				estimateFormatData.setSceneId(instance.getSceneId());
				estimateFormatData.setHostId(link.getHostId());
				estimateFormatData.setEstimateVariableId(link.getEstimateVariableId());
				estimateFormatData.setEstimateInstanceId(instance.getEstimateInstanceId());
				List<EstimateFormatData> formatDataList = estimateFormatDataDao
						.select(estimateFormatData, null);

				// 3、填充数据
				if (null != formatDataList && !formatDataList.isEmpty()) {
					List<Date> xList = formatDataList.stream()
							.map(EstimateFormatData::getCollectDate).collect(Collectors.toList());
					List<Integer> yList = formatDataList.stream().map(EstimateFormatData::getValue)
							.collect(Collectors.toList());
					CoordinateDate coordinateDate = new CoordinateDate();
					coordinateDate.setX(xList);
					coordinateDate.setY(yList);
					estimateReviewBo.setDate(coordinateDate);
				}

				// add一项格式化数据
				estimateReviewBoList.add(estimateReviewBo);
			});
		}
		return estimateReviewBoList;
	}

	@Override
	public EstimateReviewBo reviewEstimateInstanceModelData(String instanceId, String variableId) {
		// 1、查询对应的评估实例
		EstimateInstance instance = estimateInstanceDao.getByInstanceId(instanceId);

		// 2、评估id+变量id找到主机和采集器的信息
		EstimateVariableLinks estimateVariableLinks = estimateVariableLinksDao
				.getOneLink(instance.getEstimateId(), variableId);

		// 3、根据场景+主机+变量项+实例找到对应的模型匹配数据
		EstimateVariableModelOut estimateVariableModelOut = new EstimateVariableModelOut();
		estimateVariableModelOut.setSceneId(instance.getSceneId());
		estimateVariableModelOut.setHostId(estimateVariableLinks.getHostId());
		estimateVariableModelOut.setVariableId(variableId);
		estimateVariableModelOut.setEstimateInstanceId(instanceId);

		EstimateReviewBo estimateReviewBo = new EstimateReviewBo();
		estimateReviewBo.setInstanceId(instanceId);
		estimateReviewBo.setVariableId(variableId);

		List<EstimateVariableModelOut> estimateVariableModelOutList = estimateVariableModelOutDao
				.select(estimateVariableModelOut);

		if (null != estimateVariableModelOutList && !estimateVariableModelOutList.isEmpty()) {
			List<Date> xList = estimateVariableModelOutList.stream()
					.map(EstimateVariableModelOut::getRecordTime).collect(Collectors.toList());
			List<Integer> yList = estimateVariableModelOutList.stream()
					.map(EstimateVariableModelOut::getValue).collect(Collectors.toList());

			CoordinateDate coordinateDate = new CoordinateDate();
			coordinateDate.setX(xList);
			coordinateDate.setY(yList);
			estimateReviewBo.setDate(coordinateDate);
		}

		return estimateReviewBo;
	}

	@Override
	public ListRsp<ProbeOriginData> reviewEstimateInstanceOriginData(String instanceId,
																	 String variableId, Page pageBase) {
		// 1、查询对应的评估实例和评估
		EstimateInstance instance = estimateInstanceDao.getByInstanceId(instanceId);

		// 2、评估id+变量id找到主机和采集器的信息
		EstimateVariableLinks estimateVariableLinks = estimateVariableLinksDao
				.getOneLink(instance.getEstimateId(), variableId);

		ProbeOriginPageData ret = probeService.originData(instance.getSceneId(),
				estimateVariableLinks.getHostId(), estimateVariableLinks.getCollectId(),
				instance.getStartTime(), instance.getEndTime(), pageBase);

		ListRsp<ProbeOriginData> rsp = new ListRsp<>();
		rsp.setTotal(ret.getTotal());
		rsp.setList(ret.getData());
		return rsp;
	}

	@Override
	public ListRsp<EstimateInstanceBO> reviewEstimateInstanceResults(String estimateId,
																  Page pageBase) {
		// 1、查询总量
		Integer total = estimateInstanceDao.getCountByEstimateIdAndSave(estimateId,
				EstimateConstant.MANUAL_SAVE, EstimateConstant.ESTIMATE_FINISH);

		// 2、查询对应页数据
		IPage<EstimateInstance> estimateInstanceList = estimateInstanceDao.getByEstimateIdAndSave(
				estimateId, EstimateConstant.MANUAL_SAVE, EstimateConstant.ESTIMATE_FINISH,
				pageBase);

		estimateInstanceList.convert(estimateInstance -> {
			EstimateInstanceBO estimateInstanceBO = new EstimateInstanceBO();
			BeanUtils.copyProperties(estimateInstance, estimateInstanceBO);
			estimateInstanceBO
					.setResultList(JSON.parseArray(estimateInstance.getResult(), NameValueItem.class));
			return estimateInstanceBO;
		});

		return PageUtil.getListResult(estimateInstanceList);
	}

	@Override
	public void calculateAndSave(String instanceId) {
		// 1、找出对应的评估实例，设置状态为计算中
		EstimateInstance estimateInstance = estimateInstanceDao.getByInstanceId(instanceId);
		if (null != estimateInstance) {
			estimateInstance.setStatus(EstimateConstant.ESTIMATE_CALCULATE);
			// 先更新数据库实例的状态
			estimateInstanceDao.update(estimateInstance);
			// 2、计算格式化变量项
			Map<String, NameValueItem> variableResultMap = calculateVariables(estimateInstance);
			// 3、计算指标项
			Map<String, NameValueItem> indexResultMap = calculateIndexes(estimateInstance);
			// 4、计算评估项
			Map<String, NameValueItem> estimateResultMap = calculateEstimateItems(estimateInstance);
			// 5、保存计算到实例中
			estimateInstance.setStatus(EstimateConstant.ESTIMATE_FINISH);
			estimateInstance.setResult(estimateResultToJson(estimateInstance, variableResultMap,
					indexResultMap, estimateResultMap));
			estimateInstanceDao.update(estimateInstance);
		}
	}

	/**
	 * 把实际计算结果转换为json的形式保存，方便查询
	 * */
	private String estimateResultToJson(EstimateInstance instance,
			Map<String, NameValueItem> variableResultMap, Map<String, NameValueItem> indexResultMap,
			Map<String, NameValueItem> estimateResultMap) {
		// 1、查找评估方案
		EstimateInfo estimateInfo = estimateInfoDao.getByEstimateId(instance.getEstimateId());

		// 2、设置指标项
		List<EstimateIndex> estimateIndexList = estimateIndexDao
				.selectByScheme(estimateInfo.getEstimateSchemeId());
		estimateIndexList.forEach(index -> {

			NameValueItem indexItem = indexResultMap.get(index.getEstimateIndexId());
			// 添加关联的变量项
			List<IndexVariableLinks> indexVariableLinksList = indexVariableLinksDao
					.selectBySchemeAndIndex(estimateInfo.getEstimateSchemeId(),
							index.getEstimateIndexId());
			indexVariableLinksList.forEach(link -> {

				NameValueItem variableItem = variableResultMap.get(link.getVariableItemsId());
				indexItem.getLinks().add(variableItem);
			});
		});

		// 3、设置评估项
		List<EstimateItems> estimateItemsList = estimateItemsDao
				.selectByScheme(estimateInfo.getEstimateSchemeId());
		estimateItemsList.forEach(estimate -> {

			NameValueItem estimateItem = estimateResultMap.get(estimate.getEstimateItemsId());
			// 添加关联的指标项
			List<EstimateIndexLinks> estimateIndexLinksList = estimateIndexLinksDao
					.selectBySchemeAndItem(estimateInfo.getEstimateSchemeId(),
							estimate.getEstimateItemsId());
			estimateIndexLinksList.forEach(link -> {

				NameValueItem indexItem = indexResultMap.get(link.getEstimateIndexId());
				estimateItem.getLinks().add(indexItem);
			});
		});

		List<NameValueItem> retList = Lists.newArrayList();
		retList.addAll(estimateResultMap.values());

		String ret = JSON.toJSONString(retList);
		return ret;
	}

	/**
	* 计算实例的所有变量项并保存中间结果
	* */
	private Map<String, NameValueItem> calculateVariables(EstimateInstance instance) {

		Map<String, List<NameValueItem>> hostVariableMap = Maps.newHashMap();

		for (Iterator<EstimateVariableLinks> iterator = estimateVariableLinksDao
				.getByEstimateId(instance.getEstimateId()).iterator(); iterator.hasNext();) {
			EstimateVariableLinks link = iterator.next();
			// 根据变量项id找到变量项，
			EstimateVariable estimateVariable = estimateVariableDao
					.getById(link.getEstimateVariableId());
			Integer variableResult = calculateVariable(instance, link, estimateVariable);

			NameValueItem item = new NameValueItem();
			item.setName(estimateVariable.getName());
			item.setValue(variableResult);

			if (hostVariableMap.containsKey(estimateVariable.getEstimateVariableId())) {
				List<NameValueItem> itemList = hostVariableMap
						.get(estimateVariable.getEstimateVariableId());
				itemList.add(item);
			} else {
				List<NameValueItem> itemList = Lists.newArrayList();
				itemList.add(item);
				hostVariableMap.put(estimateVariable.getEstimateVariableId(), itemList);
			}
		}

		Map<String, NameValueItem> variableOutMap = Maps.newHashMap();
		for (Map.Entry<String, List<NameValueItem>> entry : hostVariableMap.entrySet()) {
			String key = entry.getKey();
			List<NameValueItem> itemList = entry.getValue();
			Double value = itemList.stream().mapToInt(NameValueItem::getValue).average()
					.getAsDouble();
			NameValueItem item = new NameValueItem();
			item.setName(itemList.get(0).getName());
			item.setValue(value.intValue());
			variableOutMap.put(key, item);
		}

		return variableOutMap;
	}

	/**
	* 计算实例的一个变量项并保存中间结果
	* */
	private Integer calculateVariable(EstimateInstance instance, EstimateVariableLinks link,
			EstimateVariable estimateVariable) {
		List<TimeSequenceValue> formalDateList = Lists.newArrayList();
		List<TimeSequenceValue> detectionDateList = Lists.newArrayList();
		Integer variableResult = 0;

		List<EstimateFormatData> estimateFormatDataList = Lists.newArrayList();

		List<EstimateVariableModelOut> estimateVariableModelOutList = Lists.newArrayList();

		// 找到算法
		EstimateAlgorithm formalEstimateAlgorithm = estimateAlgorithmDao
				.selectById(estimateVariable.getFormatAlgorithmId());
		if (null == formalEstimateAlgorithm) {
			return variableResult;
		}

		// 格式化数据
		List<String> formatParams = parseParams(estimateVariable.getFormatParam());
		formalDateList = remotePythonRuntimeService.formatProbeData(instance.getSceneId(),
				link.getHostId(), link.getCollectId(),
				formalEstimateAlgorithm.getAlgorithmContent(), instance.getStartTime(),
				instance.getEndTime(), formatParams);
		// 检测模型数据
		EstimateAlgorithm detectionEstimateAlgorithm = estimateAlgorithmDao
				.selectById(estimateVariable.getDetectionAlgorithmId());
		List<String> detectionParams = Arrays
				.asList(estimateVariable.getDetectionParam().split(","));
		detectionDateList = remotePythonRuntimeService.modelDetection(formalDateList,
				detectionParams, detectionEstimateAlgorithm.getAlgorithmContent());

		// 评分模型数据
		EstimateAlgorithm calculateEstimateAlgorithm = estimateAlgorithmDao
				.selectById(estimateVariable.getMarkAlgorithmId());
		if (null == calculateEstimateAlgorithm) {
			return variableResult;
		}
		List<String> calculateParams = parseParams(estimateVariable.getMarkParam());
		variableResult = remotePythonRuntimeService.calculateEstimateVariable(detectionDateList,
				formalDateList, calculateParams, calculateEstimateAlgorithm.getAlgorithmContent());

		// 把产生的中间数据写入数据库；

		// 保存中间变量
		// 保存格式化数据
		if (null != formalDateList && !formalDateList.isEmpty()) {
			formalDateList.forEach(formalDate -> {
				EstimateFormatData estimateFormatData = new EstimateFormatData();
				estimateFormatData.setEstimateFormatDataId(IDGeneratorUtil.getID());
				estimateFormatData.setSceneId(instance.getSceneId());
				estimateFormatData.setHostId(link.getHostId());
				estimateFormatData.setEstimateVariableId(link.getEstimateVariableId());
				estimateFormatData.setEstimateInstanceId(instance.getEstimateInstanceId());
				estimateFormatData.setValue(formalDate.getValue());
				estimateFormatData.setCollectDate(formalDate.getTime());
				estimateFormatDataList.add(estimateFormatData);
			});
		}
		// 保存检测模型输出；
		if (null != detectionDateList && !detectionDateList.isEmpty()) {
			detectionDateList.forEach(detectionDate -> {
				EstimateVariableModelOut estimateVariableModelOut = new EstimateVariableModelOut();
				estimateVariableModelOut.setVariableModelOutId(IDGeneratorUtil.getID());
				estimateVariableModelOut.setSceneId(instance.getSceneId());
				estimateVariableModelOut.setHostId(link.getHostId());
				estimateVariableModelOut.setVariableId(link.getEstimateVariableId());
				estimateVariableModelOut.setEstimateInstanceId(instance.getEstimateInstanceId());
				estimateVariableModelOut.setRecordTime(detectionDate.getTime());
				estimateVariableModelOut.setValue(detectionDate.getValue());
				estimateVariableModelOutList.add(estimateVariableModelOut);
			});
		}
		// 保存评分模型输出
		EstimateVariableOut estimateVariableOut = new EstimateVariableOut();
		estimateVariableOut.setEstimateVariableOutId(IDGeneratorUtil.getID());
		estimateVariableOut.setSceneId(instance.getSceneId());
		estimateVariableOut.setHostId(link.getHostId());
		estimateVariableOut.setEstimateVariableId(link.getEstimateVariableId());
		estimateVariableOut.setEstimateInstanceId(instance.getEstimateInstanceId());
		estimateVariableOut.setScore(null == variableResult ? 0 : variableResult);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				estimateFormatDataDao.batchAdd(estimateFormatDataList);

				estimateVariableModelOutDao.batchAdd(estimateVariableModelOutList);

				estimateVariableOutDao.insert(estimateVariableOut);
			}
		});
		return variableResult;
	}

	/**
	 * 计算指标项
	 * */

	private Map<String, NameValueItem> calculateIndexes(EstimateInstance instance) {

		List<EstimateIndexOut> estimateIndexOutList = Lists.newArrayList();
		Map<String, NameValueItem> variableOutMap = Maps.newHashMap();
		// 1、评估--评估方案id--指标项
		EstimateInfo estimateInfo = estimateInfoDao.getByEstimateId(instance.getEstimateId());
		List<EstimateIndex> estimateIndexList = estimateIndexDao
				.selectByScheme(estimateInfo.getEstimateSchemeId());
		for (Iterator<EstimateIndex> iterator = estimateIndexList.iterator(); iterator.hasNext();) {
			EstimateIndex estimateIndex = iterator.next();
			EstimateIndexOut estimateIndexOut = calculateIndex(instance, estimateInfo,
					estimateIndex);
			estimateIndexOutList.add(estimateIndexOut);

			NameValueItem item = new NameValueItem();
			item.setName(estimateIndex.getName());
			item.setValue(estimateIndexOut.getScore());
			variableOutMap.put(estimateIndex.getEstimateIndexId(), item);
		}
		// 指标项输出存入数据库;
		if (!estimateIndexOutList.isEmpty()) {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					estimateIndexOutList.forEach(estimateIndexOut -> {
						estimateIndexOutDao.insert(estimateIndexOut);
					});
				}
			});
		}

		return variableOutMap;
	}

	/**
	* 计算实例的一项指标
	* */
	private EstimateIndexOut calculateIndex(EstimateInstance instance, EstimateInfo estimateInfo,
			EstimateIndex estimateIndex) {

		// 查询指标项对应的参数项
		List<IndexVariableLinks> indexVariableLinkList = indexVariableLinksDao
				.selectBySchemeAndIndex(estimateInfo.getEstimateSchemeId(),
						estimateIndex.getEstimateIndexId());
		indexVariableLinkList = indexVariableLinkList.stream()
				.sorted(Comparator.comparing(IndexVariableLinks::getSerialNumber))
				.collect(Collectors.toList());

		// 获取变量项输出
		List<Integer> variables = Lists.newArrayList();
		indexVariableLinkList.forEach(link -> {
			EstimateVariableOut estimateVariableOut = new EstimateVariableOut();
			estimateVariableOut.setEstimateInstanceId(instance.getEstimateInstanceId());
			estimateVariableOut.setEstimateVariableId(link.getVariableItemsId());
			estimateVariableOut.setSceneId(instance.getSceneId());

			List<EstimateVariableOut> estimateVariableOutList = estimateVariableOutDao
					.select(estimateVariableOut);
			Integer variable = estimateVariableOutList.stream()
					.mapToInt(EstimateVariableOut::getScore).sum();
			variable = variable / estimateVariableOutList.size();
			variables.add(variable);
		});
		// 获取用户设置的指标自定义参数
		List<String> indexParams = parseParams(estimateIndex.getParams());
		// 获取算法脚本
		EstimateAlgorithm indexAlgorithm = estimateAlgorithmDao
				.selectById(estimateIndex.getAlgorithmId());
		Integer index = remotePythonRuntimeService.aggregation(variables, indexParams,
				indexAlgorithm.getAlgorithmContent());

		// 返回指标项产出
		EstimateIndexOut estimateIndexOut = new EstimateIndexOut();
		estimateIndexOut.setEstimateIndexOutId(IDGeneratorUtil.getID());
		estimateIndexOut.setSceneId(instance.getSceneId());
		estimateIndexOut.setEstimateInstanceId(instance.getEstimateInstanceId());
		estimateIndexOut.setEstimateIndexId(estimateIndex.getEstimateIndexId());
		estimateIndexOut.setScore(index);
		return estimateIndexOut;
	}

	/**
	* 计算实例的所有评估项
	* */
	private Map<String, NameValueItem> calculateEstimateItems(EstimateInstance instance) {

		List<EstimateItemsOut> estimateItemsOutList = Lists.newArrayList();
		Map<String, NameValueItem> estimateResultMap = Maps.newHashMap();
		// 1、评估--评估方案id--评估项
		EstimateInfo estimateInfo = estimateInfoDao.getByEstimateId(instance.getEstimateId());
		List<EstimateItems> estimateItemsList = estimateItemsDao
				.selectByScheme(estimateInfo.getEstimateSchemeId());
		for (Iterator<EstimateItems> iterator = estimateItemsList.iterator(); iterator.hasNext();) {
			EstimateItems estimateItems = iterator.next();
			EstimateItemsOut estimateItemsOut = calculateEstimateItem(instance, estimateInfo,
					estimateItems);
			estimateItemsOutList.add(estimateItemsOut);

			NameValueItem item = new NameValueItem();
			item.setName(estimateItems.getName());
			item.setValue(estimateItemsOut.getScore());
			estimateResultMap.put(estimateItemsOut.getEstimateItemsId(), item);
		}
		// 指标项输出存入数据库;
		if (!estimateItemsOutList.isEmpty()) {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					estimateItemsOutList.forEach(estimateItemsOut -> {
						estimateItemsOutDao.insert(estimateItemsOut);
					});
				}
			});
		}
		return estimateResultMap;
	}

	/**
	* 计算实例的一个评估项
	* */
	private EstimateItemsOut calculateEstimateItem(EstimateInstance instance,
			EstimateInfo estimateInfo, EstimateItems estimateItems) {

		// 查询评估项对应的指标项
		List<EstimateIndexLinks> estimateIndexLinksList = estimateIndexLinksDao
				.selectBySchemeAndItem(estimateInfo.getEstimateSchemeId(),
						estimateItems.getEstimateItemsId());
		// 指标项排序
		estimateIndexLinksList = estimateIndexLinksList.stream()
				.sorted(Comparator.comparing(EstimateIndexLinks::getSerialNumber))
				.collect(Collectors.toList());

		// 获取变量项输出
		List<Integer> indexes = Lists.newArrayList();
		estimateIndexLinksList.forEach(link -> {
			EstimateIndexOut estimateIndexOut = new EstimateIndexOut();
			estimateIndexOut.setEstimateIndexId(link.getEstimateIndexId());
			estimateIndexOut.setSceneId(instance.getSceneId());
			estimateIndexOut.setEstimateInstanceId(instance.getEstimateInstanceId());

			estimateIndexOut = estimateIndexOutDao.select(estimateIndexOut);

			indexes.add(estimateIndexOut.getScore());
		});
		// 获取用户设置的指标自定义参数
		List<String> estimateItemParams = Arrays
				.<String>asList(estimateItems.getParams().split(","));

		// 获取算法脚本
		EstimateAlgorithm indexAlgorithm = estimateAlgorithmDao
				.selectById(estimateItems.getAlgorithmId());
		Integer estimateItemResult = remotePythonRuntimeService.aggregation(indexes,
				estimateItemParams, indexAlgorithm.getAlgorithmContent());

		// 返回产出
		EstimateItemsOut estimateItemsOut = new EstimateItemsOut();
		estimateItemsOut.setEstimateItemsOutId(IDGeneratorUtil.getID());
		estimateItemsOut.setSceneId(instance.getSceneId());
		estimateItemsOut.setEstimateInstanceId(instance.getEstimateInstanceId());
		estimateItemsOut.setEstimateItemsId(estimateItems.getEstimateItemsId());
		estimateItemsOut.setScore(estimateItemResult);

		return estimateItemsOut;
	}

	/**
	 * 解析算法参数列表
	 *
	 * */
	private List<String> parseParams(String params) {
		if (!Strings.isNullOrEmpty(params)) {
			return Arrays.asList(params.split(","));
		} else {
			return null;
		}
	}

}
