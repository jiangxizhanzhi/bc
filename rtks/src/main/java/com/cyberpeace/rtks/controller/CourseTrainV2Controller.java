package com.cyberpeace.rtks.controller;

import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.rtks.service.CourseTrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Api(tags = "rtks-课程训练相关")
@RestController
@RequestMapping("/v1/course/train")
public class CourseTrainV2Controller {

    @Autowired
    private CourseTrainService courseTrainService;


    @ApiOperation("推荐练习")
    @GetMapping("/recommend")
    public BaseRsp<List<CourseLesson>> getRecommend(@ApiParam(value = "查询id") @RequestParam(value = "course_id", required = false) String courseId) {

        BaseRsp<List<CourseLesson>> rsp = new BaseRsp<>();
        List<CourseLesson> courses = courseTrainService.getRecommendLessons(courseId, UserInfoContext.getNewUserContext().getResourceId());

        rsp.setData(courses);
        return rsp;
    }

}
