package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.CollectWithStrContext;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemTestReq;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;
import com.cyberpeace.integratation.model.evaluation.*;
import com.cyberpeace.integratation.service.RemotePythonService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class EvalSystemServiceImpl extends ServiceImpl<EvalSystemMapper, EvalSystem> implements EvalSystemService {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalIndexMapper evalIndexMapper;

	@Autowired
	private EvalSystemMapper evalSystemMapper;

	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	@Autowired
	private EvalIndexService evalIndexService;

	@Autowired
	private EvalAggregationMapper evalAggregationMapper;

	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	@Autowired
	private EvalCollectionService evalCollectionService;

	@Autowired
	private RemotePythonService remotePythonService;

	@Autowired
	private EvalIndexRefService evalIndexRefService;

	@Autowired
	private EvalAlgorithmRefService evalAlgorithmRefService;

	@Override
	public Boolean batchDelete(List<String> evalSystemIds) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<String> indexIds = evalIndexMapper.selectIdsByEvalSystemIds(evalSystemIds);

				evalSystemMapper.deleteBatchIds(evalSystemIds);
				if (!indexIds.isEmpty()) {
					evalIndexMapper.deleteBatchIds(indexIds);
				}

			}
		});

		return true;
	}

	@Override
	public IPage<EvalSystem> page(EvalListQuery listQuery) {

		Page<EvalSystem> page = listQuery.toPageParam();

        return evalSystemMapper.listEvalSystems(page, listQuery);
	}

	@Override
	public EvalSystemRsp convertEvalSystemToEvalSystemRsp(EvalSystem evalSystem, Boolean isTest) {
		EvalSystemRsp evalSystemRsp = BeanUtils.copy(evalSystem, EvalSystemRsp.class);

		evalSystemRsp.setEvalSystemId(evalSystem.getResourceId());
		evalSystemRsp.setFirstIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.FIRST.getCode(), isTest));
		evalSystemRsp.setSecondIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.SECOND.getCode(), isTest));
		evalSystemRsp.setThirdIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.THIRD.getCode(), isTest));
		if(null == evalSystemRsp.getSecondIndexes().get(0).getScore()){
			List<IndexContext> thirdIndexes = evalSystemRsp.getThirdIndexes();
			thirdIndexes.forEach(thirdIndex -> thirdIndex.setScore(null));
			evalSystemRsp.setThirdIndexes(thirdIndexes);
		}
		return evalSystemRsp;
	}

	@Override
	public JSONObject testWithStr(EvalSystemTestReq evalSystemTestReq) {
		JSONObject result = new JSONObject();
		Map<EvalIndex,Integer> scoreMap = new HashMap<>();
		result.put("msg",null);
		result.put("scoreMap",null);

		List<String> column = StreamApiUtils.getColumn(evalSystemTestReq.getFifthIndex(), CollectWithStrContext::getEvalIndicatorId);
		List<EvalCollection> collects = evalCollectionMapper.selectByEvalIndicators(column);

		// 根据用过户填入 字符串 计算出采集项得分
		String msg = startCollect(evalSystemTestReq.getFifthIndex(), collects);
		if (null != msg) {
			result.put("msg",msg);
			return result;
		}

		// 根据当前指标体系关联的3级指标项 查询出关联的所有下级聚合项 并通过各自关联的算法及采集项的score的列表做参数进行聚合计算
		String evalSystemId = evalSystemTestReq.getEvalSystemId();
		List<EvalIndex> thirdIndex = evalIndexMapper.selectListBySystemIdAndLevel(evalSystemId, EvalIndex.Level.THIRD.getCode());

		List<EvalAggregation> evalAggregations = evalAggregationMapper.selectIdsByEvalIndicatorIds(column);
		List<String> algorithms = StreamApiUtils.getColumn(evalAggregations, EvalAggregation::getAggregatingAlgorithmId);
		Map<String, EvalAlgorithm> evalAlgorithmMap = evalAlgorithmService.lambdaQuery().in(EvalAlgorithm::getResourceId, algorithms).list().stream().collect(Collectors.toMap(EvalAlgorithm::getResourceId, evalAlgorithmRef -> evalAlgorithmRef));
		Map<String, Integer> collectMap = StreamApiUtils.toMap(evalCollectionMapper.selectByEvalIndicators(column), EvalCollection::getResourceId, EvalCollection::getScore);

		for (EvalAggregation item : evalAggregations) {
			msg = getAggScore(evalAlgorithmMap, collectMap, item);
			if (null != msg) {
				result.put("msg",msg);
				return result;
			}

		}
		// 对查询出的当前体系下的所有聚合项 按指标项分组 供三级计算得分时查询
		Map<String, List<EvalAggregation>> aggMap = StreamApiUtils.groupBy(evalAggregations, EvalAggregation::getEvalIndicatorId);

		List<EvalIndicator> evalIndicators = evalIndicatorMapper.selectIndexes(column);
		Map<String, EvalIndicator> indicatorMap = StreamApiUtils.toMap(evalIndicators, EvalIndicator::getResourceId, s -> s);
		List<String> algs = StreamApiUtils.getColumn(evalIndicators, EvalIndicator::getAggregatingAlgorithmId);
		Map<String, EvalAlgorithm> algMap = evalAlgorithmService.lambdaQuery().in(EvalAlgorithm::getResourceId, algs).list().stream().collect(Collectors.toMap(EvalAlgorithm::getResourceId, evalAlgorithmRef -> evalAlgorithmRef));
		// 计算指标项的得分
		for(EvalIndex index:thirdIndex){
			if(indicatorMap.get(index.getEvalIndicatorId()) == null){
				continue;
			}
			String evalIndicatorId = index.getEvalIndicatorId();
			List<EvalAggregation> aggregations = aggMap.get(evalIndicatorId);
			List<Integer> scoreList = StreamApiUtils.getColumn(aggregations, EvalAggregation::getScore);
			String algorithmId = indicatorMap.get(index.getEvalIndicatorId()).getAggregatingAlgorithmId();
			EvalAlgorithm evalAlgorithm = algMap.get(algorithmId);
			JSONObject json = getScore(index, evalAlgorithm, scoreList);
			msg = json.getString("msg");
			if(null != msg){
				result.put("msg",msg);
				return result;
			}
			Integer score = json.getInteger("score");
			scoreMap.put(index,score);
		}
		// 待确认是否需要新调接口
		result.put("scoreMap",scoreMap);
		return result;
	}

	private String startCollect(List<CollectWithStrContext> fifthIndex, List<EvalCollection> collects) {
		List<String> usingAlgorithmIds = Lists.newArrayList();
		List<String> collect = fifthIndex.stream().map(CollectWithStrContext::getResourceId).collect(Collectors.toList());
		List<EvalCollection> evalCollects = evalCollectionMapper.selectByIds(collect);
		Map<String, EvalCollection> collectMap = evalCollects.stream().collect(Collectors.toMap(EvalCollection::getResourceId, s -> s));
		fifthIndex.forEach(item -> BeanUtils.copyProperties(collectMap.get(item.getResourceId()), item));

		// 获取采集项所有的算法 并存成map
		fifthIndex.forEach(evalVariableRef -> {
			usingAlgorithmIds.add(evalVariableRef.getFormatAlgorithmId());
			usingAlgorithmIds.add(evalVariableRef.getDetectionAlgorithmId());
			usingAlgorithmIds.add(evalVariableRef.getScoreAlgorithmId());
		});

		Map<String, EvalCollection> collectionMap = StreamApiUtils.toMap(collects, EvalCollection::getResourceId, s -> s);
		Map<String, EvalAlgorithm> evalAlgorithmRefMap = evalAlgorithmService.lambdaQuery().in(EvalAlgorithm::getResourceId, usingAlgorithmIds).list().stream().collect(Collectors.toMap(EvalAlgorithm::getResourceId, evalAlgorithmRef -> evalAlgorithmRef));
		for(CollectWithStrContext evalCollect:fifthIndex) {
			EvalCollection evalCollection = collectionMap.get(evalCollect.getResourceId());
			// 格式化算法 对字符串用算法匹配出 value
			String msg = formatStr(evalAlgorithmRefMap, evalCollection, evalCollect.getStr());
			if (null != msg) {
				return msg;
			}
			// 模型匹配算法 计算格式化算法结果 与偏移量的对比值 结果为0，1
			msg = matchStr(evalAlgorithmRefMap, evalCollection);
			if (null != msg) {
				return msg;
			}
			// 评分算法 根据增长或衰减值比例计算得分
			msg = markStr(evalAlgorithmRefMap, evalCollection);
			if (null != msg) {
				return msg;
			}
		}
		return null;
	}

	private String markStr(Map<String, EvalAlgorithm> evalAlgorithmRefMap, EvalCollection evalCollect) {
		MarkData data = new MarkData();
		data.setBaseTime(System.currentTimeMillis());
		data.setData(Lists.newArrayList());
		MarkItemData itemData = new MarkItemData();
		itemData.setT(data.getBaseTime());
		itemData.setV(String.valueOf(evalCollect.getScore()));
		itemData.setM(EvalVariableRefProbeData.Status.MATCHED.getCode());
		data.getData().add(itemData);
/*
		List<Serializable> otherArguments = evalAlgorithmRefService.buildOtherArguments(evalCollect.getScoreAlgorithmParams());

		Integer score;
		String scoreAlgorithmId = evalCollect.getScoreAlgorithmId();
		EvalAlgorithm evalAlgorithm = evalAlgorithmRefMap.get(scoreAlgorithmId);
		try {
			score = remotePythonService.markPerform(evalAlgorithm.getResourceId(), evalAlgorithm.getCode(), data, otherArguments);
		} catch (RemoteServiceException e) {
			if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
				return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MARK_ALGORITHM_RESULT_IS_INVALID), evalCollect.getName());
			}
			return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_MARK_EXECUTE_FAILED), evalCollect.getName());
		}

		if (score > 100 || 0 > score) {
			return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MARK_ALGORITHM_RESULT_IS_INVALID), evalCollect.getName());
		}*/

		EvalCollection evalCollection = evalCollectionService.getById(evalCollect.getResourceId());
		evalCollection.setScore(evalCollect.getScore());
		evalCollection.setProgress(EvalVariableRef.Progress.END.getCode());
		evalCollectionService.updateById(evalCollection);

		return null;
	}

	private String matchStr(Map<String, EvalAlgorithm> evalAlgorithmRefMap, EvalCollection evalCollect) {
		MatchData data = new MatchData();
		data.setBaseTime(System.currentTimeMillis());
		data.setData(Lists.newArrayList());
		MatchItemData itemData = new MatchItemData();
		itemData.setT(data.getBaseTime());
		itemData.setV(String.valueOf(evalCollect.getScore()));
		data.getData().add(itemData);


		// 运行匹配算法
		/*List<Integer> matchReturn;
		EvalAlgorithm evalAlgorithm = evalAlgorithmRefMap.get(evalCollect.getDetectionAlgorithmId());
		List<Serializable> otherArguments = evalAlgorithmRefService.buildOtherArguments(evalCollect.getDetectionAlgorithmParams());
		try {
			matchReturn = remotePythonService.matchPerform(evalAlgorithm.getResourceId(), evalAlgorithm.getCode(), data, otherArguments);
		} catch (RemoteServiceException e) {
			if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
				return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.MATCH_ALGORITHM_RESULT_IS_INVALID), evalAlgorithm.getName());
			}
			return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_MATCH_EXECUTE_FAILED), evalAlgorithm.getName());
		}*/


		evalCollect.setProgress(EvalVariableRef.Progress.MARKING.getCode());
//		evalCollect.setScore(matchReturn.get(0));
		evalCollectionService.updateById(evalCollect);
		return null;
	}

	private String formatStr(Map<String, EvalAlgorithm> evalAlgorithmRefMap, EvalCollection evalCollect, String str) {
		List<String> strings = new ArrayList<>();
		strings.add(str);
		FormatData data = new FormatData();
		data.setData(strings);
		// 运行匹配算法

		/*EvalAlgorithm evalAlgorithm = evalAlgorithmRefMap.get(evalCollect.getFormatAlgorithmId());
		List<Serializable> otherArguments = evalAlgorithmRefService.buildOtherArguments(evalCollect.getFormatAlgorithmParams());
		List<String> formatReturn;
		try {
			formatReturn = remotePythonService.formatPerform(evalAlgorithm.getResourceId(), evalAlgorithm.getCode(), data, otherArguments);
		} catch (RemoteServiceException e) {
			if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
				return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.FORMAT_ALGORITHM_RESULT_IS_INVALID), evalAlgorithm.getName());
			}
			return String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.VARIABLE_FORMAT_EXECUTE_FAILED), evalAlgorithm.getName());
		}*/
		JSONObject jsonObject = JSON.parseObject(str);
		evalCollect.setScore(Integer.valueOf(jsonObject.values().stream().findFirst().get().toString()));
		evalCollectionService.updateById(evalCollect);
		return null;
	}

	private String getAggScore(Map<String, EvalAlgorithm> evalAlgorithmMap, Map<String, Integer> collectMap, EvalAggregation item) {
		String msg = "";
		EvalAlgorithm evalAlgorithm = evalAlgorithmMap.get(item.getAggregatingAlgorithmId());
		List<Integer> scoreList = Lists.newArrayList();

		String collectionIds = item.getCollectionIds();
		List<String> list = JsonUtil.stringToArray(collectionIds).toJavaList(String.class);
		list.forEach(currentId ->{
			Integer score = collectMap.get(currentId);
			scoreList.add(score);
		});
		if (scoreList.contains(null)) {
			return null;
		}
		List<Serializable> otherArguments = evalAlgorithmRefService
				.buildOtherArguments(item.getAggregatingAlgorithmParams());
		Integer score;
		try {
			if (evalAlgorithm.getMode().equals(EvalAlgorithm.Mode.FORMAT_ALGORITHM.getCode())) {
				score = evalIndexRefService.indexAhpPerform(item.getMatrixData(), scoreList);
			} else {
				score = remotePythonService.indexPerform(evalAlgorithm.getResourceId(), evalAlgorithm.getCode(),
						scoreList, otherArguments);
			}
		} catch (RemoteServiceException e) {
			if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
				msg = String.format(
						BusinessException.getMessageByLanguage(
								CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
						evalIndexRefService.getLevelText(4), item.getName());
				return msg;
			} else {
				msg = String.format(
						BusinessException
								.getMessageByLanguage(CrBusinessErrorCode.INDEX_EXECUTE_FAILED),
						evalIndexRefService.getLevelText(4), item.getName());
				return msg;
			}
		}

		if (score > 100 || 0 > score) {
			msg = String.format(
					BusinessException.getMessageByLanguage(
							CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
					evalIndexRefService.getLevelText(4), item.getName());
			return msg;
		}
		item.setScore(score);
		evalAggregationMapper.updateById(item);
		return null;
	}

	private JSONObject getScore(EvalIndex item, EvalAlgorithm algorithm, List<Integer> scoreList) {
		// 次级全部执行失败情况下（scoreList为空），继续向下执行
		// 正常情况下, scoreList应均为0~100整数，包含`null`为专家评分下未进行计算变量项(五级指标)，无法进行下一步聚合计算，跳过
		JSONObject res = new JSONObject();
		res.put("score", null);
		res.put("msg", null);
		if (scoreList.contains(null)) {
			return res;
		}

		List<Serializable> otherArguments = evalAlgorithmRefService
				.buildOtherArguments(item.getAggregatingAlgorithmParams());
		Integer score;
		String msg;
		try {
			String algorithmId = algorithm.getResourceId();
			EvalAlgorithm evalAlgorithm = evalAlgorithmService.getById(algorithmId);
			if (evalAlgorithm.getMode().equals(EvalAlgorithm.Mode.FORMAT_ALGORITHM.getCode())) {
				score = evalIndexRefService.indexAhpPerform(item.getMatrixData(), scoreList);
			} else {
				score = remotePythonService.indexPerform(algorithmId, algorithm.getCode(),
						scoreList, otherArguments);
			}
		} catch (RemoteServiceException e) {
			if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
				msg = String.format(
						BusinessException.getMessageByLanguage(
								CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
						evalIndexRefService.getLevelText(item.getLevel()), item.getName());
			} else {
				msg = String.format(
						BusinessException
								.getMessageByLanguage(CrBusinessErrorCode.INDEX_EXECUTE_FAILED),
						evalIndexRefService.getLevelText(item.getLevel()), item.getName());
			}
			res.put("msg", msg);
			return res;
		}

		if (score > 100 || 0 > score) {
			msg = String.format(
					BusinessException.getMessageByLanguage(
							CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
					evalIndexRefService.getLevelText(item.getLevel()), item.getName());
			res.put("msg", msg);
			return res;
		}
		res.put("score", score);
		return res;
	}
}
