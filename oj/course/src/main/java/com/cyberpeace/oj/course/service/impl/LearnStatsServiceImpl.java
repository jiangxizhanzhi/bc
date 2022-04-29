package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.OrgInfo;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.LearnStatsMapper;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LearnStatsServiceImpl implements LearnStatsService {

    @Autowired
    LearnStatsMapper learnStatsMapper;

    @Autowired
    RemoteUserService remoteUserService;

    @Autowired
    RemoteOrganizationService remoteOrganizationService;

    @Autowired
    CourseLearnStatisticsService courseLearnStatisticsService;

    @Override
    public ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization, String date_type, boolean isPage) {
        Date endTime = new Date();
        Date startTime = getStartTime(date_type, endTime);
        JSONObject result;
        OrderItem orderField = page.getOrders().isEmpty() ? new OrderItem() : (OrderItem) page.getOrders().get(0);
        result = remoteUserService.getList(null, 0L, pattern, organization);
        List<UserInfo> userInfos = result.getJSONArray("list").toJavaList(UserInfo.class);
        userInfos = userInfos.stream().filter(userInfo -> userInfo.getName() != null && !userInfo.getName().isEmpty()).collect(Collectors.toList());
        if (userInfos.isEmpty()) {
            return new ListRsp<>();
        }

        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());
        Map<String, UserStatsBo> userStatsMap = learnStatsMapper.userStatsSelect(
                userIds, Note.NoteType.REPORT.getCode(), startTime, endTime
        ).stream().collect(Collectors.toMap(UserStatsBo::getResourceId, userStatsBo -> userStatsBo));

        // TODO 获取竞赛的理论赛参加次数排名
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
        if (!isPage){
            ListRsp<UserStatsBo> res = new ListRsp<>();
            res.setList(userStatsBos);
            return res;
        }
        return pagedData(page, result.getInteger("total"), orderField, userStatsBos);
    }

    /**
     * 分页
     * @param page 分页对象
     * @param total 总数
     * @param orderField 排序字段
     * @param userStatsBos 带分页数据
     * @return 分页后的数据
     */
    private <T> ListRsp<T> pagedData(Page page, Integer total, OrderItem orderField, List<T> userStatsBos) {
        ListRsp<T> responseData = new ListRsp<T>();

        if (orderField.getColumn() == null) {
            responseData.setList(userStatsBos);
        } else {
            LocalStringUtil.getMatchStr(orderField.getColumn(), PatternConstant.UNDER_LINE_LOWERCASE).forEach(
                    key -> orderField.setColumn(orderField.getColumn().replace(key, key.toUpperCase().substring(1)))
            );

            Comparator<T> userStatsBoComparator = Comparator.comparing(
                    item -> Float.valueOf(ObjUtil.getFieldValueByName(orderField.getColumn(), item).toString())
            );
            if (!orderField.isAsc()) {
                userStatsBoComparator = userStatsBoComparator.reversed();
            }

            userStatsBos.sort(userStatsBoComparator);
            responseData.setList(userStatsBos.stream().skip((page.getCurrent() - 1) * page.getSize()).limit(page.getSize()).collect(Collectors.toList()));
        }

        responseData.setTotal(total);
        responseData.setCurrentPage(page.getCurrent());
        responseData.setLastPage(MathUtils.ceil(responseData.getTotal() * 1.0 / page.getSize()));
        return responseData;
    }

    @Override
    public UserStatsRankBo getUserLearnRank() {
        int limit = 5;
        List<UserInfo> userInfos  = remoteUserService.getList(
                null, 0L, null, null
        ).getJSONArray("list").toJavaList(UserInfo.class);

        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());
        Map<String, String> usernameMap = userInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getUserName));

        UserStatsRankBo userStatsRankBo = new UserStatsRankBo();

        // 课时学习排行
        userStatsRankBo.setLessonRecordUser(Lists.newArrayList());
        userStatsRankBo.setLessonRecordCount(Lists.newArrayList());
        learnStatsMapper.lessonRecordRank(userIds, limit).forEach(item -> {
            userStatsRankBo.getLessonRecordUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getLessonRecordCount().add(item.getInteger("count"));
        });

        // 完成报告排行
        userStatsRankBo.setFinishReportUser(Lists.newArrayList());
        userStatsRankBo.setFinishReportCount(Lists.newArrayList());
        learnStatsMapper.reportFinishRank(userIds, Note.NoteType.REPORT.getCode(), limit).forEach(item -> {
            userStatsRankBo.getFinishReportUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getFinishReportCount().add(item.getInteger("count"));
        });

        // 完成课后练习排行
        userStatsRankBo.setFinishPracticeUser(Lists.newArrayList());
        userStatsRankBo.setFinishPracticeCount(Lists.newArrayList());
        learnStatsMapper.practiceFinishRank(userIds, limit).forEach(item -> {
            userStatsRankBo.getFinishPracticeUser().add(usernameMap.get(item.getString("user_id")));
            userStatsRankBo.getFinishPracticeCount().add(item.getInteger("count"));
        });

        // TODO　获取竞赛的考试排行

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

    @Override
    public HistogramRes getClassIntegralTopTen(String ranking_type, String orgId) {
        List<UserInfo> userInfos  = remoteUserService.getList(
                null, 0L, null, null
        ).getJSONArray("list").toJavaList(UserInfo.class);
        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());

        Date endTime = new Date();
        Date startTime = DateUtils.addDays(endTime, -30);

        // 课后练习积分情况
        List<JSONObject> afterClassIntegrals = learnStatsMapper.afterClassIntegral(userIds, startTime, endTime);
        Map<String, String> integralAfterClass = getUserWithIntegral(afterClassIntegrals);

        // 课时学习积分情况
        List<JSONObject> lessonIntegrals = learnStatsMapper.lessonIntegral(userIds, startTime, endTime);
        Map<String, String> integralLesson = getUserWithIntegral(lessonIntegrals);

        // TODO 获取竞赛的理论赛的积分数据

        List<String> xNames = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();
        Map<String, Integer> result = new HashMap<>();

        // 个人积分合并
        if (ranking_type.equals("org")) {
            Map<String, String> childrenOrg = collectChildrenOrg();
            Map<String, List<UserInfo>> org_users = userInfos.stream().filter(item -> StringUtils.isNotBlank(item.getOrganizationId())).collect(Collectors.groupingBy(UserInfo::getOrganizationId));
            for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
                String org_id = org.getKey();
                int integral = 0;
                if (org_users.containsKey(org_id)) {
                    List<UserInfo> org_infos = org_users.get(org_id);
                    integral += afterClassIntegrals.stream().filter(tmp -> org_infos.stream().map(UserInfo::getUserId).collect(Collectors.toList()).contains(tmp.getString("user_id"))).mapToInt(s -> Integer.parseInt(s.getString("integral"))).sum();
                    integral += lessonIntegrals.stream().filter(tmp -> org_infos.stream().map(UserInfo::getUserId).collect(Collectors.toList()).contains(tmp.getString("user_id"))).mapToInt(s -> Integer.parseInt(s.getString("integral"))).sum();
                }
                result.put(org.getValue(), integral);
            }

        // 班级积分合并
        } else if (ranking_type.equals("person")) {
            userInfos.forEach(user ->{
                String userId = user.getUserId();
                int integral = 0;
                if(integralAfterClass.containsKey(userId)){
                    integral += Integer.parseInt(integralAfterClass.get(userId));
                }
                if(integralLesson.containsKey(userId)){
                    integral += Integer.parseInt(integralLesson.get(userId));
                }
                result.put(user.getName(),integral);
            });
        }
        ArrayList<Map.Entry<String, Integer>> entries = getTop5ls(result);
        for (Map.Entry<String, Integer> tmp : entries) {
            xNames.add(tmp.getKey());
            yValues.add(tmp.getValue());
        }
        HistogramRes res = new HistogramRes();
        res.setXNames(xNames);
        res.setYValues(yValues);
        return res;
    }

    private ArrayList<Map.Entry<String, Integer>> getTop5ls(Map<String, Integer> result) {
        List<Map.Entry<String,Integer>> ls = new ArrayList<>(result.entrySet());
        ls.sort((o2, o1) -> o1.getValue() - o2.getValue());
        // 获取Top5
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(ls);
        if(ls.size() > 5){
            entries = new ArrayList<>(ls.subList(0, 5));
        }
        return entries;
    }

    @Override
    public UserStatsRankBo getOrgLearnRank() {
        UserStatsRankBo userStatsRankBo = new UserStatsRankBo();
        List<UserInfo> userInfos  = remoteUserService.getList(
                null, 0L, null, null
        ).getJSONArray("list").toJavaList(UserInfo.class);
        Map<String, String> childrenOrg = collectChildrenOrg();
        Map<String, List<UserInfo>> orgUsers = userInfos.stream().filter(item -> StringUtils.isNotBlank(item.getOrganizationId())).collect(Collectors.groupingBy(UserInfo::getOrganizationId));
        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());

        // 课时学习全量排行
        userStatsRankBo.setLessonRecordUser(Lists.newArrayList());
        userStatsRankBo.setLessonRecordCount(Lists.newArrayList());
        List<JSONObject> lessons = learnStatsMapper.lessonRecordRank(userIds, null);

        // 完成报告全量排行
        userStatsRankBo.setFinishReportUser(Lists.newArrayList());
        userStatsRankBo.setFinishReportCount(Lists.newArrayList());
        List<JSONObject> reports = learnStatsMapper.reportFinishRank(userIds, Note.NoteType.REPORT.getCode(), null);

        // 完成课后练习全量排行
        userStatsRankBo.setFinishPracticeUser(Lists.newArrayList());
        userStatsRankBo.setFinishPracticeCount(Lists.newArrayList());
        List<JSONObject> afterClassPractices = learnStatsMapper.practiceFinishRank(userIds, null);

        // TODO 获取竞赛的理论赛参加次数排名

        Map<String, Integer> lessonMap = new HashMap<>();
        Map<String, Integer> reportMap = new HashMap<>();
        Map<String, Integer> afterClassMap = new HashMap<>();
        Map<String, Integer> examMap = new HashMap<>();
        for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
            String org_id = org.getKey();
            int org_lessons = 0;
            int org_reports = 0;
            int org_practices = 0;
            if (orgUsers.containsKey(org_id)) {
                List<UserInfo> org_infos = orgUsers.get(org_id);
                org_lessons = lessons.stream().filter(tmp -> org_infos.stream().map(UserInfo::getUserId).collect(Collectors.toList()).contains(tmp.getString("user_id"))).mapToInt(s -> Integer.parseInt(s.getString("count"))).sum();
                org_reports = reports.stream().filter(tmp -> org_infos.stream().map(UserInfo::getUserId).collect(Collectors.toList()).contains(tmp.getString("user_id"))).mapToInt(s -> Integer.parseInt(s.getString("count"))).sum();
                org_practices = afterClassPractices.stream().filter(tmp -> org_infos.stream().map(UserInfo::getUserId).collect(Collectors.toList()).contains(tmp.getString("user_id"))).mapToInt(s -> Integer.parseInt(s.getString("count"))).sum();
            }
            lessonMap.put(org.getValue(), org_lessons);
            reportMap.put(org.getValue(), org_reports);
            afterClassMap.put(org.getValue(), org_practices);
        }
        ArrayList<Map.Entry<String, Integer>> lessonEntries = getTop5ls(lessonMap);
        ArrayList<Map.Entry<String, Integer>> reportEntries = getTop5ls(reportMap);
        ArrayList<Map.Entry<String, Integer>> afterClassEntries = getTop5ls(afterClassMap);
        ArrayList<Map.Entry<String, Integer>> examEntries = getTop5ls(examMap);
        for(int i = 0; i < lessonEntries.size(); i++) {
            Map.Entry<String, Integer> tmpLesson = lessonEntries.get(i);
            Map.Entry<String, Integer> tmpReport = reportEntries.get(i);
            Map.Entry<String, Integer> tmpAfter = afterClassEntries.get(i);
//            Map.Entry<String, Integer> tmpExam = examEntries.get(i);
            userStatsRankBo.getLessonRecordUser().add(tmpLesson.getKey());
            userStatsRankBo.getLessonRecordCount().add(tmpLesson.getValue());
            userStatsRankBo.getFinishReportUser().add(tmpReport.getKey());
            userStatsRankBo.getFinishReportCount().add(tmpReport.getValue());
            userStatsRankBo.getFinishPracticeUser().add(tmpAfter.getKey());
            userStatsRankBo.getFinishPracticeCount().add(tmpAfter.getValue());
//            userStatsRankBo.getAttendExamUser().add(tmpExam.getKey());
//            userStatsRankBo.getAttendExamCount().add(tmpExam.getValue());
        }
        return userStatsRankBo;
    }



    private Map<String, String> collectChildrenOrg() {
        List<OrgInfo> orgInfos = remoteOrganizationService.classList().toJavaList(OrgInfo.class);
        Map<String, String> childrenOrg = new HashMap<>();
        getAllChildrenOrg(orgInfos, childrenOrg);
        return childrenOrg;
    }

    private Map<String, String> getUserWithIntegral(List<JSONObject> afterClassIntegrals) {
        return StreamApiUtils.toMap(afterClassIntegrals, afterClassIntegral -> afterClassIntegral.getString("user_id"),
                afterClassIntegral -> afterClassIntegral.getString("integral"));
    }

    private void getAllChildrenOrg(List<OrgInfo> orgInfos, Map<String, String> childrenOrg){

        for (OrgInfo org:orgInfos){
            List<OrgInfo> children = org.getChildren();
            if(children.size() == 0){
                childrenOrg.put(org.getId(), org.getPath());
            } else {
                getAllChildrenOrg(children, childrenOrg);
            }
        }
    }

    @Override
    public ListRsp<OrgStatsBo> getOrgStatsData(Page page, String pattern, String organization, String date_type) {
        // 获取并校验时间参数 month取当月起往前两年的时间区间 day取当日起往前30天的时间区间
        Date endTime = new Date();
        Date startTime = getStartTime(date_type, endTime);

        // 获取组织、人名模糊查询得到的用户
        if (organization == null || organization.isEmpty()){
            organization = null;
        }
        List<UserInfo> userInfos = remoteUserService.getList(
                null, 0L, pattern, organization
        ).getJSONArray("list").toJavaList(UserInfo.class);
        List<String> userIds = userInfos.stream().map(UserInfo::getUserId).collect(Collectors.toList());

        if (userIds.size() == 0){
            return PageUtil.getListResult(null);
        }

        // 获取所有的末梢组织
        Map<String, String> childrenOrg = collectChildrenOrg();
        Map<String, List<UserInfo>> org_users = userInfos.stream().filter(item -> StringUtils.isNotBlank(item.getOrganizationId())).collect(Collectors.groupingBy(UserInfo::getOrganizationId));


        List<OrgStatsBo> result = Lists.newArrayList();
        List<UserStatsBo> userStatsBos = learnStatsMapper.userStatsSelect(userIds, Note.NoteType.REPORT.getCode(), startTime, endTime);
        for (Map.Entry<String, String> org : childrenOrg.entrySet()) {
            String org_id = org.getKey();
            List<UserStatsBo> collect = new ArrayList<>();
            int orgUserCount = 0;
            if(org_users.containsKey(org_id)){
                List<UserInfo> org_infos = org_users.get(org_id);
                collect = userStatsBos.stream().filter(tmp -> org_infos.stream().map(UserInfo::getUserId).collect(Collectors.toList()).contains(tmp.getResourceId())).collect(Collectors.toList());
                orgUserCount = (int) userInfos.stream().filter(s -> StringUtils.isNotBlank(s.getOrganizationId()) && s.getOrganizationId().equals(org_id)).count();
            }
            result.add(generateObj(collect, org.getValue(), orgUserCount));
        }
        OrderItem orderField = page.getOrders().isEmpty() ? new OrderItem() : (OrderItem) page.getOrders().get(0);

        return pagedData(page, result.size(), orderField, result);
    }


    private OrgStatsBo generateObj(List<UserStatsBo> collect, String org_name, Integer orgUserCount){
        OrgStatsBo res = new OrgStatsBo();
        res.setOrgName(org_name);
        res.setOrgUserCount(orgUserCount);
        res.setFinishPracticeCount(collect.stream().mapToInt(UserStatsBo::getFinishPracticeCount).sum());
        res.setFinishReportCount(collect.stream().mapToInt(UserStatsBo::getFinishReportCount).sum());
        res.setLessonRecordCount(collect.stream().mapToInt(UserStatsBo::getLessonRecordCount).sum());
        // TODO 竞赛理论赛的时间过滤下的数据

        return res;

    }

    private Date getStartTime(String date_type, Date endTime) {
        Date startTime;
        if (null == date_type || date_type.isEmpty() || date_type.equals("month")) {
            startTime = DateUtils.addDays(endTime, -CommonConstant.TWENTY_FOUR_MONTHS);
        } else if (date_type.equals("day")) {
            startTime = DateUtils.addDays(endTime, -CommonConstant.THRITY);
        } else {
            throw new InvalidParamException("incorrect date_type！");
        }
        return startTime;
    }
}
