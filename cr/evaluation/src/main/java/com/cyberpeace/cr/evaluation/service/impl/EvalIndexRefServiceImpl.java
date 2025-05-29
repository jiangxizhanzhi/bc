package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmRefService;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import com.cyberpeace.cr.evaluation.utils.AHPComputeWeight;
import com.cyberpeace.cr.evaluation.utils.MatrixData;
import com.cyberpeace.integratation.service.RemotePythonService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 评估指数裁判Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个Spring Boot服务类 `EvalIndexRefServiceImpl` 的实现，主要负责处理评估指数相关的逻辑。以下是对代码的主要功能和结构的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.evaluation.service.impl` 
 * - **类名**：`EvalIndexRefServiceImpl` 
 * - **继承关系**：继承自 `ServiceImpl<EvalIndexRefMapper, EvalIndexRef>`，实现了 `EvalIndexRefService` 接口。 
 * ### 主要成员变量 
 * - **远程蟒服务 (`remotePythonService`)**：用于调用远程Python服务。 
 * - **事务模板 (`transactionTemplate`)**：用于管理数据库事务。 
 * - **评估变量裁判服务 (`evalVariableRefService`)** 和 **评估算法裁判服务 (`evalAlgorithmRefService`)**：分别用于处理评估变量和算法的相关逻辑。 
 * - **评估算法映射器 (`evalAlgorithmMapper`)** 和 **评估变量映射器 (`evalVariableRefMapper`)**：用于与数据库交互。 
 * - **中文数字映射 (`int2cn`)**：将整数转换为中文字符。 
 * - **周期图映射 (`CYCLE_MAP`)**：用于特定类型的数值转换。 
 * ### 主要方法 
 * 1. **`startIndex` 方法** 
 * - 功能：根据传入的评估指数、算法、索引引用图等参数，启动评估过程。 
 * - 实现细节： 
 * - 根据 `level` 判断是处理变量还是指数。 
 * - 获取相关得分，并通过AHP方法或远程Python服务计算最终得分。 
 * - 处理异常情况并记录日志。 
 * 2. **`selectVariables` 方法** 
 * - 功能：根据给定的指数ID查询相关变量。 
 * - 实现细节： 
 * - 根据输入ID判断是处理指数还是变量。 
 * - 使用递归方法收集所有相关变量ID。 
 * - 返回排序后的变量列表。 
 * 3. **`thirdSpecialist` 方法** 
 * - 功能：根据数据类型和属性值进行特定的转换。 
 * - 实现细节： 
 * - 处理百分比、周期和整数三种类型的数据。 
 * - 根据不同属性（固定型和偏离型）计算最终值。 
 * 4. **`systemEvalVariableIds` 方法** 
 * - 功能：获取系统评估变量ID列表。 
 * - 实现细节： 
 * - 根据记录ID查询特定层级的指数。 
 * - 收集并返回这些指数下的变量ID。 
 * 5. **`resultIndex` 方法** 
 * - 功能：获取评估指数结果。 
 * - 实现细节： 
 * - 根据指数ID获取相关数据。 
 * - 根据层级构建子节点信息。 
 * 6. **`collectionIndexes` 方法** 
 * - 功能：递归地收集索引及其子节点。 
 * - 实现细节： 
 * - 根据深度和当前节点信息，递归收集子节点信息。 
 * ### 总结 
 * - **核心功能**：该类主要负责处理评估指数的启动、查询、转换和结果收集等逻辑。 
 * - **技术栈**：使用了MyBatis Plus进行数据库操作，Spring框架进行依赖注入和事务管理。 
 * - **设计模式**：递归方法和依赖注入是主要的设计模式。 
 * 这段代码展示了复杂的业务逻辑处理，包括多种数据类型处理、递归查询和异常处理等。 
**/
@Service
@Slf4j
public class EvalIndexRefServiceImpl extends ServiceImpl<EvalIndexRefMapper, EvalIndexRef> implements EvalIndexRefService {

	/**
	 * 远程蟒Service
	 */
	@Autowired
	private RemotePythonService remotePythonService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 评估变量裁判Service
	 */
	@Autowired
	private EvalVariableRefService evalVariableRefService;

	/**
	 * 评估算法裁判Service
	 */
	@Autowired
	private EvalAlgorithmRefService evalAlgorithmRefService;

	/**
	 * 评估算法Mapper
	 */
	@Autowired
	private EvalAlgorithmMapper evalAlgorithmMapper;

	/**
	 * 评估变量裁判Mapper
	 */
	@Autowired
	private EvalVariableRefMapper evalVariableRefMapper;

	/**
	 * int2cn
	 */
	private final Map<Integer, String> int2cn = new HashMap<Integer, String>() {

		{
			put(1, "一");
			put(2, "二");
			put(3, "三");
			put(4, "四");
		}
	};

	/**
	 * 获取水平发短信
	 *
	 * @param level 水平
	 * @return {@code String }
	 */
	private String getLevelText(Integer level) {
		if (CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN)) {
			return Integer.toString(level);
		}
		return int2cn.get(level);
	}

	/**
	 * 索引 AHP 执行
	 *
	 * @param matrixData 矩阵数据
	 * @param data       数据
	 * @return {@code Integer }
	 */
	private Integer indexAhpPerform(String matrixData, List<Integer> data) {
		int res = 0;
		JSONArray objects = JSONArray.parseArray(matrixData);
		if (objects.size() != data.size()) {
			throw new RemoteServiceException(ErrorCode.UNFIT_MATRIX_DATA);
		}
		if (objects.size() == 1) {
			return data.get(0);
		}
		double[][] formatMatrixData = MatrixData.getFormatMatrixData(matrixData);
		int N = objects.size();
		double[] weight = new double[N];
		AHPComputeWeight instance = AHPComputeWeight.getInstance();
		instance.weight(formatMatrixData, weight, N);
		for (int i = 0; i < N; i++) {
			res += weight[i] * data.get(i);
		}
		return res;
	}

	/**
	 * 起始索引
	 *
	 * @param evalIndexRef       eval 索引 ref
	 * @param evalAlgorithmRef   eval 算法 ref
	 * @param evalIndexRefMap    EVAL 索引参考图
	 * @param evalVariableRefMap eval 变量 ref map
	 * @param evalRecord         EVAL 记录
	 */
	@Override
	public void startIndex(EvalIndexRef evalIndexRef, EvalAlgorithmRef evalAlgorithmRef,
			Map<String, EvalIndexRef> evalIndexRefMap,
			Map<String, EvalVariableRef> evalVariableRefMap, EvalRecord evalRecord) {
		String msg = "";
		List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
		List<Integer> scoreList = Lists.newArrayList();

		// 仅获取正常运行结束的指标/变量项分值，执行失败项目（progress为EXCEPTION）在下一级运算中忽略
		if (evalIndexRef.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
			for (String nextId : nextIds) {
				EvalVariableRef evalVariableRef = evalVariableRefMap.get(nextId);
				if (!evalVariableRef.getProgress()
						.equals(EvalVariableRef.Progress.EXCEPTION.getCode())) {
					scoreList.add(evalVariableRef.getScore());
				}
			}
		} else {
			for (String nextId : nextIds) {
				EvalIndexRef evalIndexRef1 = evalIndexRefMap.get(nextId);
				if (!evalIndexRef1.getProgress()
						.equals(EvalIndexRef.Progress.EXCEPTION.getCode())) {
					scoreList.add(evalIndexRef1.getScore());
				}
			}
		}
		// 次级全部执行失败情况下（scoreList为空），继续向下执行
		// 正常情况下, scoreList应均为0~100整数，包含`null`为专家评分下未进行计算变量项(五级指标)，无法进行下一步聚合计算，跳过
		if (scoreList.contains(null)) {
			return;
		}
		List<Serializable> otherArguments = evalAlgorithmRefService
				.buildOtherArguments(evalIndexRef.getAlgorithmParams());

		EvalLog.debug(evalRecord, evalIndexRef,
				String.format("code: %s", evalAlgorithmRef.getCode()));
		EvalLog.debug(evalRecord, evalIndexRef,
				String.format("other arguments: %s", otherArguments));
		EvalLog.debug(evalRecord, evalIndexRef,
				String.format("data argument: %s", JsonUtil.toJsonString(scoreList)));

		Integer score;
		try {
			String algorithmId = evalAlgorithmRef.getResourceId();
			EvalAlgorithmRef algorithmRef = evalAlgorithmRefService.getById(algorithmId);
			if (algorithmRef.getMode().equals(EvalAlgorithm.Mode.FORMAT_ALGORITHM.getCode())) {
				score = indexAhpPerform(evalIndexRef.getMatrixData(), scoreList);
			} else {
				score = remotePythonService.indexPerform(algorithmId, evalAlgorithmRef.getCode(),
						scoreList, otherArguments);
			}
			EvalLog.debug(evalRecord, evalIndexRef,
					String.format("system evaluation type index result: %s.", score));
		} catch (RemoteServiceException e) {
			score = 0;
			if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
				msg = String.format(
						BusinessException.getMessageByLanguage(
								CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
						this.getLevelText(evalIndexRef.getLevel()), evalIndexRef.getName());
			} else {
				msg = String.format(
						BusinessException
								.getMessageByLanguage(CrBusinessErrorCode.INDEX_EXECUTE_FAILED),
						this.getLevelText(evalIndexRef.getLevel()), evalIndexRef.getName());
			}
			evalRecord.setMessage(evalRecord.getMessage() + msg + "\n");
			EvalLog.error(evalRecord, evalIndexRef, "system evaluation type index on failure.");
		}

		// 聚合算法返回值有效性校验
		if (score > 100 || 0 > score) {
			msg = String.format(
					BusinessException.getMessageByLanguage(
							CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
					this.getLevelText(evalIndexRef.getLevel()), evalIndexRef.getName());
			evalRecord.setMessage(evalRecord.getMessage() + msg + "\n");
			score = 0;
		}

		evalIndexRef.setScore(score);
		if (StringUtils.isEmpty(msg)) {
			evalIndexRef.setProgress(EvalIndexRef.Progress.END.getCode());
		} else {
			evalIndexRef.setProgress(EvalIndexRef.Progress.EXCEPTION.getCode());
		}
	}

	/**
	 * 查询变量
	 *
	 * @param indexId 指数id
	 * @return {@code List<EvalRoomPairRsp> }
	 */
	@Override
	public List<EvalRoomPairRsp> selectVariables(String indexId) {
		List<EvalRoomPairRsp> results = Lists.newArrayList();

		EvalIndexRef evalIndexRef = getById(indexId);
		if (evalIndexRef == null) {
			// 非指标项id, 查询变量项
			EvalVariableRef evalVariableRef = evalVariableRefService.getById(indexId);
			if (evalVariableRef == null) {
				log.error("Resource not found! indexId = [{}]", indexId);
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
			evalRoomPairRsp.setId(evalVariableRef.getResourceId());
			evalRoomPairRsp.setLabel(evalVariableRef.getName());
			results.add(evalRoomPairRsp);
			return results;
		}
		Map<String, EvalIndexRef> evalIndexRefMap = lambdaQuery()
				.eq(EvalIndexRef::getRecordId, evalIndexRef.getRecordId())
				.ge(EvalIndexRef::getLevel, evalIndexRef.getLevel()).list().stream()
				.collect(Collectors.toMap(EvalIndexRef::getResourceId,
						evalIndexRef1 -> evalIndexRef1));

		List<String> variableIds = Lists.newArrayList();
		collectionVariableIds(evalIndexRef, evalIndexRefMap, variableIds);

		evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, variableIds).list()
				.forEach(evalVariableRef -> {
					EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
					evalRoomPairRsp.setId(evalVariableRef.getResourceId());
					evalRoomPairRsp.setLabel(evalVariableRef.getName());

					results.add(evalRoomPairRsp);
				});

		// 排序results，让它按照variableIds列表的顺序排列
		List<EvalRoomPairRsp> sortedResults = new ArrayList<>(results.size());
		for (String variableId : variableIds) {
			for (EvalRoomPairRsp result : results) {
				if (variableId.equals(result.getId())) {
					sortedResults.add(result);
					break;
				}
			}
		}

		return sortedResults;
	}

	/**
	 * 收集某一指标项下所有变量项ID
	 *
	 * @param evalIndexRef    需要收集的指标项
	 * @param evalIndexRefMap 所选指标项的所有次级指标
	 * @param variableIds     收集变量项id集合
	 */
	private void collectionVariableIds(EvalIndexRef evalIndexRef,
			Map<String, EvalIndexRef> evalIndexRefMap, List<String> variableIds) {
		List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
		nextIds.forEach(nextId -> {
			variableIds.add(nextId);

			EvalIndexRef evalIndexRef1 = evalIndexRefMap.get(nextId);
			if (evalIndexRef1 != null) {
				collectionVariableIds(evalIndexRef1, evalIndexRefMap, variableIds);
			}
		});
	}

	/**
	 * 周期图
	 */
	private final Map<Integer, Map<Integer, Integer>> CYCLE_MAP = new HashMap<Integer, Map<Integer, Integer>>() {

		{
			put(EvalIndexRef.DataAttr.COST.getCode(), new HashMap<Integer, Integer>() {

				{
					put(1, 20);
					put(2, 50);
					put(3, 100);
				}
			});
			put(EvalIndexRef.DataAttr.BENEFIT.getCode(), new HashMap<Integer, Integer>() {

				{
					put(1, 100);
					put(2, 50);
					put(3, 20);
				}
			});
		}

	};

	/**
	 * 第三专家
	 *
	 * @param datatype       数据类型
	 * @param attribute      属性
	 * @param attributeValue 属性值
	 * @param value          值
	 * @return {@code Integer }
	 */
	@Override
	public Integer thirdSpecialist(Integer datatype, Integer attribute, Integer attributeValue,
			Integer value) {
		if (datatype.equals(EvalIndexRef.DataType.PERCENT.getCode())) {
			return value;
		} else if (datatype.equals(EvalIndexRef.DataType.CYCLE.getCode())) {
			Map<Integer, Integer> attrMap = CYCLE_MAP.get(EvalIndexRef.DataAttr.COST.getCode());
			return attrMap != null ? attrMap.getOrDefault(value, 0) : 0;
		} else if (datatype.equals(EvalIndexRef.DataType.INTEGER.getCode())) {
			// prd259~261页
			// 固定型属性是指属性值越接近某个固定值 越好的属性
			if (attribute.equals(EvalIndexRef.DataAttr.FIXED.getCode())) {
				return Math.min(
						Math.max(100 - (value * 100 - attributeValue * 100) / attributeValue, 0),
						100);
				// 偏离型属性是指属性值越偏离某个固定值 越好的属性
			} else if (attribute.equals(EvalIndexRef.DataAttr.DEVIATE.getCode())) {
				return Math.min(
						Math.max(Math.abs(attributeValue * 100 - value * 100) / attributeValue, 0),
						100);
			}
		}
		return 0;
	}

	/**
	 * 系统评估变量id列表
	 *
	 * @param recordId 记录id
	 * @return {@code Set<String> }
	 */
	@Override
	public Set<String> systemEvalVariableIds(String recordId) {
		Set<String> results = Sets.newHashSet();
		List<String> fourthIds = Lists.newArrayList();
		lambdaQuery().eq(EvalIndexRef::getRecordId, recordId)
				.eq(EvalIndexRef::getLevel, EvalIndex.Level.THIRD.getCode())
				.eq(EvalIndexRef::getType, EvalIndexRef.Type.SYSTEM.getCode()).list()
				.forEach(evalIndexRef -> fourthIds.addAll(
						JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class)));

		if (fourthIds.isEmpty()) {
			return results;
		}

		lambdaQuery().in(EvalIndexRef::getResourceId, fourthIds).list()
				.forEach(evalIndexRef -> results.addAll(
						JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class)));

		return results;
	}

	/**
	 * 结果索引
	 *
	 * @param indexId 指数id
	 * @return {@code EvalRecordResultIndexRsp }
	 */
	@Override
	public EvalRecordResultIndexRsp resultIndex(String indexId) {
		EvalIndexRef evalIndexRef = getById(indexId);
		if (evalIndexRef == null) {
			log.error("Resource not found! indexId = [{}]", indexId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Map<String, EvalRecordResultIndexRsp> evalRecordResultIndexRspMap = this.getBaseMapper()
				.selectResultIndexes(evalIndexRef.getRecordId(), evalIndexRef.getLevel()).stream()
				.collect(Collectors.toMap(EvalRecordResultIndexRsp::getResourceId,
						evalRecordResultIndexRsp -> evalRecordResultIndexRsp));

		EvalRecordResultIndexRsp firstIndex = evalRecordResultIndexRspMap
				.get(evalIndexRef.getResourceId());

		if (evalIndexRef.getLevel().equals(EvalIndex.Level.FIRST.getCode())) {
			firstIndex.setChildren(collectionIndexes(firstIndex, 3, evalRecordResultIndexRspMap));
		} else if (evalIndexRef.getLevel().equals(EvalIndex.Level.SECOND.getCode())) {
			firstIndex.setChildren(collectionIndexes(firstIndex, 2, evalRecordResultIndexRspMap));
		} else if (evalIndexRef.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
			firstIndex.setChildren(collectionIndexes(firstIndex, 3, evalRecordResultIndexRspMap));
		} else if (evalIndexRef.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
			firstIndex.setChildren(collectionIndexes(firstIndex, 2, evalRecordResultIndexRspMap));
		} else {
			log.error("Invalid parameter! level = [{}]", evalIndexRef.getLevel());
			throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
		}
		return firstIndex;
	}

	/**
	 * 集合索引
	 *
	 * @param evalRecordResultIndexRsp    评估记录结果指数响应
	 * @param depth                       深度
	 * @param evalRecordResultIndexRspMap 评估记录结果指数响应地图
	 * @return {@code List<EvalRecordResultIndexRsp> }
	 */
	private List<EvalRecordResultIndexRsp> collectionIndexes(
			EvalRecordResultIndexRsp evalRecordResultIndexRsp, Integer depth,
			Map<String, EvalRecordResultIndexRsp> evalRecordResultIndexRspMap) {
		List<EvalRecordResultIndexRsp> results = Lists.newArrayList();
		if (depth.equals(1) || StringUtils.isEmpty(evalRecordResultIndexRsp.getNextIds())) {
			return results;
		}
		List<String> nextLevelIdList = JsonUtil
				.toJsonObjectArray(evalRecordResultIndexRsp.getNextIds(), String.class);

		if (evalRecordResultIndexRsp.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
			evalVariableRefMapper.getVariableIdByRecordIds(nextLevelIdList)
					.forEach(evalVariableRef -> {
						EvalRecordResultIndexRsp evalRecordResultIndexRsp1 = new EvalRecordResultIndexRsp();
						BeanUtils.copyProperties(evalVariableRef, evalRecordResultIndexRsp1);
						results.add(evalRecordResultIndexRsp1);
					});
		} else {
			nextLevelIdList.forEach(nextLevelId -> {
				EvalRecordResultIndexRsp evalRecordResultIndexRsp1 = evalRecordResultIndexRspMap
						.get(nextLevelId);
				evalRecordResultIndexRsp1.setChildren(collectionIndexes(evalRecordResultIndexRsp1,
						depth - 1, evalRecordResultIndexRspMap));
				results.add(evalRecordResultIndexRsp1);
			});
		}

		return results;
	}
}

