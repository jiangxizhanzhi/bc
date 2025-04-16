package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.SocService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
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
    public static final String STUDY_TYPE = "红蓝对抗";

    @Override
    public void lessonDetailRecordUpload(UserInfo localUser, String courseId) {
        if (localUser == null || localUser.getOutUserType() == null || !localUser.getOutUserType().equals("soc")) {
            return;
        }
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
        courseRecord.setUserId(localUser.getAccountId());
        JSONObject jsonObject = (JSONObject) JSON.toJSON(courseRecord);
        log.info("sendCourseRecordMessage start and request:" + jsonObject.toJSONString());
        log.info("sendCourseRecordMessage url:" + SOC_COURSE_SEND_URL + " and type:" + WebConstant.POST);
        sendCourseRecordMessage(jsonObject, SOC_COURSE_SEND_URL, WebConstant.POST);
    }

    public void sendCourseRecordMessage(JSONObject message, String url, String urlType) {
        try {
            // 创建信任管理器
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};

            // 获取默认的SSL上下文
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // 打开HTTPS连接
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();

            // 设置请求方法
            connection.setRequestMethod(urlType);

            // 设置请求头和数据体（仅限非GET请求）
            if (!"GET".equalsIgnoreCase(urlType)) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(message.toString().getBytes(StandardCharsets.UTF_8));
                }
            }

            // 获取响应状态码
            int responseCode = connection.getResponseCode();
            log.info("Response Code: " + responseCode);

            // 读取响应内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 打印响应内容
            log.info("Response Body: " + response);

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            log.info("发送课程记录失败," + e.getMessage());
        }
    }

}
