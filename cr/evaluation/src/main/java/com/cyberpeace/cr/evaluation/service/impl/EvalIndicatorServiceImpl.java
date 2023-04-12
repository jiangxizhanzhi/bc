package com.cyberpeace.cr.evaluation.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.service.EvalAggregationService;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.bo.EvalAggregationLoadBo;
import com.cyberpeace.cr.evaluation.service.bo.EvalAlgorithmLoadBo;
import com.cyberpeace.cr.evaluation.service.bo.EvalCollectionLoadBo;
import com.cyberpeace.cr.evaluation.service.bo.EvalIndicatorLoadBo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAggregationMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndicatorMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@Service
public class EvalIndicatorServiceImpl extends ServiceImpl<EvalIndicatorMapper, EvalIndicator> implements EvalIndicatorService {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	@Autowired
	private EvalAggregationMapper evalAggregationMapper;

	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	@Autowired
	private EvalAggregationService evalAggregationService;

	@Autowired
	private EvalCollectionService evalCollectionService;

	@Override
	public void saveBatchContext(EvalIndicatorReq evalIndicatorReq, String userId,
			List<String> collectionIds) {

		List<EvalIndicator> evalIndicatorList = evalIndicatorReq.getEvalIndicators().stream()
				.map(evalIndicatorContext -> {
					return convertEvalIndicatorReqToEvalIndicator(evalIndicatorContext,
							evalIndicatorReq, userId, collectionIds);
				}).collect(Collectors.toList());

		this.saveBatch(evalIndicatorList);
	}

	private EvalIndicator convertEvalIndicatorReqToEvalIndicator(
			EvalIndicatorContext evalIndicatorContext, EvalIndicatorReq evalIndicatorReq,
			String userId, List<String> collectionIds) {
		EvalIndicator evalIndicator = BeanUtils.copy(evalIndicatorContext, EvalIndicator.class);

		evalIndicator.setResourceId(evalIndicatorContext.getEvalIndicatorId());
		evalIndicator.setAggregationIds(
				JsonUtil.arrayToString(evalIndicatorContext.getAggregationIds()));
		evalIndicator.setCollectionIds(JsonUtil.arrayToString(collectionIds));
		evalIndicator.setCreateUserId(userId);
		evalIndicator.setDescription(evalIndicatorReq.getDescription());
		evalIndicator.setDatatype(evalIndicatorReq.getDatatype());
		evalIndicator.setAttribute(evalIndicatorReq.getAttribute());
		evalIndicator.setAttributeValue(evalIndicatorReq.getAttributeValue());
		evalIndicator.setConnlist(evalIndicatorReq.getConnlist());

		if (evalIndicatorReq.getAttribute().equals(EvalIndexRef.DataAttr.COST.getCode())
				|| evalIndicatorReq.getAttribute()
						.equals(EvalIndexRef.DataAttr.BENEFIT.getCode())) {
			evalIndicator.setAttributeValue(null);
		}

		return evalIndicator;
	}

	@Override
	public Boolean batchDelete(List<String> evalIndicatorIds) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				deleteAggregationAndCollection(evalIndicatorIds);
				evalIndicatorMapper.deleteBatchIds(evalIndicatorIds);

			}
		});

		return true;
	}

	@Override
	public void deleteAggregationAndCollection(List<String> evalIndicatorIds) {
		List<String> evalAggregationIds = StreamApiUtils.map(
				evalAggregationMapper.selectIdsByEvalIndicatorIds(evalIndicatorIds),
				evalAggregation -> evalAggregation.getResourceId());
		List<String> evalCollectionIds = StreamApiUtils.map(
				evalCollectionMapper.selectIdsByEvalIndicatorIds(evalIndicatorIds),
				evalCollectionContext -> evalCollectionContext.getResourceId());

		if (evalAggregationIds.size() > 0) {
			evalAggregationMapper.deleteBatchIds(evalAggregationIds);
		}
		if (evalCollectionIds.size() > 0) {
			evalCollectionMapper.deleteBatchIds(evalCollectionIds);
		}
	}

	@Override
	public void updateBatchContext(EvalIndicatorReq evalIndicatorReq,
			List<EvalIndicatorContext> evalIndicators, String userId, List<String> collectionIds) {
		List<EvalIndicator> evalIndicatorList = evalIndicators.stream()
				.map(evalIndicatorContext -> {
					return convertEvalIndicatorReqToEvalIndicator(evalIndicatorContext,
							evalIndicatorReq, userId, collectionIds);
				}).collect(Collectors.toList());

		this.updateBatchById(evalIndicatorList);
	}

	@Override
	public IPage<EvalIndicator> page(EvalListQuery listQuery) {
		Page page = listQuery.toPageParam();

		return evalIndicatorMapper.listEvalIndicator(page, listQuery);
	}

	@Override
	public Integer selectIndexUsedNum(List<String> ids) {
		return evalIndicatorMapper.selectIndexUsedNum(ids);
	}

	@Override
	public Boolean loadEvalIndicator(MultipartFile file) {
		List<EvalIndicatorLoadBo> evalIndicatorLoadBoList;
		try {
			evalIndicatorLoadBoList = JsonUtil.toJsonObjectArray(new String(file.getBytes(), StandardCharsets.UTF_8), EvalIndicatorLoadBo.class);
		} catch (IOException e) {
			throw new BusinessException();
		} catch (Exception e) {
			throw new BusinessException(CrBusinessErrorCode.FILE_FORMAT_ERROR);
		}
		if (!lambdaQuery()
				.in(EvalIndicator::getName,
						evalIndicatorLoadBoList.stream().map(EvalIndicatorLoadBo::getName).collect(Collectors.toList()))
				.list().isEmpty()) {
			throw new BusinessException(CrBusinessErrorCode.INDEX_LOAD_NAME_EXISTS);
		}

		Map<String, EvalAlgorithm> saveEvalAlgorithmMap = Maps.newHashMap();
		List<EvalIndicator> saveEvalIndicatorList = Lists.newArrayList();
		List<EvalAggregation> saveEvalAggregationList = Lists.newArrayList();
		List<EvalCollection> saveEvalCollectionList = Lists.newArrayList();

		evalIndicatorLoadBoList.forEach(evalIndicatorLoadBo -> {
			EvalIndicator evalIndicator = BeanUtils.copy(evalIndicatorLoadBo, EvalIndicator.class);
			// 拼接connlist、aggregationIds、collectionIds
			List<JSONObject> connlist = Lists.newArrayList();
			List<String> aggregationIds = Lists.newArrayList();
			List<String> collectionIds = Lists.newArrayList();

			evalIndicator.setAggregatingAlgorithmId(getAlgorithmByName(evalIndicatorLoadBo.getAggregatingAlgorithm(), saveEvalAlgorithmMap).getResourceId());
			evalIndicator.setResourceId(UUID.randomUUID().toString());

			JSONObject indicatorConnlist = new JSONObject();
			indicatorConnlist.put("sourceNodeId", evalIndicator.getResourceId());
			indicatorConnlist.put("targetNodeId", evalIndicator.getAggregatingAlgorithmId() + "-" + evalIndicator.getName());
			connlist.add(indicatorConnlist);

			evalIndicatorLoadBo.getAggregations().forEach(evalAggregationLoadBo -> {
				EvalAggregation evalAggregation = BeanUtils.copy(evalAggregationLoadBo, EvalAggregation.class);
				evalAggregation.setAggregatingAlgorithmId(getAlgorithmByName(evalAggregationLoadBo.getAggregatingAlgorithm(), saveEvalAlgorithmMap).getResourceId());
				evalAggregation.setResourceId(UUID.randomUUID().toString());
				evalAggregation.setEvalIndicatorId(evalIndicator.getResourceId());
				aggregationIds.add(evalAggregation.getResourceId());
				List<String> evalAggregationCollectionIds = Lists.newArrayList();

				JSONObject aggregationConnlist = new JSONObject();
				aggregationConnlist.put("sourceNodeId", indicatorConnlist.get("targetNodeId"));
				aggregationConnlist.put("targetNodeId", evalAggregation.getResourceId());
				connlist.add(aggregationConnlist);

				JSONObject aggregationConnlist2 = new JSONObject();
				aggregationConnlist2.put("sourceNodeId", aggregationConnlist.get("targetNodeId"));
				aggregationConnlist2.put("targetNodeId", evalAggregation.getAggregatingAlgorithmId() + "-" + evalAggregation.getName() + "-" + "2");
				connlist.add(aggregationConnlist2);

				evalAggregationLoadBo.getCollections().forEach(evalCollectionLoadBo -> {
					EvalCollection evalCollection = BeanUtils.copy(evalCollectionLoadBo, EvalCollection.class);
					evalCollection.setFormatAlgorithmId(getAlgorithmByName(evalCollectionLoadBo.getFormatAlgorithm(), saveEvalAlgorithmMap).getResourceId());
					evalCollection.setDetectionAlgorithmId(getAlgorithmByName(evalCollectionLoadBo.getDetectionAlgorithm(), saveEvalAlgorithmMap).getResourceId());
					evalCollection.setScoreAlgorithmId(getAlgorithmByName(evalCollectionLoadBo.getScoreAlgorithm(), saveEvalAlgorithmMap).getResourceId());
					evalCollection.setResourceId(UUID.randomUUID().toString());
					evalCollection.setEvalIndicatorId(evalIndicator.getResourceId());

					evalAggregationCollectionIds.add(evalCollection.getResourceId());
					collectionIds.add(evalCollection.getResourceId());
					JSONObject collectionConnlist = new JSONObject();
					collectionConnlist.put("sourceNodeId", aggregationConnlist2.get("targetNodeId"));
					collectionConnlist.put("targetNodeId", evalCollection.getResourceId());
					connlist.add(collectionConnlist);

					saveEvalCollectionList.add(evalCollection);
				});
				evalAggregation.setCollectionIds(JsonUtil.toJsonString(evalAggregationCollectionIds));
				saveEvalAggregationList.add(evalAggregation);
			});

			evalIndicator.setConnlist(JsonUtil.toJsonString(connlist));
			evalIndicator.setAggregationIds(JsonUtil.toJsonString(aggregationIds));
			evalIndicator.setCollectionIds(JsonUtil.toJsonString(collectionIds));
			evalIndicator.setCreateUserId(UserInfoContext.getUserContext().getUserId());
			saveEvalIndicatorList.add(evalIndicator);
		});

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				saveBatch(saveEvalIndicatorList);
				evalAggregationService.saveBatch(saveEvalAggregationList);
				evalCollectionService.saveBatch(saveEvalCollectionList);
				evalAlgorithmService.saveBatch(saveEvalAlgorithmMap.values());
			}
		});

		return Boolean.TRUE;
	}

	private EvalAlgorithm getAlgorithmByName(EvalAlgorithmLoadBo evalAlgorithmLoadBo, Map<String, EvalAlgorithm> saveEvalAlgorithmMap) {
		EvalAlgorithm evalAlgorithm = evalAlgorithmService.lambdaQuery().eq(EvalAlgorithm::getName, evalAlgorithmLoadBo.getName()).one();
		if (evalAlgorithm == null) {
			if (saveEvalAlgorithmMap.containsKey(evalAlgorithmLoadBo.getName())) {
				return saveEvalAlgorithmMap.get(evalAlgorithmLoadBo.getName());
			}
			evalAlgorithm = BeanUtils.copy(evalAlgorithmLoadBo, EvalAlgorithm.class);
			evalAlgorithm.setResourceId(IDGeneratorUtil.getID());
			evalAlgorithm.setCreateUserId(UserInfoContext.getUserContext().getUserId());
			saveEvalAlgorithmMap.put(evalAlgorithm.getName(), evalAlgorithm);
		}
		return evalAlgorithm;
	}

	@Override
	public String dumpEvalIndicator(List<String> resourceIds) {
		List<EvalIndicator> evalIndicatorList = lambdaQuery().in(EvalIndicator::getResourceId, resourceIds).list();

		if (evalIndicatorList.isEmpty()) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		Set<String> algorithmIds = Sets.newHashSet();

		evalIndicatorList.forEach(evalIndicator -> {
			algorithmIds.add(evalIndicator.getAggregatingAlgorithmId());
		});

		Map<String, EvalAggregation> evalAggregationMap = evalAggregationMapper.selectIdsByEvalIndicatorIds(resourceIds)
				.stream().collect(Collectors.toMap(EvalAggregation::getResourceId, evalAggregation -> evalAggregation));
		evalAggregationMap.values().forEach(evalAggregation -> {
			algorithmIds.add(evalAggregation.getAggregatingAlgorithmId());
		});

		Map<String, EvalCollection> evalCollectionMap = evalCollectionMapper.selectIdsByEvalIndicatorIds(resourceIds)
				.stream().collect(Collectors.toMap(EvalCollection::getResourceId, evalCollection -> evalCollection));
		evalCollectionMap.values().forEach(evalCollection -> {
			algorithmIds.add(evalCollection.getFormatAlgorithmId());
			algorithmIds.add(evalCollection.getDetectionAlgorithmId());
			algorithmIds.add(evalCollection.getScoreAlgorithmId());
		});

		Map<String, EvalAlgorithm> evalAlgorithmMap = evalAlgorithmService.getBaseMapper().selectBatchIds(algorithmIds)
				.stream().collect(Collectors.toMap(EvalAlgorithm::getResourceId, evalAlgorithm -> evalAlgorithm));

		List<EvalIndicatorLoadBo> result = Lists.newArrayList();
		evalIndicatorList.forEach(evalIndicator -> {
			EvalIndicatorLoadBo evalIndicatorLoadBo = BeanUtils.copy(evalIndicator, EvalIndicatorLoadBo.class);
			evalIndicatorLoadBo.setAggregatingAlgorithm(
					BeanUtils.copy(evalAlgorithmMap.get(evalIndicator.getAggregatingAlgorithmId()), EvalAlgorithmLoadBo.class)
			);

			evalIndicatorLoadBo.setAggregations(JsonUtil.toJsonObjectArray(evalIndicator.getAggregationIds(), String.class).stream().map(aggregationId -> {
				EvalAggregation evalAggregation = evalAggregationMap.get(aggregationId);
				EvalAggregationLoadBo evalAggregationLoadBo = BeanUtils.copy(evalAggregation, EvalAggregationLoadBo.class);
				evalAggregationLoadBo.setAggregatingAlgorithm(
						BeanUtils.copy(evalAlgorithmMap.get(evalAggregation.getAggregatingAlgorithmId()), EvalAlgorithmLoadBo.class)
				);

				evalAggregationLoadBo.setCollections(JsonUtil.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class).stream().map(collectionId -> {
					EvalCollection evalCollection = evalCollectionMap.get(collectionId);

					EvalCollectionLoadBo evalCollectionLoadBo = BeanUtils.copy(evalCollection, EvalCollectionLoadBo.class);
					evalCollectionLoadBo.setFormatAlgorithm(BeanUtils.copy(evalAlgorithmMap.get(evalCollection.getFormatAlgorithmId()), EvalAlgorithmLoadBo.class));
					evalCollectionLoadBo.setDetectionAlgorithm(BeanUtils.copy(evalAlgorithmMap.get(evalCollection.getDetectionAlgorithmId()), EvalAlgorithmLoadBo.class));
					evalCollectionLoadBo.setScoreAlgorithm(BeanUtils.copy(evalAlgorithmMap.get(evalCollection.getScoreAlgorithmId()), EvalAlgorithmLoadBo.class));
					return evalCollectionLoadBo;
				}).collect(Collectors.toList()));

				return evalAggregationLoadBo;
			}).collect(Collectors.toList()));

			result.add(evalIndicatorLoadBo);
		});

		return JsonUtil.toJsonString(result);
	}

}
