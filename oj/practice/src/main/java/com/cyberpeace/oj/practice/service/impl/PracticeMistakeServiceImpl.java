package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PracticeMistakeServiceImpl extends ServiceImpl<PracticeMistakeMapper, PracticeMistake> implements PracticeMistakeService {

	@Autowired
	private PracticeMistakeMapper practiceMistakeMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Override
	public Integer getTheoryMistakeCount(String userId, List<String> practiceIds) {
		return practiceMistakeMapper.countByUserId(userId, practiceIds);
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
		List<PersonalMistakeRes> theoryList = practiceMistakeMapper.selectByPracticeId( userInfo.getUserId());
		//实操题
//		List<PersonalMistakeRes> operationList = practiceMistakeMapper.selectOpertionByPracticeId(userInfo.getUserId());
//		if (CollectionUtils.isNotEmpty(operationList)) {
//			for (PersonalMistakeRes res : operationList) {
//				if (StringUtils.isEmpty(res.getDirection())) {
//					continue;
//				}
//				res.setDirection(JSON.parseArray(res.getDirection(), String.class).stream().collect(Collectors.joining(",")));
//			}
//			theoryList.addAll(operationList);
//		}
		return theoryList;
	}

	@Override
	public Boolean bathDelete(List<String> practiceIds, String userId) {
		practiceMistakeMapper.deleteByPracticeIds(practiceIds, userId);
		return true;
	}
}
