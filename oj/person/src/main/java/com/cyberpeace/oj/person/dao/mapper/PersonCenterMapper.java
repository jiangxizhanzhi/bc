package com.cyberpeace.oj.person.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.person.dao.entity.PersonCollection;
import com.cyberpeace.oj.person.dao.entity.PersonComment;
import com.cyberpeace.oj.person.dao.entity.PersonNote;
import com.cyberpeace.oj.person.dao.entity.StudyRecord;

/**
 * 人中心Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface PersonCenterMapper extends BaseMapper<ResourceCollection> {

    /**
     * 获取用户的评论、回复
     *
     * @param page
     * @param userId
     * @param parent
     * @param businessIds 业务id列表
     * @return
     */
    IPage<PersonComment> selectCommentByUser(
            Page<PersonComment> page,
            @Param("userId") String userId,
            @Param("parent") String parent,
            @Param("businessIds") List<String> businessIds);

    /**
     * 获取用户的笔记
     *
     * @param page
     * @param userId
     * @param noteType
     * @param businessIds  业务id列表
     * @param businessType 业务类型
     * @param practiceType 练习类型
     * @return
     */
    IPage<PersonNote> selectNoteByUser(
            Page<PersonNote> page,
            @Param("userId") String userId,
            @Param("noteType") Integer noteType,
            @Param("businessIds") List<String> businessIds,
            @Param("businessType") Integer businessType,
            @Param("practiceType") Integer practiceType);


    /**
     * 获取用户资源
     *
     * @param userId
     * @param noteType 笔记类型
     * @return
     */
    List<String> selectByUserId(@Param("userId") String userId,@Param("noteType") Integer noteType);


    /**
     * 获取用户收藏的资源
     *
     * @param page         分页
     * @param userId
     * @param businessType 业务类型
     * @param businessIds  业务id列表
     * @param practiceType 练习类型
     * @return
     */
    IPage<PersonCollection> selectByUser(
            Page<PersonCollection> page,
            @Param("userId") String userId,
            @Param("businessType") String businessType,
            @Param("businessIds") List<String> businessIds,
            @Param("practiceType") Integer practiceType);

    /**
     * 查询资源id由用户
     *
     * @param userId       用户id
     * @param businessType 业务类型
     * @param businessIds  业务id列表
     * @return {@code List<String> }
     */
    List<String> selectResourceIdByUser(@Param("userId") String userId,
                                        @Param("businessType") String businessType,
                                        @Param("businessIds") List<String> businessIds);

    /**
     * 获取用户课程学习数据
     *
     * @param userId
     * @param lessonIds 课时id列表
     * @return
     */
    StudyRecord selectRecordByUser(
            @Param("userId") String userId, @Param("lessonIds") List<String> lessonIds);

    /**
     * 获取用户实验报告
     *
     * @param userId
     * @param lessonIds 课时id列表
     * @return
     */
    Integer selectReportCount(
            @Param("userId") String userId, @Param("lessonIds") List<String> lessonIds);

    /**
     * 获取用户练习完成排名、得分、数量
     *
     * @param userId
     * @param lessonIds 课时id列表
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
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @param lessonIds 课时id列表
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
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @param lessonIds 课时id列表
     * @return
     */
    List<Note> selectReportByTime(
            @Param("userId") String userId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("lessonIds") List<String> lessonIds);

    /**
     * 查询报告平均由时间
     *
     * @param userId    用户id
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @param lessonIds 课时id列表
     * @return {@code List<NoteEvaluate> }
     */
    List<NoteEvaluate> selectReportAverageByTime(
            @Param("userId") String userId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime,
            @Param("lessonIds") List<String> lessonIds);

    /**
     * 查询报告评估由时间
     *
     * @param noteIds   笔记id列表
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @return {@code List<NoteEvaluate> }
     */
    List<NoteEvaluate> selectReportEvaluationByTime(@Param("noteIds") List<String> noteIds,
			@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 查询课时由认证
     *
     * @param courseIds 课程id列表
     * @return {@code List<String> }
     */
    List<String> selectLessonByAuth(@Param("courseIds") List<String> courseIds);

    /**
     * 查询课时由管理
     *
     * @param lessonType 课时类型
     * @return {@code List<String> }
     */
    List<String> selectLessonByAdmin(@Param("lessonType") Integer lessonType);

    /**
     * 查询exp （英文）课时由认证
     *
     * @param courseIds 课程id列表
     * @return {@code List<String> }
     */
    List<String> selectExpLessonByAuth(@Param("courseIds") List<String> courseIds);

    /**
     * 查询实操练习由认证
     *
     * @return {@code List<String> }
     */
    List<String> selectOperationPracticeByAuth();

    /**
     * 查询课后练习由认证
     *
     * @param lessonIds 课时id列表
     * @return {@code List<String> }
     */
    List<String> selectAfterclassPracticeByAuth(@Param("lessonIds") List<String> lessonIds);

    /**
     * 查询理论练习由认证
     *
     * @return {@code List<String> }
     */
    List<String> selectTheoryPracticeByAuth();

    /**
     * 查询公共课程
     *
     * @param courseIds 课程id列表
     * @return {@code List<String> }
     */
    List<String> selectPublicCourse(@Param("courseIds") List<String> courseIds);

}
