package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLearnStatisticsMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseLearnStatisticsServiceImpl implements CourseLearnStatisticsService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseLessonMapper courseLessonMapper;

    @Autowired
    private CourseLearnStatisticsMapper courseLearnStatisticsMapper;


    @Override
    public HistogramRes courseHistogram() {
        List<Course> courses = courseLearnStatisticsMapper.selectByHeat();
        return getHistogramRes(courses);
    }

    @Override
    public HistogramRes directionHistogram() {
        List<Course> courses = courseLearnStatisticsMapper.sumHeatByDirection();
        return getHistogramRes(courses);
    }

    private HistogramRes getHistogramRes(List<Course> courses) {
        List<String> xNames = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();
        for (Course course:courses){
            xNames.add(course.getName());
            yValues.add(course.getHeat());
        }
        HistogramRes res = new HistogramRes();
        res.setXNames(xNames);
        res.setYValues(yValues);
        return res;

    }

    @Override
    public ListRsp<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page, AuthContext authContext) {
        List<String> courseIds = null;
        if (null != authContext) {
            courseIds = courseMapper.selectByAuth(authContext);
        }

        IPage<AdminCourseStatisticsRes> adminCourseStatisticsResIPage = courseLearnStatisticsMapper.listCourseByPage(page, context, courseIds, false);
        return PageUtil.getListResult(adminCourseStatisticsResIPage.convert(this::curseStatisticToBo));
    }

    private AdminCourseStatisticsRes curseStatisticToBo(AdminCourseStatisticsRes adminCourseStatisticsRes) {
        UserInfo userInfo = remoteUserService.getById(adminCourseStatisticsRes.getCreateUser());
        if (null == userInfo){
            return null;
        }
        adminCourseStatisticsRes.setCreateUser(userInfo.getUserName());
        return adminCourseStatisticsRes;
    }

    @Override
    public ListRsp<AdminLessonStatisticsRes> LessonStatisticsList(String courseId, Page page, LessonStatisticsListReq pageReq) {
        IPage<AdminLessonStatisticsRes> rsp = courseLearnStatisticsMapper.listLessonByPage(page, courseId, true, pageReq.getType(), pageReq.getSearch());
        return PageUtil.getListResult(rsp);
    }

  @Override
  public ListRsp<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(
      String organizationId, String lessonId, Page page) {
    List<String> userIds = new ArrayList<>();
    if (StringUtils.isNotEmpty(organizationId)) {
      if (organizationId.equals("all")) {
        organizationId = "";
      }
      JSONObject userJsons = remoteUserService.getList(0L, 0L, "", organizationId);
      List<UserInfo> users = userJsons.getJSONArray("list").toJavaList(UserInfo.class);
      userIds = StreamApiUtils.map(users, UserInfo::getUserId);
    }
      IPage<AdminLessonStatisticsDetailRes> basicRes = courseLearnStatisticsMapper.detailLessonByPage(page, lessonId, userIds);
      CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);

      ListRsp<AdminLessonStatisticsDetailRes> basicRsp= PageUtil.getListResult(basicRes);

      if (CourseLesson.LessonType.EXPERIMENT.getCode().equals(courseLesson.getType())){
          List<AdminLessonStatisticsDetailRes> theoryRes = basicRsp.getList();

          List<String> users = StreamApiUtils.map(theoryRes, AdminLessonStatisticsDetailRes::getCreateUser);
          IPage<AdminLessonStatisticsDetailRes> expReportData = courseLearnStatisticsMapper.detailExpLessonByPage(page, lessonId, users);
          ListRsp<AdminLessonStatisticsDetailRes> expRsp = PageUtil.getListResult(expReportData);
          List<AdminLessonStatisticsDetailRes> expRes = expRsp.getList();

          Map<String, AdminLessonStatisticsDetailRes> expMap = expRes.stream().collect(
                  Collectors.toMap(AdminLessonStatisticsDetailRes::getCreateUser, lessonStatisticsDetailRes -> lessonStatisticsDetailRes));
          List<String> theoryUsers = expRes.stream().map(AdminLessonStatisticsDetailRes::getCreateUser).distinct().collect(Collectors.toList());

          theoryRes = theoryRes.stream().map(res -> {
              String createUser = res.getCreateUser();
              UserInfo userInfo = remoteUserService.getById(createUser);
              res.setCreateUser(userInfo.getUserName());
              res.setOrganizationName(userInfo.getOrganizationData());
              if (theoryUsers.contains(createUser)){
                  res.setIsReport(true);
                  AdminLessonStatisticsDetailRes expData = expMap.get(createUser);
                  res.setMarkingTime(expData.getMarkingTime());
                  res.setScore(expData.getScore());
                  res.setReportUpdateTime(expData.getReportUpdateTime());
              }
              return res;
          }).collect(Collectors.toList());
          basicRsp.setList(theoryRes);
          return basicRsp;
      }
      basicRes.convert(this::lessonStatisticToBo);
      return PageUtil.getListResult(basicRes);
  }

    private AdminLessonStatisticsDetailRes lessonStatisticToBo(AdminLessonStatisticsDetailRes detailRes) {
        UserInfo userInfo = remoteUserService.getById(detailRes.getCreateUser());
        detailRes.setCreateUser(userInfo.getUserName());
        detailRes.setOrganizationName(userInfo.getOrganizationData());
        return detailRes;
    }
}
