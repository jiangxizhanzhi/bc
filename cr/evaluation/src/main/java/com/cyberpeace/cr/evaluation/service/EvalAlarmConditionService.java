package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlarmConditionAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlarmConditionReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlarmCondition;

import java.util.List;

public interface EvalAlarmConditionService extends IService<EvalAlarmCondition> {

    void addAlarmConditionList(List<EvalAlarmConditionReq> alarmConditions, String cloudTemplateId, String ruleId);
}
