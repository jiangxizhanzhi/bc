package com.cyberpeace.oj.person.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.person.controller.res.StudyCalendarRes;
import com.cyberpeace.oj.person.controller.res.StudyStaticsRes;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;
import com.cyberpeace.oj.practice.controller.res.ProgressPracticeRes;

import java.util.Date;
import java.util.List;

public interface PersonCenterService {

	/**
	 * 分页查询用户所有的评论信息
	 *
	 * @param page
	 * @param userId 用户ID
	 * @return
	 */
	IPage<PersonComment> pageComment(Page<PersonComment> page, String userId, String parent,
			AuthContext authContext);

	/**
	 * 分页查询用户所有笔记
	 *
	 * @param page
	 * @param userId 用户ID
	 * @return
	 */
	IPage<PersonNote> pageNotes(Page<PersonNote> page, String userId, AuthContext authContext,
								Integer businessType, Integer practiceType);

	/**
	 * 分页查询用户所有收藏
	 *
	 * @param page
	 * @param userId 用户ID
	 * @return
	 */
	IPage<PersonCollection> pageCollections(Page<PersonCollection> page, String userId, String type,
			AuthContext authContext, Integer practiceType);

	/**
	 * 查询用户的学习课程与训练的记录
	 *
	 * @param userId 用户ID
	 * @return
	 */
	BaseRsp<StudyRecord> studyRecords(String userId, AuthContext authContext);

	/**
	 * 查询30天内用户的学习课程与训练的记录
	 *
	 * @param userId 用户ID
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BaseRsp<BrokenLine> studyPeriodRecords(String userId, Date startTime, Date endTime,
			AuthContext authContext);

	/**
	 * 查询收藏的习题信息
	 * @param userId
	 * @param practiceType
	 * @return
	 */
	List<CollectionPracticeBo> selectCollectionPractice(String userId, Integer practiceType);

	List<String> selectCollectionResourceIds(String userId, AuthContext authContext);

	StudyStaticsRes studyStatics();

	List<StudyCalendarRes> studyCalendar(String startTime, String endTime);

	List<ProgressPracticeRes> abilityFramework();

}
