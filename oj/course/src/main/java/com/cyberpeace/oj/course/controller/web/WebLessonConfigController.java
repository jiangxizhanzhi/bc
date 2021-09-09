package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("oj-前台课程目录相关api")
@RestController
@RequestMapping("/web/lessonconfig")
@RequiresAuthentication
public class WebLessonConfigController {

    @Autowired
    private LessonConfigService lessonConfigService;

    @GetMapping("")
    @ApiOperation("课程目录查询")
    public BaseRsp<LessonConfigBo> getLessonConfig(@ApiParam(name = "course_id",
            value = "课程编号") @RequestParam("course_id") String courseId) {

        String userId = UserInfoContext.getUserContext().getUserId();
        LessonConfigBo config = lessonConfigService.getLessonConfig(courseId, userId, true);
        return new BaseRsp<>(config);
    }

}
