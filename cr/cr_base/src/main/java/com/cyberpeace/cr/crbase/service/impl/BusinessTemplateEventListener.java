package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.AuthMessageListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务模板事件听者
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see AuthMessageListener
 * @since 1.0.0
 */
@Service
@Slf4j
public class BusinessTemplateEventListener extends AuthMessageListener {

	/**
	 * 业务模板Service
	 *
	 * @see BusinessTemplateService
	 */
	@Autowired
	private BusinessTemplateService businessTemplateService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.COMPREHENSIVE_TRAINING.getCode();
	}

	/**
	 * 用户删除时
	 *
	 * @param users 用户
	 * @return {@code Boolean }
	 * @throws
	 * @see List<String>
	 * @see Boolean
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
