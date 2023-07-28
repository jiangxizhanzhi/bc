package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cs.service.impl.AuthMessageListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BusinessTemplateEventListener extends AuthMessageListener {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.COMPREHENSIVE_TRAINING.getCode();
	}

	@Override
	public Boolean onUserDelete(List<String> users) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String user : users) {
			businessTemplateService.clearUserAuth(user);

		}

		return true;
	}

}
