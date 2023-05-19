package com.cyberpeace.oj.person.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.person.dao.entity.*;
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
   *
   * @param page
   * @param userId
   * @param parent
   * @return
   */
  IPage<PersonComment> selectCommentByUser(
      Page page,
      @Param("userId") String userId,
      @Param("parent") String parent,
      @Param("businessIds") List<String> businessIds);
  /**
   * 获取用户的笔记
   *
   * @param page
   * @param userId
   * @param noteType
   * @return
   */
  IPage<PersonNote> selectNoteByUser(
      Page page,
      @Param("userId") String userId,
      @Param("noteType") Integer noteType,
      @Param("businessIds") List<String> businessIds,
      @Param("businessType") Integer businessType);

  /**
   * 获取用户收藏的资源
   *
   * @param userId
   * @return
   */
  IPage<PersonCollection> selectByUser(
      Page page,
      @Param("userId") String userId,
      @Param("businessType") String businessType,
      @Param("businessIds") List<String> businessIds);

  /**
   * 获取用户课程学习数据
   *
   * @param userId
   * @return
   */
  StudyRecord selectRecordByUser(
      @Param("userId") String userId, @Param("lessonIds") List<String> lessonIds);

  /**
   * 获取用户实验报告
   *
   * @param userId
   * @return
   */
  Integer selectReportCount(
      @Param("userId") String userId, @Param("lessonIds") List<String> lessonIds);

  /**
   * 获取用户练习完成排名、得分、数量
   *
   * @param userId
   * @return
   */
  StudyRecord selectPracticeByUser(
      @Param("userId") String userId, @Param("lessonIds") List<String> lessonIds);

  /**
   * 获取用户解出实操题、理论题数量
   *
   * @param userId
   * @return
   */
  StudyRecord selectSlovedPracticeByUser(@Param("userId") String userId);

  /**
   * 获取distinct用户
   *
   * @return
   */
  List<String> selectDistinctUser();

  /**
   * 获取用户30天内完成的课程
   *
   * @param userId
   * @return
   */
  List<LessonRecord> selectLessonByTime(
      @Param("userId") String userId,
      @Param("startTime") Date startTime,
      @Param("endTime") Date endTime,
      @Param("lessonIds") List<String> lessonIds);

  /**
   * 获取用户30天内完成的报告
   *
   * @param userId
   * @return
   */
  List<Note> selectReportByTime(
      @Param("userId") String userId,
      @Param("startTime") Date startTime,
      @Param("endTime") Date endTime,
      @Param("lessonIds") List<String> lessonIds);

  List<NoteEvaluate> selectReportAverageByTime(
          @Param("userId") String userId,
          @Param("startTime") Date startTime,
          @Param("endTime") Date endTime,
          @Param("lessonIds") List<String> lessonIds);

  List<String> selectLessonByAuth(@Param("courseIds") List<String> courseIds);

  List<String> selectLessonByAdmin(@Param("lessonType") Integer lessonType);

  List<String> selectExpLessonByAuth(@Param("courseIds") List<String> courseIds);

  List<String> selectOperationPracticeByAuth();

  List<String> selectAfterclassPracticeByAuth(@Param("lessonIds") List<String> lessonIds);

  List<String> selectTheoryPracticeByAuth();

  List<String> selectPublicCourse(@Param("courseIds") List<String> courseIds);

}
