package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;

import java.util.List;

/**
 * 实践错误Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface PracticeMistakeService extends IService<PracticeMistake> {

	/**
	 * 获取理论错误计数
	 *
	 * @param userId      用户id
	 * @param practiceIds 实践id列表
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Integer
	 */
	Integer getTheoryMistakeCount(String userId, List<String> practiceIds);

	/**
	 * 新增不對
	 *
	 * @param userId       用户id
	 * @param practiceId   实践id
	 * @param practiceType 执业类型
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void addIncorrect(String userId, String practiceId, Integer practiceType);

	/**
	 * 新增正确
	 *
	 * @param userId       用户id
	 * @param practiceId   实践id
	 * @param practiceType 执业类型
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void addCorrect(String userId, String practiceId, Integer practiceType);

	/**
	 * 获取实践错误
	 *
	 * @return {@code List<PersonalMistakeRes> }
	 * @throws
	 * @see List<PersonalMistakeRes>
	 */
	List<PersonalMistakeRes> getPracticeMistake();

	/**
	 * 浴缸删除
	 *
	 * @param practiceIds 实践id列表
	 * @param userId      用户id
	 * @return {@code Boolean }
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see Boolean
	 */
	Boolean bathDelete(List<String> practiceIds, String userId);
}
