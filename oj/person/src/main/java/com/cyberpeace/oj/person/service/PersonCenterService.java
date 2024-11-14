package com.cyberpeace.oj.person.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;

import java.util.Date;
import java.util.List;

/**
 * 人中心Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface PersonCenterService {

	/**
	 * 分页查询用户所有的评论信息
	 *
	 * @param page
	 * @param userId      用户ID
	 * @param parent      父母
	 * @param authContext auth 上下文
	 * @return
	 */
	IPage<PersonComment> pageComment(Page<PersonComment> page, String userId, String parent,
			AuthContext authContext);

	/**
	 * 分页查询用户所有笔记
	 *
	 * @param page
	 * @param userId       用户ID
	 * @param authContext  auth 上下文
	 * @param businessType 业务类型
	 * @param practiceType 练习类型
	 * @return
	 */
	IPage<PersonNote> pageNotes(Page<PersonNote> page, String userId, AuthContext authContext,
								Integer businessType, Integer practiceType);

	/**
	 * 分页查询用户所有收藏
	 *
	 * @param page
	 * @param userId       用户ID
	 * @param type         类型
	 * @param authContext  auth 上下文
	 * @param practiceType 练习类型
	 * @return
	 */
	IPage<PersonCollection> pageCollections(Page<PersonCollection> page, String userId, String type,
			AuthContext authContext, Integer practiceType);

	/**
	 * 查询用户的学习课程与训练的记录
	 *
	 * @param userId      用户ID
	 * @param authContext auth 上下文
	 * @return
	 */
	BaseRsp<StudyRecord> studyRecords(String userId, AuthContext authContext);

	/**
	 * 查询30天内用户的学习课程与训练的记录
	 *
	 * @param userId      用户ID
	 * @param startTime
	 * @param endTime
	 * @param authContext auth 上下文
	 * @return
	 */
	BaseRsp<BrokenLine> studyPeriodRecords(String userId, Date startTime, Date endTime,
			AuthContext authContext);

	/**
	 * 查询收藏的习题信息
	 *
	 * @param userId
	 * @param practiceType
	 * @return
	 */
	List<CollectionPracticeBo> selectCollectionPractice(String userId, Integer practiceType);

	/**
	 * 查询收集资源id列表
	 *
	 * @param userId      用户id
	 * @param authContext auth 上下文
	 * @return {@code List<String> }
	 */
	List<String> selectCollectionResourceIds(String userId, AuthContext authContext);
}
