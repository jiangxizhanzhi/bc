package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlarmReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSituationReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalActionTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalActionTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalActionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalActionTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalActionTemplateServiceImpl extends ServiceImpl<EvalActionTemplateMapper, EvalActionTemplate> implements EvalActionTemplateService {

	@Override
	public EvalActionTemplateEnumRsp getActionEnum() {
		EvalActionTemplateEnumRsp evalActionTemplateEnumRsp = new EvalActionTemplateEnumRsp();
		evalActionTemplateEnumRsp.setActionType(Arrays.stream(EvalActionTemplate.ActionType.values())
													  .map(actionType -> new CodeNameRsp(actionType.getCode(), actionType.getDesc())).collect(Collectors.toList()));
		evalActionTemplateEnumRsp.setLevel(Arrays.stream(EvalActionTemplate.Level.values())
												 .map(level -> new CodeNameRsp(level.getCode(), level.getDesc())).collect(Collectors.toList()));
		return evalActionTemplateEnumRsp;
	}

	@Override
	public void	addActionTemplateList(List<EvalAlarmReq> alarmReqs, List<EvalSituationReq> situationReqs, String cloudTemplateId, String ruleId) {
		List<EvalActionTemplate> evalActionTemplateList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(situationReqs)) {
			for (EvalSituationReq evalSituationReq : situationReqs) {
				EvalActionTemplate evalActionTemplate = BeanUtils.copy(evalSituationReq, EvalActionTemplate.class);
				evalActionTemplate.setCloudTemplateId(cloudTemplateId);
				evalActionTemplate.setRuleId(ruleId);
				evalActionTemplate.setActionType(EvalActionTemplate.ActionType.SITUATION.getCode());
				evalActionTemplateList.add(evalActionTemplate);
			}
		}
		if (CollectionUtils.isNotEmpty(alarmReqs)) {
			for (EvalAlarmReq evalAlarmReq : alarmReqs) {
				EvalActionTemplate evalActionTemplate = BeanUtils.copy(evalAlarmReq, EvalActionTemplate.class);
				evalActionTemplate.setCloudTemplateId(cloudTemplateId);
				evalActionTemplate.setRuleId(ruleId);
				evalActionTemplate.setActionType(EvalActionTemplate.ActionType.ALARM.getCode());
				evalActionTemplateList.add(evalActionTemplate);
			}
		}
		saveBatch(evalActionTemplateList);
	}
}
