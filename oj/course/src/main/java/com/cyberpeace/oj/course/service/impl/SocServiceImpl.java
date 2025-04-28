package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.course.client.SocClient;
import com.cyberpeace.oj.course.client.SocResponse;
import com.cyberpeace.oj.course.dao.entity.CourseSocCategory;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseSocCategoryService;
import com.cyberpeace.oj.course.service.SocService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.SocCourseBo;
import com.cyberpeace.oj.course.service.bo.SocRequestAuthBo;
import com.cyberpeace.oj.course.service.bo.SocTokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Slf4j
@Service
public class SocServiceImpl implements SocService {

    @Autowired
    private CourseLessonService courseLessonService;


    @Autowired
    private SocClient socClient;



    @Autowired
    private CourseSocCategoryService courseSocCategoryService;

    /**
     * 消息标识前缀
     */
    public static final String MESSAGE_PREFIX = "SD";


    /**
     * 请求认证头
     */
    public static final String SOC_REQUEST_AUTH_HEADER = "Authorization";


    private String SOC_REQUEST_AUTH_URL = SystemPropUtil.getProp("SOC_REQUEST_AUTH_URL");

    /**
     * SOC标识我们的系统id
     */
    private String SOC_AUTH_SYSTEM_ID = SystemPropUtil.getProp("SOC_AUTH_SYSTEM_ID");
    /**
     * SOC AUTH密钥
     */
    private String SOC_AUTH_HASH_CODE = SystemPropUtil.getProp("SOC_AUTH_HASH_CODE");

    // 课程上报url
    private String SOC_COURSE_SEND_URL = SystemPropUtil.getProp("SOC_COURSE_SEND_URL");

    // 山东移动接收方CODE
    private String CALLER_CODE = SystemPropUtil.getProp("SOC_CALLER_CODE");

    // 课程名称前缀，主要用于现场配置soc人员前期测试，后期直接配置空字符串即可
    private String COURSE_NAME_PREFIX = SystemPropUtil.getProp("SOC_COURSE_NAME_PREFIX");

    // 山东移动课程类型信息接收默认值
    public static final String STUDY_TYPE = "网络空间安全基础";


    public static final String COURSE_TYPE = "信息技术基础";

    @Override
    public void lessonDetailRecordUpload(UserInfo localUser, String courseId) {
        log.info("localUser is {}", JSON.toJSONString(localUser));
        if (localUser == null || localUser.getOutUserType() == null || !localUser.getOutUserType().equals("soc")) {
            log.info("UserInfo is not soc user,no need to report!");
            return;
        }
        log.info("function lessonDetailRecordUpload start");
        // 查询该课程信息
        CourseInfoBo course = courseLessonService.getCourseInfoByResourceId(courseId);
        // 查询该课程总共发费时间
        Integer trainingTime = courseLessonService.getTrainingTimeByCourseId(courseId, localUser.getUserId());
        log.info("course:{}", course.getName());
        log.info("trainingTime:{} s", trainingTime);
        // 上报课程记录
        recordUpload(localUser, course, trainingTime);
    }


    /**
     * 课程情况上报
     * @param localUser
     * @param course
     * @param trainingTime
     */
    public void recordUpload(UserInfo localUser, CourseInfoBo course, Integer trainingTime) {
        if (trainingTime == null || trainingTime == 0) {
            return;
        }
        String categoryName = courseLessonService.getCategoryName(course.getCategoryId());
        String token = getAuthInfo();
        if(StringUtils.isEmpty(token)){
            log.warn("get soc request auth token failed!");
            return;
        }
        HashMap<String, String> headers = new HashMap<>();
        headers.put(SOC_REQUEST_AUTH_HEADER,token);
        // 上报时间
        SocCourseBo courseRecord = new SocCourseBo();
        courseRecord.setCaller(CALLER_CODE);
        courseRecord.setCourseName(COURSE_NAME_PREFIX+course.getName());
        List<CourseSocCategory> courseSocCategoryList =
            courseSocCategoryService.lambdaQuery().eq(CourseSocCategory::getCourseId, course.getResourceId()).list();
        if(CollectionUtils.isNotEmpty(courseSocCategoryList)){
            //直接取第一个结果，实际应该只有一个结果匹配
            CourseSocCategory courseSocCategory = courseSocCategoryList.stream().findFirst().get();
            courseRecord.setCourseType(courseSocCategory.getSocThirdCategory());
            courseRecord.setStudyType(courseSocCategory.getSocSecondCategory());
        }else {
            courseRecord.setCourseType(COURSE_TYPE);
            courseRecord.setStudyType(STUDY_TYPE);
        }
        courseRecord.setMessageSn(getMessageSn());
        courseRecord.setPathName(categoryName);
        //上报时间单位为分钟
        courseRecord.setTotalLearningTime(trainingTime/60);
        courseRecord.setUserId(localUser.getAccountId());
        JSONObject jsonObject = (JSONObject) JSON.toJSON(courseRecord);
        log.info("sendCourseRecordMessage start and request:" + jsonObject.toJSONString());
        log.info("sendCourseRecordMessage url:" + SOC_COURSE_SEND_URL + " and type:" + WebConstant.POST);
        String result = sendRecordMessage(jsonObject, headers);
        if(StringUtils.isEmpty(result)){
            log.warn("soc report lesson info response is empty!");
        }
        SocResponse<Object> socTokenInfoSocResponse = JSON.parseObject(result,
                new TypeReference<SocResponse<Object>>() {
                });
        if(Objects.isNull(socTokenInfoSocResponse)){
            log.warn("soc report lesson info response is null!");
            return;
        }
        if(WebConstant.HTTP_200.equals(socTokenInfoSocResponse.getCode())){
            log.info("soc report lesson info success! messageSn is {}",socTokenInfoSocResponse.getMessageSn());
            return;
        }
        log.warn("oc report lesson info failed! error message is {}",socTokenInfoSocResponse.getMessage());
    }

    /**
     * 获取请求鉴权token
     * @return
     */
    private String getAuthInfo() {
        if(StringUtils.isAnyEmpty(SOC_AUTH_SYSTEM_ID,SOC_AUTH_HASH_CODE)){
            log.warn("request auth param is empty!");
            return StringUtils.EMPTY;
        }
        SocRequestAuthBo socRequestAuthBo = SocRequestAuthBo.builder()
                .systemId(SOC_AUTH_SYSTEM_ID)
                .hashCode(SOC_AUTH_HASH_CODE)
                .messageSn(getMessageSn())
                .build();
        String socRequestAuth = getSocRequestAuth(socRequestAuthBo);
        if(StringUtils.isEmpty(socRequestAuth)){
            log.warn("soc auth response if empty!");
            return StringUtils.EMPTY;
        }
        SocResponse<SocTokenInfo> socTokenInfoSocResponse = JSON.parseObject(socRequestAuth,
                new TypeReference<SocResponse<SocTokenInfo>>() {
                });
        //todo 考虑是否需要处理过期时间，默认3600s
        if(Objects.isNull(socTokenInfoSocResponse) || Objects.isNull(socTokenInfoSocResponse.getResult())){
            return StringUtils.EMPTY;
        }
        return socTokenInfoSocResponse.getResult().getToken();
    }

    /**
     * 获取消息序号
     * @return
     */
    private String getMessageSn(){
        return MESSAGE_PREFIX+TimeUtil.format3(new Date())+System.currentTimeMillis();
    }

    /**
     * 获取鉴权token信息
     * @param socRequestAuthBo
     * @return
     */
    private String getSocRequestAuth(SocRequestAuthBo socRequestAuthBo) {
        try {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(socRequestAuthBo);
            return socClient.sendRequestToSocServer(jsonObject, SOC_REQUEST_AUTH_URL, WebConstant.POST,null);
        } catch (Exception e) {
            log.error("get soc request auth info failed!",e);
        }
        return null;
    }

    /**
     * 上报请求发送
     * @param jsonObject
     * @param headers
     * @return
     */
    private String sendRecordMessage(JSONObject jsonObject,Map<String, String> headers) {
        try {
            return socClient.sendRequestToSocServer(jsonObject, SOC_COURSE_SEND_URL, WebConstant.POST,headers);
        } catch (Exception e) {
            log.error("soc report lesson info failed!",e);
        }
        return null;
    }



}
