package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeMistakeMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 练习错误Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
public class PracticeMistakeServiceImpl extends ServiceImpl<PracticeMistakeMapper, PracticeMistake> implements PracticeMistakeService {

	/**
	 * 练习错误Mapper
	 */
	@Autowired
	private PracticeMistakeMapper practiceMistakeMapper;

	/**
	 * 练习设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 练习理论Mapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 获取理论错误计数
	 *
	 * @param userId      用户id
	 * @param practiceIds 练习id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer getTheoryMistakeCount(String userId, List<String> practiceIds) {
		return practiceMistakeMapper.countByUserId(userId, practiceIds);
	}

	/**
	 * 答错次数+1
	 *
	 * @param userId
	 * @param practiceId
	 * @param practiceType 练习类型
	 */
	@Override
	public void addIncorrect(String userId, String practiceId, Integer practiceType) {
		LambdaQueryWrapper<PracticeMistake> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(PracticeMistake::getPracticeId, practiceId);
		lambdaQueryWrapper.eq(PracticeMistake::getUserId, userId);
		PracticeMistake practiceMistake = practiceMistakeMapper.selectOne(lambdaQueryWrapper);
		if (practiceMistake == null) {
			practiceMistake = new PracticeMistake();
			practiceMistake.setPracticeId(practiceId);
			practiceMistake.setUserId(userId);
			practiceMistake.setPracticeType(practiceType);
			practiceMistake.setCorrect(0);
			practiceMistake.setIncorrect(1);
			practiceMistakeMapper.insert(practiceMistake);
		} else {
			practiceMistake.setIncorrect(practiceMistake.getIncorrect() + 1);
			practiceMistakeMapper.updateById(practiceMistake);
		}
	}

	/**
	 * 答对次数+1
	 *
	 * @param userId
	 * @param practiceId
	 * @param practiceType 练习类型
	 */
	@Override
	public void addCorrect(String userId, String practiceId, Integer practiceType) {
		LambdaQueryWrapper<PracticeMistake> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(PracticeMistake::getPracticeId, practiceId);
		lambdaQueryWrapper.eq(PracticeMistake::getUserId, userId);
		PracticeMistake practiceMistake = practiceMistakeMapper.selectOne(lambdaQueryWrapper);
		if (practiceMistake != null) {
			practiceMistake.setCorrect(practiceMistake.getCorrect() + 1);
			practiceMistakeMapper.updateById(practiceMistake);
		}
	}

	/**
	 * 获取练习错误
	 *
	 * @return {@code List<PersonalMistakeRes> }
	 */
	@Override
	public List<PersonalMistakeRes> getPracticeMistake() {
		UserInfo userInfo = UserInfoContext.getUserContext();
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, true);
		List<String> authSetIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authSetIds)) {
			return new ArrayList<>();
		}

		// 过滤非公开习题集
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), authContext.getIsWeb());
		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
		authSetIds.retainAll(setIds);
		if (CollectionUtils.isEmpty(authSetIds)) {
			return new ArrayList<>();
		}

		List<String> practiceIds = practiceTheoryMapper.selectBySetIds(authSetIds,
				authContext.getIsWeb());

		if (CollectionUtils.isEmpty(practiceIds)) {
			return new ArrayList<>();
		}
		return practiceMistakeMapper.selectByPracticeId(practiceIds, userInfo.getUserId());
	}

	/**
	 * 浴缸删除
	 *
	 * @param practiceIds 练习id列表
	 * @param userId      用户id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean bathDelete(List<String> practiceIds, String userId) {
		practiceMistakeMapper.deleteByPracticeIds(practiceIds, userId);
		return true;
	}
}
