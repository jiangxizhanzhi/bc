package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.integratation.service.RemotePythonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EvalIndexRefServiceImpl extends ServiceImpl<EvalIndexRefMapper, EvalIndexRef> implements EvalIndexRefService {

    @Autowired
    private RemotePythonService remotePythonService;

    @Autowired
    private TransactionTemplate transactionTemplate;

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
}
