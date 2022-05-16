package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.util.JsonUtil;
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

import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Override
    public Boolean startIndex(EvalIndexRef evalIndexRef, EvalAlgorithmRef evalAlgorithmRef, Map<String, EvalIndexRef> evalIndexRefMap, Map<String, EvalVariableRef> evalVariableRefMap) {
        List<String> nextIds = JsonUtil.toJsonObjectArray(evalIndexRef.getNextIds(), String.class);
        List<Integer> scoreList;

        // nextId获取项目节点一定拥有score属性
        if (evalIndexRef.getLevel().equals(EvalIndexRef.Level.AGGREGATION.getCode())) {
            scoreList = nextIds.stream().map(nextId -> evalVariableRefMap.get(nextId).getScore()).collect(Collectors.toList());
        } else {
            scoreList = nextIds.stream().map(nextId -> evalIndexRefMap.get(nextId).getScore()).collect(Collectors.toList());
        }

        Integer score = remotePythonService.indexPerform(null, evalAlgorithmRef.getResourceId(), evalAlgorithmRef.getCode(), scoreList, null);

        evalIndexRef.setScore(score);
        return SqlHelper.retBool(this.getBaseMapper().updateById(evalIndexRef));
    }

    @Override
    public List<EvalRoomPairRsp> selectVariables(String indexId) {
        EvalIndexRef thirdIndex = lambdaQuery()
                .eq(EvalIndexRef::getLevel, EvalIndex.Level.THIRD.getCode())
                .eq(EvalIndexRef::getResourceId, indexId)
                .one();

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
}
