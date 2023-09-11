package com.cyberpeace.oj.course.service;

import java.util.List;

import com.cyberpeace.oj.ojbase.service.bo.check.CheckPointAnswer;

public interface LessonCheckPointService {

	/**
	 * @param lessonId
	 * @param time
	 * @param answerContexts
	 */
	void submitAnswer(String lessonId, String time, List<CheckPointAnswer> answerContexts);
}
