package com.cyberpeace.oj.practice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.AuthMessageListener;
import com.cyberpeace.oj.practice.service.PracticeSetService;

import lombok.extern.slf4j.Slf4j;

/**
 * 练习身份验证侦听器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AuthMessageListener
 * @since 1.0.0
 */
@Service
@Slf4j
public class PracticeAuthListener extends AuthMessageListener {

    /**
     * 实践设置Service
     *
     * @see PracticeSetService
     */
    @Autowired
	private PracticeSetService practiceSetService;

    /**
     * 声明消息类型
     *
     * @return {@code Integer }
     * @throws
     * @see Integer
     */
    @Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.PRACTICE.getCode();
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
			practiceSetService.clearUserAuth(user);

		}

		return true;
	}

    /**
     * 在组织删除时
     *
     * @param orgs 组织
     * @return {@code Boolean }
     * @throws
     * @see List<String>
     * @see Boolean
     */
    @Override
	public Boolean onOrgDelete(List<String> orgs) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String org : orgs) {
			practiceSetService.clearOrgAuth(org);

		}

		return true;
	}

}
