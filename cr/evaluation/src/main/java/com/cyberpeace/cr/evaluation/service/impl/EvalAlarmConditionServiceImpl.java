package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlarmConditionAddReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlarmCondition;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlarmConditionMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlarmConditionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalAlarmConditionServiceImpl extends ServiceImpl<EvalAlarmConditionMapper, EvalAlarmCondition> implements EvalAlarmConditionService {

    @Override
    public void addAlarmConditionList(List<EvalAlarmConditionAddReq> alarmConditionAddReqList, String cloudTemplateId, String ruleId) {
        if (CollectionUtils.isEmpty(alarmConditionAddReqList)) {
            return;
        }
        List<EvalAlarmCondition> evalHostTemplateList = alarmConditionAddReqList.stream().map(alarmConditionAddReq -> {
            EvalAlarmCondition evalAlarmCondition = BeanUtils.copy(alarmConditionAddReq, EvalAlarmCondition.class);
            evalAlarmCondition.setCloudTemplateId(cloudTemplateId);
            evalAlarmCondition.setRuleId(ruleId);
            return evalAlarmCondition;
        }).collect(Collectors.toList());
        saveBatch(evalHostTemplateList);
    }
}
