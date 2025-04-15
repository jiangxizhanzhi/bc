package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.SocService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class SocServiceImpl implements SocService {

    @Autowired
    private CourseLessonService courseLessonService;

    // 课程上报url
    private String SOC_COURSE_SEND_URL = SystemPropUtil.getProp("SOC_COURSE_SEND_URL");

    // 山东移动接收方CODE
    public static final String CALLER_CODE = "SD";

    // 山东移动课程类型信息接收默认值
    public static final String STUDY_TYPE = "网络攻防";

    @Override
    public void lessonDetailRecordUpload(UserInfo localUser, String courseId, String lessonId) {
        log.info("function lessonDetailRecordUpload start");
        // 查询该课程信息
        CourseInfoBo course = courseLessonService.getCourseInfoByResourceId(courseId);
        // 查询该课程总共发费时间
        Integer trainingTime = courseLessonService.getTrainingTimeByCourseId(courseId, localUser.getUserId());
        log.info("course:{}", course.getName());
        log.info("trainingTime:{}", trainingTime);
        // 上报课程记录
        recordUpload(localUser, course, trainingTime);
    }

    @Data
    class Course {
        private String caller;
        private String courseName;
        private String courseType;
        private String messageSn;
        private String pathName;
        private String studyType;
        private Integer totalLearningTime;
        private String userId;
    }

    public void recordUpload(UserInfo localUser, CourseInfoBo course, Integer trainingTime) {
        if (trainingTime == null || trainingTime == 0) {
            return;
        }
        String categoryName = courseLessonService.getCategoryName(course.getCategoryId());
        // 上报时间
        Course courseRecord = new Course();
        courseRecord.setCaller(CALLER_CODE);
        courseRecord.setCourseName(course.getName());
        courseRecord.setCourseType(categoryName);
        courseRecord.setMessageSn(UUID.randomUUID().toString());
        courseRecord.setPathName(categoryName);
        courseRecord.setStudyType(STUDY_TYPE);
        courseRecord.setTotalLearningTime(trainingTime);
        courseRecord.setUserId(localUser.getUserId());
        JSONObject jsonObject = (JSONObject) JSON.toJSON(courseRecord);
        sendMessage(jsonObject, SOC_COURSE_SEND_URL, WebConstant.POST);
    }

    public void sendMessage(JSONObject message, String url, String urlType) {
        log.info("sendMessage:{}", message.toJSONString());
        if (urlType == null) {
            return;
        }
        if (urlType.equalsIgnoreCase("post")) {
            try {
                JSONObject result = HttpClientUtil.post(url, message, JSONObject.class);
                log.info("sendMessage result:{}", result.toJSONString());
            } catch (Exception e) {
                log.info("Failed to send message to SOC.", e);
            }
        } else {
        }
    }

}
