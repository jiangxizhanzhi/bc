package com.cyberpeace.oj.person.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.dao.mapper.PersonCenterMapper;
import com.cyberpeace.oj.person.service.PersonCenterService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonCenterServiceImpl extends ServiceImpl<CommentMapper, Comment> implements PersonCenterService {


    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CourseLessonMapper courseLessonMapper;

    @Autowired
    LessonRecordMapper lessonRecordMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    ResourceCollectionMapper resourceCollectionMapper;

    @Autowired
    PersonCenterMapper personCenterMapper;

    @Autowired
    NoteMapper noteMapper;


    @Override
    public IPage<PersonComment> pageComment(Page page, String userId, String parent, AuthContext authContext) {
        List<String> resourceIds = new ArrayList<>();
        if (null != authContext) {
            List<String> courseIds = courseMapper.selectByAuth(authContext);
            resourceIds.addAll(courseIds);
            resourceIds.addAll(personCenterMapper.selectLessonByAuth(courseIds));
            resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
        }
        IPage<PersonComment> iPage = personCenterMapper.selectCommentByUser(page, userId, parent, resourceIds);
        return iPage.convert(this::commentToBo);
    }


    @Override
    public IPage<PersonNote> pageNotes(Page page, String userId, AuthContext authContext) {
        Integer noteType = Note.NoteType.NOTE.getCode();
        List<String> resourceIds = new ArrayList<>();
        if (null != authContext) {
            List<String> courseIds = courseMapper.selectByAuth(authContext);
            resourceIds.addAll(courseIds);
            resourceIds.addAll(personCenterMapper.selectLessonByAuth(courseIds));
            resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
            resourceIds.addAll(personCenterMapper.selectTheoryPracticeByAuth());
            resourceIds.addAll(personCenterMapper.selectAfterclassPracticeByAuth());
        }
        IPage<PersonNote> iPage = personCenterMapper.selectNoteByUser(page, userId, noteType, resourceIds);
        return iPage.convert(this::noteToBo);
    }

    /**
     * @param page
     * @param userId 用户ID
     * @return
     */
    @Override
    public IPage<PersonCollection> pageCollections(Page page, String userId, String type, AuthContext authContext) {
        List<String> resourceIds = new ArrayList<>();
        if (null != authContext) {
            List<String> courseIds = courseMapper.selectByAuth(authContext);
            resourceIds.addAll(courseIds);
            resourceIds.addAll(personCenterMapper.selectLessonByAuth(courseIds));
            resourceIds.addAll(personCenterMapper.selectTheoryPracticeByAuth());
            resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
        }
        return personCenterMapper.selectByUser(page, userId, type, resourceIds);
    }



    @Override
    public BaseRsp<StudyRecord> studyRecords(String userId, AuthContext authContext) {
        List<String> lessonIds = new ArrayList<>();
        List<String> expLessonIds = new ArrayList<>();
        if (null != authContext) {
            List<String> courseIds = courseMapper.selectByAuth(authContext);
            lessonIds = personCenterMapper.selectLessonByAuth(courseIds);

        }
        // 获取用户课程学习数据
        StudyRecord studyRecord = personCenterMapper.selectRecordByUser(userId, lessonIds);
        // 获取用户提交的实验报告数量
        Integer reportCount = personCenterMapper.selectReportCount(userId, expLessonIds);
        studyRecord.setReportCount(reportCount);
        // 获取用户完成练习的排名、得分、数量
        List<String> users = personCenterMapper.selectDistinctUser();
        if (users.contains(userId)){
            StudyRecord practiceRecord = personCenterMapper.selectPracticeByUser(userId, lessonIds);
            studyRecord.setPracticeFinishCount(practiceRecord.getPracticeFinishCount());
            Integer score = practiceRecord.getScore();
            String practiceRanking = practiceRecord.getPracticeRanking();
            studyRecord.setPracticeRanking(practiceRanking);
            studyRecord.setScore(score);
        }
        BaseRsp<StudyRecord> rsp = new BaseRsp<>();
        rsp.setData(studyRecord);
        return rsp;
    }

    @Override
    public BaseRsp<BrokenLine> studyPeriodRecords(String userId, Date startTime, Date endTime, AuthContext authContext) {
        // 资源过滤
        List<String> lessonIds = new ArrayList<>();
        List<String> expLessonIds = new ArrayList<>();
        if (null != authContext) {
            List<String> courseIds = courseMapper.selectByAuth(authContext);
            lessonIds = personCenterMapper.selectLessonByAuth(courseIds);
            expLessonIds = personCenterMapper.selectExpLessonByAuth(courseIds);
        }
        List<LessonRecord> lessonRecord = personCenterMapper.selectLessonByTime(userId, startTime, endTime, lessonIds);
        List<Note> finishReports = personCenterMapper.selectReportByTime(userId, startTime, endTime, expLessonIds);
        int daysBetween = TimeUtil.getDaysBetween(TimeUtil.getStartOfDay(startTime),
                TimeUtil.getStartOfDay(endTime));
        Map<String, List<LessonRecord>> courseRecordMap = lessonRecord.stream().collect(Collectors
                .groupingBy(course -> TimeUtil.format8(course.getCreateTime())));
        Map<String, List<Note>> finishReportMap = finishReports.stream().collect(Collectors
                .groupingBy(finishPractice -> TimeUtil.format8(finishPractice.getCreateTime())));
        List<String> dates = Lists.newArrayList();
        List<Integer> courseRecords = Lists.newArrayList();
        List<Integer> reportRecords = Lists.newArrayList();
        for (int i = 0; i < daysBetween; i++) {
            Date date = TimeUtil.addDates(startTime, i);
            String currentDate = TimeUtil.format8(date);
            dates.add(currentDate);
            List<LessonRecord> courseRecordLists = courseRecordMap.get(currentDate);
            if (courseRecordLists == null || courseRecordLists.size() == 0){
                courseRecords.add(0);
            } else {
                courseRecords.add(courseRecordLists.size());
            }
            List<Note> finishReportLists = finishReportMap.get(currentDate);
            if (finishReportLists == null || finishReportLists.size() == 0){
                reportRecords.add(0);
            } else {
                reportRecords.add(finishReportLists.size());
            }
        }
        BaseRsp<BrokenLine> baseRsp = new BaseRsp<>();
        BrokenLine res = new BrokenLine();
        res.setXNames(dates);
        res.setYFirstValues(courseRecords);
        res.setYSecondValues(reportRecords);
        baseRsp.setData(res);
        return baseRsp;
    }

    /**
     * 数据格式转换
     * @param comment
     * @return
     */
    private PersonComment commentToBo(PersonComment comment) {

        // 获取用户信息
        UserInfo userInfo = remoteUserService.getById(comment.getUserId());
        comment.setUsername(userInfo.getUserName());
        return comment;
    }

    /**
     * 数据格式转换
     * @param note
     * @return
     */
    private PersonNote noteToBo(PersonNote note) {
        UserInfo userInfo = remoteUserService.getById(note.getUserId());
        note.setUserName(userInfo.getUserName());
        return note;
    }
}
