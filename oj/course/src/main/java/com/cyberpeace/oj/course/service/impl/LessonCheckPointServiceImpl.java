package com.cyberpeace.oj.course.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.course.dao.entity.LessonCheckPointAnswer;
import com.cyberpeace.oj.course.dao.mapper.LessonCheckPointAnswerMapper;
import com.cyberpeace.oj.course.service.LessonCheckPointService;
import com.cyberpeace.oj.ojbase.service.bo.check.CheckPointAnswer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LessonCheckPointServiceImpl implements LessonCheckPointService {

	@Autowired
	private LessonCheckPointAnswerMapper lessonCheckPointAnswerMapper;

	@Override
	public void submitAnswer(String lessonId, String time, List<CheckPointAnswer> answerContexts) {
		CrCommonValidator.validateId(lessonId);
		if (CollectionUtils.isEmpty(answerContexts)) {
			return;
		}
		String answerJson = JsonUtil.arrayToString(answerContexts);
		String userId = UserInfoContext.getUserContext().getUserId();
		LessonCheckPointAnswer exist = lessonCheckPointAnswerMapper.selectByLessonAndUserAndTime(lessonId,
				userId, time);
		if (null == exist) {
			LessonCheckPointAnswer answer = new LessonCheckPointAnswer();
			answer.setLessonId(lessonId);
			answer.setUserId(userId);
			answer.setAnswer(answerJson);
			answer.setTime(time);
			lessonCheckPointAnswerMapper.insert(answer);
		} else {
			exist.setAnswer(answerJson);
			lessonCheckPointAnswerMapper.updateById(exist);
		}
	}
}
