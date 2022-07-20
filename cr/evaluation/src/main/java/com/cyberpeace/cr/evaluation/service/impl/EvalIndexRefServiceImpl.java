package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.exception.evaluate.RemoteException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import com.cyberpeace.integratation.service.RemotePythonService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvalIndexRefServiceImpl extends ServiceImpl<EvalIndexRefMapper, EvalIndexRef> implements EvalIndexRefService {

    @Autowired
    private RemotePythonService remotePythonService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EvalVariableRefService evalVariableRefService;

    private final Map<Integer, String> int2cn = new HashMap<Integer, String>() {
        {
            put(1, "一");
            put(2, "二");
            put(3, "三");
            put(4, "四");
        }
    };

    @Override
    public void startIndex(EvalIndexRef evalIndexRef,
                           EvalAlgorithmRef evalAlgorithmRef,
                           Map<String, EvalIndexRef> evalIndexRefMap,
                           Map<String, EvalVariableRef> evalVariableRefMap,
                           EvalRecord evalRecord) {
        List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
        List<Integer> scoreList;

        if (evalIndexRef.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
            scoreList = nextIds.stream().map(nextId -> evalVariableRefMap.get(nextId).getScore()).collect(Collectors.toList());
        } else {
            scoreList = nextIds.stream().map(nextId -> evalIndexRefMap.get(nextId).getScore()).collect(Collectors.toList());
        }
        // 正常情况下, scoreList应均为0~100整数，包含`null`为专家评分下未进行计算变量项(五级指标)，无法进行下一步聚合计算，设置score为0，跳过
        if (scoreList.contains(null)) {
            evalIndexRef.setScore(0);
            return;
        }

        // todo 格式化算法入参、算法日志
        Integer score;
        try {
            score = remotePythonService.indexPerform(evalAlgorithmRef.getResourceId(), evalAlgorithmRef.getCode(), scoreList, null);
            EvalLog.info(evalRecord, evalIndexRef, String.format("system evaluation type index result: %s.", score));
        } catch (RemoteServiceException e) {
            score = 0;
            String msg;
            if (e.getMessage().equals(ErrorCode.ALGORITHM_RETURN_NULL)) {
                msg = String.format(
                        BusinessException.getMessageByLanguage(CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
                        this.int2cn.get(evalIndexRef.getLevel()),
                        evalIndexRef.getName()
                );
            } else {
                msg = String.format(
                        BusinessException.getMessageByLanguage(CrBusinessErrorCode.INDEX_EXECUTE_FAILED),
                        this.int2cn.get(evalIndexRef.getLevel()),
                        evalIndexRef.getName()
                );
            }
            evalRecord.setMessage(evalRecord.getMessage() + msg + "\n");
            EvalLog.error(evalRecord, evalIndexRef, "system evaluation type index on failure.");
        }

        if (score > 100 || 0 > score) {
            String msg = String.format(
                    BusinessException.getMessageByLanguage(CrBusinessErrorCode.AGG_ALGORITHM_RESULT_IS_INVALID),
                    this.int2cn.get(evalIndexRef.getLevel()),
                    evalIndexRef.getName()
            );
            evalRecord.setMessage(evalRecord.getMessage() + msg + "\n");
            score = 0;
        }

        evalIndexRef.setScore(score);
    }

    @Override
    public List<EvalRoomPairRsp> selectVariables(String indexId) {
        List<EvalRoomPairRsp> results = Lists.newArrayList();

        EvalIndexRef evalIndexRef = getById(indexId);
        if (evalIndexRef == null) {
            // 非指标项id, 查询变量项
            EvalVariableRef evalVariableRef = evalVariableRefService.getById(indexId);
            if (evalVariableRef == null) {
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
                .ge(EvalIndexRef::getLevel, evalIndexRef.getLevel())
                .list()
                .stream().collect(Collectors.toMap(EvalIndexRef::getResourceId, evalIndexRef1 -> evalIndexRef1));

        Set<String> variableIds = Sets.newHashSet();
        collectionVariableIds(evalIndexRef, evalIndexRefMap, variableIds);

        evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, variableIds).list().forEach(evalVariableRef -> {
            EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
            evalRoomPairRsp.setId(evalVariableRef.getResourceId());
            evalRoomPairRsp.setLabel(evalVariableRef.getName());

            results.add(evalRoomPairRsp);
        });

        return results;
    }

    /**
     * 收集某一指标项下所有变量项ID
     * @param evalIndexRef 需要收集的指标项
     * @param evalIndexRefMap 所选指标项的所有次级指标
     * @param variableIds 收集变量项id集合
     */
    private void collectionVariableIds(EvalIndexRef evalIndexRef, Map<String, EvalIndexRef> evalIndexRefMap, Set<String> variableIds) {
        List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
        nextIds.forEach(nextId -> {
            variableIds.add(nextId);

            EvalIndexRef evalIndexRef1 = evalIndexRefMap.get(nextId);
            if (evalIndexRef1 != null) {
                collectionVariableIds(evalIndexRef1, evalIndexRefMap, variableIds);
            }
        });
    }

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

    @Override
    public Integer thirdSpecialist(Integer datatype, Integer attribute, Integer attributeValue, Integer value) {
        if (datatype.equals(EvalIndexRef.DataType.PERCENT.getCode())) {
            return value;
        } else if (datatype.equals(EvalIndexRef.DataType.CYCLE.getCode())) {
            Map<Integer, Integer> attrMap = CYCLE_MAP.get(EvalIndexRef.DataAttr.COST.getCode());
            return attrMap != null ? attrMap.getOrDefault(value, 0) : 0;
        } else if (datatype.equals(EvalIndexRef.DataType.INTEGER.getCode())) {
            // prd259~261页
            // 固定型属性是指属性值越接近某个固定值 越好的属性
            if (attribute.equals(EvalIndexRef.DataAttr.FIXED.getCode())) {
                return Math.min(Math.max(100 - (value * 100 - attributeValue * 100) / attributeValue, 0), 100);
            // 偏离型属性是指属性值越偏离某个固定值 越好的属性
            } else if (attribute.equals(EvalIndexRef.DataAttr.DEVIATE.getCode())) {
                return Math.min(Math.max(Math.abs(attributeValue * 100 - value * 100) / attributeValue, 0), 100);
            }
        }
        return 0;
    }

    @Override
    public Set<String> systemEvalVariableIds(String recordId) {
        Set<String> results = Sets.newHashSet();
        List<String> fourthIds = Lists.newArrayList();
        lambdaQuery()
                .eq(EvalIndexRef::getRecordId, recordId)
                .eq(EvalIndexRef::getLevel, EvalIndex.Level.THIRD.getCode())
                .eq(EvalIndexRef::getType, EvalIndexRef.Type.SYSTEM.getCode())
                .list()
                .forEach(evalIndexRef -> fourthIds.addAll(JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class)));

        if (fourthIds.isEmpty()) {
            return results;
        }

        lambdaQuery()
                .in(EvalIndexRef::getResourceId, fourthIds)
                .list()
                .forEach(evalIndexRef -> results.addAll(JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class)));

        return results;
    }

    @Override
    public EvalRecordResultIndexRsp resultIndex(String recordId, String indexId) {
        EvalIndexRef evalIndexRef = getById(indexId);
        if (evalIndexRef == null) {
            throw new BusinessException();
        }

        Map<String, EvalRecordResultIndexRsp> evalRecordResultIndexRspMap = this.getBaseMapper().selectResultIndexes(
                recordId, evalIndexRef.getLevel()
        ).stream().collect(Collectors.toMap(EvalRecordResultIndexRsp::getResourceId, evalRecordResultIndexRsp -> evalRecordResultIndexRsp));

        EvalRecordResultIndexRsp firstIndex = evalRecordResultIndexRspMap.get(evalIndexRef.getResourceId());

        if (evalIndexRef.getLevel().equals(EvalIndex.Level.FIRST.getCode())) {
            firstIndex.setChildren(collectionIndexes(firstIndex, 3, evalRecordResultIndexRspMap));
        } else if (evalIndexRef.getLevel().equals(EvalIndex.Level.SECOND.getCode())) {
            firstIndex.setChildren(collectionIndexes(firstIndex, 2, evalRecordResultIndexRspMap));
        } else if (evalIndexRef.getLevel().equals(EvalIndex.Level.THIRD.getCode())) {
            firstIndex.setChildren(collectionIndexes(firstIndex, 3, evalRecordResultIndexRspMap));
        } else if (evalIndexRef.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
            firstIndex.setChildren(collectionIndexes(firstIndex, 2, evalRecordResultIndexRspMap));
        } else {
            throw new BusinessException();
        }
        return firstIndex;
    }

    private List<EvalRecordResultIndexRsp> collectionIndexes(EvalRecordResultIndexRsp evalRecordResultIndexRsp,
                                                             Integer depth,
                                                             Map<String, EvalRecordResultIndexRsp> evalRecordResultIndexRspMap) {
        List<EvalRecordResultIndexRsp> results = Lists.newArrayList();
        if (depth.equals(1) || StringUtils.isEmpty(evalRecordResultIndexRsp.getNextIds())) {
            return results;
        }
        List<String> nextLevelIdList = JsonUtil.toJsonObjectArray(evalRecordResultIndexRsp.getNextIds(), String.class);

        if (evalRecordResultIndexRsp.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
            evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, nextLevelIdList).list().forEach(evalVariableRef -> {
                EvalRecordResultIndexRsp evalRecordResultIndexRsp1 = new EvalRecordResultIndexRsp();
                BeanUtils.copyProperties(evalVariableRef, evalRecordResultIndexRsp1);
                results.add(evalRecordResultIndexRsp1);
            });
        } else {
            nextLevelIdList.forEach(nextLevelId -> {
                EvalRecordResultIndexRsp evalRecordResultIndexRsp1 = evalRecordResultIndexRspMap.get(nextLevelId);
                evalRecordResultIndexRsp1.setChildren(collectionIndexes(evalRecordResultIndexRsp1, depth - 1, evalRecordResultIndexRspMap));
                results.add(evalRecordResultIndexRsp1);
            });
        }

        return results;
    }
}
