package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.exception.evaluate.RemoteException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import com.cyberpeace.integratation.service.RemotePythonService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
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
        EvalIndexRef thirdIndex = lambdaQuery()
                .eq(EvalIndexRef::getLevel, EvalIndex.Level.THIRD.getCode())
                .eq(EvalIndexRef::getResourceId, indexId)
                .one();
        if (thirdIndex == null) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }

        List<EvalIndexRef> fourthIndexes = lambdaQuery()
                .eq(EvalIndexRef::getLevel, EvalIndexRef.Level.AGGREGATION.getCode())
                .in(EvalIndexRef::getResourceId, JsonUtil.toJsonObjectArray(thirdIndex.getNextIds(), String.class))
                .list();

        Set<String> variables = Sets.newHashSet();
        fourthIndexes.forEach(evalIndexRef -> {
            variables.addAll(JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class));
        });

        List<EvalRoomPairRsp> results = Lists.newArrayList();

        evalVariableRefService.lambdaQuery().in(EvalVariableRef::getResourceId, variables).list().forEach(evalVariableRef -> {
            EvalRoomPairRsp evalRoomPairRsp = new EvalRoomPairRsp();
            evalRoomPairRsp.setId(evalVariableRef.getResourceId());
            evalRoomPairRsp.setLabel(evalVariableRef.getName());

            results.add(evalRoomPairRsp);
        });

        return results;
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
}
