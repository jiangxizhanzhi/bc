package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cs.service.impl.AuthMessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务模板事件监听器
 *
 * @author SN
 * @date 2024/11/13
 */
@Service
@Slf4j
public class BusinessTemplateEventListener extends AuthMessageListener {

	/**
	 * 业务模板Service
	 */
	@Autowired
	private BusinessTemplateService businessTemplateService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer declareMessageType() {
		return BusinessCode.COMPREHENSIVE_TRAINING.getCode();
	}

	/**
	 * 用户删除时
	 *
	 * @param users 用户
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onUserDelete(List<String> users) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String user : users) {
			businessTemplateService.clearUserAuth(user);

		}

		return true;
	}

}
