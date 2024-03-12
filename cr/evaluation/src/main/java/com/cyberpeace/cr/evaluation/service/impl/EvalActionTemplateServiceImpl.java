package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalActionTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalActionTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalActionTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalActionTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalActionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalActionTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalActionTemplateServiceImpl extends ServiceImpl<EvalActionTemplateMapper, EvalActionTemplate> implements EvalActionTemplateService {

	@Autowired
	private EvalActionTemplateMapper evalActionTemplateMapper;

	@Override
	public String addAction(EvalActionTemplateAddReq evalActionTemplateAddReq) {
		EvalActionTemplate evalActionTemplate = BeanUtils.copy(evalActionTemplateAddReq, EvalActionTemplate.class);
		evalActionTemplateMapper.insert(evalActionTemplate);
		return evalActionTemplate.getResourceId();
	}

	@Override
	public void deleteAction(List<String> ids) {
		evalActionTemplateMapper.deleteBatchIds(ids);
	}

	@Override
	public void modifyAction(EvalActionTemplateModifyReq evalActionTemplateModifyReq) {
		EvalActionTemplate evalActionTemplate = BeanUtils.copy(evalActionTemplateModifyReq, EvalActionTemplate.class);
		evalActionTemplateMapper.updateById(evalActionTemplate);
	}

	@Override
	public EvalActionTemplateEnumRsp getActionEnum() {
		EvalActionTemplateEnumRsp evalActionTemplateEnumRsp = new EvalActionTemplateEnumRsp();
		evalActionTemplateEnumRsp.setActionType(Arrays.stream(EvalActionTemplate.ActionType.values())
													  .map(actionType -> new CodeNameRsp(actionType.getCode(), actionType.getDesc())).collect(Collectors.toList()));
		evalActionTemplateEnumRsp.setLevel(Arrays.stream(EvalActionTemplate.Level.values())
												 .map(level -> new CodeNameRsp(level.getCode(), level.getDesc())).collect(Collectors.toList()));
		return evalActionTemplateEnumRsp;
	}
}
