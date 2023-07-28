package com.cyberpeace.oj.practice.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cs.service.impl.AuthMessageListener;
import com.cyberpeace.oj.practice.service.PracticeSetService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PracticeAuthListener extends AuthMessageListener {

	@Autowired
	private PracticeSetService practiceSetService;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.PRACTICE.getCode();
	}

	@Override
	public Boolean onUserDelete(List<String> users) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String user : users) {
			practiceSetService.clearUserAuth(user);

		}

		return true;
	}

	@Override
	public Boolean onOrgDelete(List<String> orgs) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String org : orgs) {
			practiceSetService.clearOrgAuth(org);

		}

		return true;
	}

}
