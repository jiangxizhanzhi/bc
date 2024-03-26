package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlarmReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSituationReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalActionTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalActionTemplate;

import java.util.List;

public interface EvalActionTemplateService extends IService<EvalActionTemplate> {

	EvalActionTemplateEnumRsp getActionEnum();

	void addActionTemplateList(List<EvalAlarmReq> alarmReqs, List<EvalSituationReq> situationReqs, String cloudTemplateId, String ruleId);
}
