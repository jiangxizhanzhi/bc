package com.cyberpeace.oj.course.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.dao.entity.LessonPrepareNote;
import com.cyberpeace.oj.course.dao.mapper.LessonPrepareNoteMapper;
import com.cyberpeace.oj.course.service.LessonPrepareNoteService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LessonPrepareNoteServiceImpl implements LessonPrepareNoteService {

	@Autowired
	private LessonPrepareNoteMapper lessonPrepareNoteMapper;

	@Override
	public void submitNote(String lessonId, String note) {
		UserInfo userInfo = UserInfoContext.getUserContext();
		if (null == userInfo) {
			return;
		}
		if (StringUtils.isBlank(note)) {
			return;
		}
		LessonPrepareNote exist = lessonPrepareNoteMapper.getByLessonAndUser(lessonId,
				userInfo.getUserId());
		if (null == exist) {
			LessonPrepareNote toInsert = new LessonPrepareNote();
			toInsert.setLessonId(lessonId);
			toInsert.setUserId(userInfo.getUserId());
			toInsert.setNote(note);
			lessonPrepareNoteMapper.insert(toInsert);
		} else {
			lessonPrepareNoteMapper.updateByLessonAndUser(lessonId, userInfo.getUserId(), note);
		}
	}
}
