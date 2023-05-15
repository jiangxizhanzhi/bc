package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;
import com.cyberpeace.oj.practice.dao.mapper.PracticeMistakeMapper;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeMistakeServiceImpl extends ServiceImpl<PracticeMistakeMapper, PracticeMistake> implements PracticeMistakeService {

	@Autowired
	private PracticeMistakeMapper practiceMistakeMapper;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Override
	public Integer getTheoryMistakeCount(String userId, Integer practiceType) {
		return practiceMistakeMapper.countByUserId(userId, practiceType);
	}

	/**
	 * 答错次数+1
	 * @param userId
	 * @param practiceId
	 * @param practiceType
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
	 * @param userId
	 * @param practiceId
	 * @param practiceType
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

	@Override
	public List<PersonalMistakeRes> getPracticeMistake() {
		UserInfo userInfo = UserInfoContext.getUserContext();
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, true);
		List<String> practiceIds = practiceTheoryService.getPracticeIdsByUserAndSet(authContext, null);
		return practiceMistakeMapper.selectByPracticeId(practiceIds);
	}
}
