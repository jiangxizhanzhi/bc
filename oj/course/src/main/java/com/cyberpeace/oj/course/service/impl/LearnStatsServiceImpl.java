package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.mapper.LearnStatsMapper;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public ListRsp<CourseStatsBo> getCourseStats(Page page, String pattern, String directionId, Integer difficulty) {
        return PageUtil.getListResult(learnStatsMapper.courseStatsSelect(page, pattern, directionId, difficulty, Note.NoteType.REPORT.getCode(), Note.NoteResourceType.LESSON.getCode()));
    }

    @Override
    public ListRsp<LessonStatsBo> getLessonStats(Page page, String courseId, String pattern, Integer lessonType) {
        return PageUtil.getListResult(learnStatsMapper.lessonStatsSelect(page, courseId, pattern, lessonType, Note.NoteType.REPORT.getCode(), Note.NoteResourceType.LESSON.getCode()));
    }

    @Override
    public CourseStatsRankBo getCourseHeatRank() {
        CourseStatsRankBo courseStatsRankBo = new CourseStatsRankBo();

        // 课程热度排行
        List<String> courseNameLs = Lists.newArrayList();
        List<Integer> courseHeatLs = Lists.newArrayList();
        learnStatsMapper.courseHeatRank().forEach(item -> {
            courseNameLs.add(item.getString("name"));
            courseHeatLs.add(item.getInteger("heat"));
        });
        JSONObject peopleOrderingData = new JSONObject();
        peopleOrderingData.put("course_name", courseNameLs);
        peopleOrderingData.put("heat", courseHeatLs);
        courseStatsRankBo.setPeopleOrderingData(peopleOrderingData);

        // 方向热度排行
        List<String> directionNameLs = Lists.newArrayList();
        List<Integer> directionHeatLs = Lists.newArrayList();
        learnStatsMapper.directionHeatRank().forEach(item -> {
            directionNameLs.add(item.getString("name"));
            directionHeatLs.add(item.getInteger("heat"));
        });
        JSONObject directionOrderingData = new JSONObject();
        directionOrderingData.put("direction_name", directionNameLs);
        directionOrderingData.put("heat", directionHeatLs);
        courseStatsRankBo.setDirectionOrderingData(directionOrderingData);

        return courseStatsRankBo;
    }

    @Override
    public ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization) {
        JSONObject result = remoteUserService.getList(page.getCurrent(), page.getSize(), pattern, organization);
        List<UserInfo> userInfos = result.getJSONArray("list").toJavaList(UserInfo.class);

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
            userStatsBo.setOrganization_data(item.getOrganizationData());
            userStatsBo.setRole(item.getRoles());

            userStatsBos.add(userStatsBo);
        });

        ListRsp<UserStatsBo> responseData = new ListRsp<>();
        responseData.setTotal(result.getInteger("total"));
        responseData.setList(userStatsBos);
        responseData.setCurrentPage(page.getCurrent());
        responseData.setLastPage(MathUtils.ceil(responseData.getTotal() * 1.0 / page.getSize()));

        return responseData;
    }

    @Override
    public UserStatsRankBo getUserLearnRank() {
        List<UserInfo> userInfos = remoteUserService.getList();
        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());
        Map<String, String> usernameMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getUserName));

        UserStatsRankBo userStatsRankBo = new UserStatsRankBo();

        userStatsRankBo.setLessonRecordUser(Lists.newArrayList());
        userStatsRankBo.setLessonRecordCount(Lists.newArrayList());
        learnStatsMapper.lessonRecordRank(userIds).forEach(item -> {
            userStatsRankBo.getLessonRecordUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getLessonRecordCount().add(item.getInteger("count"));
        });

        userStatsRankBo.setFinishReportUser(Lists.newArrayList());
        userStatsRankBo.setFinishReportCount(Lists.newArrayList());
        learnStatsMapper.reportFinishRank(userIds, Note.NoteType.REPORT.getCode()).forEach(item -> {
            userStatsRankBo.getFinishReportUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getFinishReportCount().add(item.getInteger("count"));
        });

        userStatsRankBo.setFinishPracticeUser(Lists.newArrayList());
        userStatsRankBo.setFinishPracticeCount(Lists.newArrayList());

        return userStatsRankBo;
    }

    @Override
    public PersonalStatsDetail4CountBo getPersonalStatsDetail4Count(String resourceId) {
        return new PersonalStatsDetail4CountBo();
    }

    @Override
    public PersonalStatsDetail4MouthBo getPersonalStatsDetail4Mouth(String userId) {
        if (userId == null) {
            userId = UserInfoContext.getUserContext().getUserId();
        }

        // 仅30天日期
        List<String> dateRange = Lists.newArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 30; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
            dateRange.add(0, dateFormat.format(calendar.getTime()));
        }
        Map<String, JSONObject> learnCourse4Mouth = learnStatsMapper.PersonalLearnCourse4Mouth(userId, dateRange);
        Map<String, JSONObject> finishReport4Mouth = learnStatsMapper.PersonalFinishReport4Mouth(userId, dateRange, Note.NoteType.REPORT.getCode());

        List<Integer> learnCourseLs = Lists.newArrayList();
        dateRange.forEach(item -> {
            if (learnCourse4Mouth.containsKey(item)) {
                learnCourseLs.add(learnCourse4Mouth.get(item).getInteger("count"));
            } else {
                learnCourseLs.add(0);
            }
        });
        PersonalStatsDetail4MouthBo result = new PersonalStatsDetail4MouthBo();
        result.setThirtyDays(dateRange);
        result.setLessonLearnRecord(learnCourseLs);
        return result;
    }

    @Override
    public ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(String resourceId) {
        return new ListRsp<>();
    }
}
