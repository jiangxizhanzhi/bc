package com.cyberpeace.oj.course.service;

import java.util.List;

import com.cyberpeace.oj.ojbase.service.bo.check.CheckPointAnswer;

public interface LessonCheckPointService {

	/**
	 * @param lessonId
	 * @param answerContexts
	 */
	void submitAnswer(String lessonId, List<CheckPointAnswer> answerContexts);
}
