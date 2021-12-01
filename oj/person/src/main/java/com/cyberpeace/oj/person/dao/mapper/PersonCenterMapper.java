package com.cyberpeace.oj.person.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.person.dao.entity.PersonCollection;
import com.cyberpeace.oj.person.dao.entity.PersonComment;
import com.cyberpeace.oj.person.dao.entity.PersonNote;
import com.cyberpeace.oj.person.dao.entity.StudyRecord;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface PersonCenterMapper extends BaseMapper<ResourceCollection> {


	/**
	 * 获取用户的评论、回复
	 * @param page
	 * @param userId
	 * @param parent
	 * @return
	 */
	IPage<PersonComment> selectCommentByUser(Page page, @Param("userId") String userId, @Param("parent") String parent);
	/**
	 * 获取用户的笔记
	 * @param page
	 * @param userId
	 * @param noteType
	 * @return
	 */
	IPage<PersonNote> selectNoteByUser(Page page, @Param("userId") String userId, @Param("noteType") Integer noteType);

	/**
	 * 获取用户收藏的资源
	 * @param userId
	 * @return
	 */
	IPage<PersonCollection> selectByUser(Page page, @Param("userId") String userId, @Param("businessType") String businessType);


	/**
	 * 获取用户课程学习数据
	 * @param userId
	 * @return
	 */
	StudyRecord selectRecordByUser(@Param("userId") String userId);

	/**
	 * 获取用户实验报告
	 * @param userId
	 * @return
	 */
	Integer selectReportCount(@Param("userId") String userId);

	/**
	 * 获取用户练习完成排名、得分、数量
	 * @param userId
	 * @return
	 */
	StudyRecord selectPracticeByUser(@Param("userId") String userId);

	/**
	 * 获取用户30天内完成的课程
	 * @param userId
	 * @return
	 */
	List<Date> selectCourseByTime(@Param("userId") String userId, @Param("startTime") Date startTime,
										  @Param("endTime") Date endTime);

	/**
	 * 获取用户30天内完成的练习
	 * @param userId
	 * @return
	 */
	List<PracticeSolved> selectPracticeByTime(@Param("userId") String userId, @Param("startTime") Date startTime,
										@Param("endTime") Date endTime);
}
