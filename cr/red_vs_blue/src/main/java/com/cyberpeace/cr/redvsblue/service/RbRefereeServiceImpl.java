package com.cyberpeace.cr.redvsblue.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListBo;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.integratation.service.RemoteUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbRefereeServiceImpl implements RbRefereeService {

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public List<EvaluationListRsp> getEvaluationList(EvaluationListReq req) {
		// get task_submit list
		List<EvaluationListBo> boList = crTaskSubmitMapper.list(req.getRoomId(), req.getRoleId(),
				req.getUserId(), req.getEvaluateStatus());
		List<EvaluationListRsp> rspList = BeanUtils.deepCopyList(boList, EvaluationListRsp.class);
		List<String> userIds = rspList.stream().map(EvaluationListBo::getSubmitUser)
				.collect(Collectors.toList());
		// get user info
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);
		for (EvaluationListRsp rsp : rspList) {
			UserInfo userInfo = userInfoMap.get(rsp.getSubmitUser());
			if (userInfo == null) {
				continue;
			}
			rsp.setSubmitUserAvatar(userInfo.getAvatarUrl());
			rsp.setSubmitUserName(userInfo.getName());
		}
		return rspList;
	}
}
