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
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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
        if(localUser == null || localUser.getOutUserType() == null || !localUser.getOutUserType().equals("soc")){
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
        sendRecordMessage(jsonObject, SOC_COURSE_SEND_URL, WebConstant.POST);
    }

//    public void sendMessage(JSONObject message, String url, String urlType) {
//        log.info("sendMessage:{}", message.toJSONString());
//        if (urlType == null) {
//            return;
//        }
//        // 创建HttpClient
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            // 根据urlType决定请求类型
//            HttpRequestBase request = null;
//            if ("POST".equalsIgnoreCase(urlType)) {
//                // 创建POST请求
//                HttpPost postRequest = new HttpPost(url);
//                postRequest.setHeader("Content-Type", "application/json");
//                // 设置请求体
//                StringEntity entity = new StringEntity(message.toString());
//                postRequest.setEntity(entity);
//                request = postRequest;
//            } else if ("GET".equalsIgnoreCase(urlType)) {
//                // 创建GET请求
//                request = new org.apache.http.client.methods.HttpGet(url);
//            }
//
//            if (request != null) {
//                // 执行请求
//                HttpResponse response = httpClient.execute(request);
//                HttpEntity entity = response.getEntity();
//            } else {
//                throw new IllegalArgumentException("Unsupported URL type: " + urlType);
//            }
//        } catch (Exception e) {
//            log.info("sendMessage error:{}", e.getMessage());
//        }
//    }

    public void sendRecordMessage(JSONObject message, String url, String urlType) {
        log.info("sendMessage: {}", message.toJSONString());
        if (urlType == null || url == null) {
            log.warn("urlType 或 url 参数为空");
            return;
        }

        try (CloseableHttpClient httpClient = buildHttpClient(url)) {
            HttpRequestBase request = createRequest(message, url, urlType);
            if (request == null) {
                throw new Exception("不支持的请求类型: " + urlType);
            }

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                log.info("响应状态码: {}", statusCode);
            }
        } catch (Exception e) {
            log.error("请求失败 - URL: {}, 类型: {}", url, urlType, e);
        }
    }

    // 创建支持 HTTPS 的 HttpClient（带绕过验证逻辑）
    private CloseableHttpClient buildHttpClient(String url) throws Exception {
        if (url.startsWith("https://")) {
            SSLContext sslContext = SSLContexts.custom()
                    .loadTrustMaterial((chain, authType) -> true)
                    .build();
            return HttpClients.custom()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
        } else {
            return HttpClients.createDefault();
        }
    }

    // 创建请求对象
    private HttpRequestBase createRequest(JSONObject message, String url, String urlType) throws Exception {
        if ("POST".equalsIgnoreCase(urlType)) {
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(message.toString()));
            return post;
        } else if ("GET".equalsIgnoreCase(urlType)) {
            return new HttpGet(url);
        }
        return null;
    }

}
