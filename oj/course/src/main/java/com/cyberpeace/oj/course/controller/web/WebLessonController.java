package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.controller.req.CollectionReq;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "oj-前台课时相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/lesson")
public class WebLessonController {

    @Autowired
    private CourseLessonService courseLessonService;

    @ApiOperation("课时收藏")
    @PostMapping("/collection")
    public BaseRsp<String> collectionLesson(@ApiParam(name = "req", value = "课时收藏信息") @RequestBody CollectionReq req) {

        String userId = UserInfoContext.getUserContext().getUserId();
        String lesson = courseLessonService.collectionLesson(userId, req.getResource(),
                req.getIsCollection());
        return new BaseRsp<>(lesson);
    }

    @ApiOperation("课时详情查询")
    @GetMapping("")
    @ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号"),
            @ApiImplicitParam(name = "course_id", value = "课程编号") })
    public BaseRsp<LessonInfoBo> lessonDetail(@RequestParam("course_id") String courseId,
                                              @RequestParam("lesson_id") String lessonId) {

        String userId = UserInfoContext.getUserContext().getUserId();
        LessonInfoBo infoBo = courseLessonService.getLessonDetail(userId, courseId, lessonId);
        return new BaseRsp<>(infoBo);
    }

}
