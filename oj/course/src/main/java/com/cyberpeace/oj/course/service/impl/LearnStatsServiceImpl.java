package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.LearnStatsMapper;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.PersonalStatsDetail4CountBo;
import com.cyberpeace.oj.course.service.bo.PersonalStatsDetail4CourseBo;
import com.cyberpeace.oj.course.service.bo.UserStatsBo;
import com.cyberpeace.oj.course.service.bo.UserStatsRankBo;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LearnStatsServiceImpl implements LearnStatsService {

    @Autowired
    LearnStatsMapper learnStatsMapper;

    @Autowired
    RemoteUserService remoteUserService;

    @Override
    public ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization) {
        JSONObject result;
        OrderItem orderField = (OrderItem) page.getOrders().get(0);

        if (orderField.getColumn() == null) {
            result = remoteUserService.getList(page.getCurrent(), page.getSize(), pattern, organization);
        } else {
            result = remoteUserService.getList(null, 0L, pattern, organization);
        }
        List<UserInfo> userInfos = result.getJSONArray("list").toJavaList(UserInfo.class);
        if (userInfos.isEmpty()) {
            return new ListRsp<>();
        }

        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());
        Map<String, UserStatsBo> userStatsMap = learnStatsMapper.userStatsSelect(
                userIds, Note.NoteType.REPORT.getCode()
        ).stream().collect(Collectors.toMap(UserStatsBo::getResourceId, userStatsBo -> userStatsBo));

        List<UserStatsBo> userStatsBos = Lists.newArrayList();
        userInfos.forEach(item -> {
            UserStatsBo userStatsBo = new UserStatsBo();

            if (userStatsMap.get(item.getUserId()) != null) {
                BeanUtils.copyProperties(userStatsMap.get(item.getUserId()), userStatsBo);
            }
            userStatsBo.setResourceId(item.getUserId());
            userStatsBo.setUsername(item.getUserName());
            userStatsBo.setName(item.getName());
            userStatsBo.setOrganizationData(item.getOrganizationData());
            userStatsBo.setRoles(item.getRoles());

            userStatsBos.add(userStatsBo);
        });

        ListRsp<UserStatsBo> responseData = new ListRsp<>();
        // 分页
        if (orderField.getColumn() == null) {
            responseData.setList(userStatsBos);
        } else {
            LocalStringUtil.getMatchStr(orderField.getColumn(), PatternConstant.UNDER_LINE_LOWERCASE).forEach(
                    key -> orderField.setColumn(orderField.getColumn().replace(key, key.toUpperCase().substring(1)))
            );

            Comparator<UserStatsBo> userStatsBoComparator = Comparator.comparing(
                    item -> Float.valueOf(ObjUtil.getFieldValueByName(orderField.getColumn(), item).toString())
            );
            if (!orderField.isAsc()) {
                userStatsBoComparator = userStatsBoComparator.reversed();
            }

            userStatsBos.sort(userStatsBoComparator);
            responseData.setList(userStatsBos.stream().skip((page.getCurrent() - 1) * page.getSize()).limit(page.getSize()).collect(Collectors.toList()));
        }

        responseData.setTotal(result.getInteger("total"));
        responseData.setCurrentPage(page.getCurrent());
        responseData.setLastPage(MathUtils.ceil(responseData.getTotal() * 1.0 / page.getSize()));

        return responseData;
    }

    @Override
    public UserStatsRankBo getUserLearnRank() {
        List<UserInfo> userInfos  = remoteUserService.getList(
                null, 0L, null, null
        ).getJSONArray("list").toJavaList(UserInfo.class);

        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());
        Map<String, String> usernameMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getUserName));

        UserStatsRankBo userStatsRankBo = new UserStatsRankBo();

        // 课时学习排行
        userStatsRankBo.setLessonRecordUser(Lists.newArrayList());
        userStatsRankBo.setLessonRecordCount(Lists.newArrayList());
        learnStatsMapper.lessonRecordRank(userIds).forEach(item -> {
            userStatsRankBo.getLessonRecordUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getLessonRecordCount().add(item.getInteger("count"));
        });

        // 完成报告排行
        userStatsRankBo.setFinishReportUser(Lists.newArrayList());
        userStatsRankBo.setFinishReportCount(Lists.newArrayList());
        learnStatsMapper.reportFinishRank(userIds, Note.NoteType.REPORT.getCode()).forEach(item -> {
            userStatsRankBo.getFinishReportUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getFinishReportCount().add(item.getInteger("count"));
        });

        // 完成课后练习排行
        userStatsRankBo.setFinishPracticeUser(Lists.newArrayList());
        userStatsRankBo.setFinishPracticeCount(Lists.newArrayList());
        learnStatsMapper.practiceFinishRank(userIds).forEach(item -> {
            userStatsRankBo.getFinishPracticeUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getFinishPracticeCount().add(item.getInteger("count"));
        });

        return userStatsRankBo;
    }

    @Override
    public PersonalStatsDetail4CountBo getPersonalStatsDetail4Count(String resourceId) {
        PersonalStatsDetail4CountBo result = new PersonalStatsDetail4CountBo();

        // 课程总数
        JSONObject courseNumStats = learnStatsMapper.courseNumStats(CourseLesson.LessonType.THEORY.getCode(), CourseLesson.LessonType.EXPERIMENT.getCode());
        result.setCourseTotalCount(courseNumStats.getInteger("course_count"));
        result.setTheoryCourseTotalCount(courseNumStats.getInteger("theory_count"));
        result.setExpCourseTotalCount(courseNumStats.getInteger("exp_count"));

        // 已学习课程数量
        JSONObject learnNumStats = learnStatsMapper.learnNumStats(resourceId, CourseLesson.LessonType.THEORY.getCode(), CourseLesson.LessonType.EXPERIMENT.getCode());
        result.setFinishCoursesCount(learnNumStats.getInteger("finish_course_count"));
        result.setFinishTheoryCount(learnNumStats.getInteger("finish_theory_count"));
        result.setFinishExpCount(learnNumStats.getInteger("finish_exp_count"));

        // 课后练习总数、已完成数量
        JSONObject practiceNumStats = learnStatsMapper.practiceNumStats(resourceId, Paper.PaperType.LESSON.getCode());
        result.setAfterClassPracticeTotalCount(practiceNumStats.getInteger("practice_count"));
        result.setAfterClassPractice(practiceNumStats.getInteger("finish_practice_count"));

        return result;
    }

    @Override
    public ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(Page page, String resourceId) {
        return PageUtil.getListResult(
                learnStatsMapper.learnCourseStats(
                        page, resourceId, CourseLesson.LessonType.THEORY.getCode(), CourseLesson.LessonType.EXPERIMENT.getCode(), Note.NoteType.REPORT.getCode()
                )
        );
    }
}
