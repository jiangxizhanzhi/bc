package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.controller.req.CourseStatsReq;
import com.cyberpeace.oj.course.controller.req.LessonStatsReq;
import com.cyberpeace.oj.course.controller.req.UserStatsReq;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "后台-学习统计-个人统计")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/admin/course/personal-stats")
@Slf4j
public class AdminPersonalStatsController {
    // todo  ------- 数据准确性待验证 -------

    @Autowired
    LearnStatsService learnStatsService;

    @ApiOperation("课程统计(弃用)")
    @GetMapping("/course-stats")
    public BaseRsp<ListRsp<CourseStatsBo>> courseStats(CourseStatsReq courseStatsReq) {
        return new BaseRsp<>(learnStatsService.getCourseStats(courseStatsReq.toPageParam(), courseStatsReq.getPattern(), courseStatsReq.getDirection(), courseStatsReq.getDifficult()));
    }

    @ApiOperation("课程-课时统计(弃用)")
    @GetMapping("/lesson-stats")
    public BaseRsp<ListRsp<LessonStatsBo>> lessonStats(LessonStatsReq lessonStatsReq) {
        return new BaseRsp<>(learnStatsService.getLessonStats(lessonStatsReq.toPageParam(), lessonStatsReq.getCourse_id(), lessonStatsReq.getPattern(), lessonStatsReq.getType()));
    }

    @ApiOperation("课程学习排名(弃用)")
    @GetMapping("/course-learn-rank")
    public BaseRsp<CourseStatsRankBo> courseLearnRank() {
        return new BaseRsp<>(learnStatsService.getCourseHeatRank());
    }

    @ApiOperation("个人统计")
    @GetMapping("/person-stats")
    public BaseRsp<ListRsp<UserStatsBo>> personalStats(UserStatsReq userStatsReq) {
        return new BaseRsp<>(learnStatsService.getUserStats(userStatsReq.toPageParam(), userStatsReq.getPattern(), userStatsReq.getOrganization()));
    }

    @ApiOperation("个人统计排名")
    @GetMapping("/person-stats-rank")
    public BaseRsp<UserStatsRankBo> personalStatsRank() {
        return new BaseRsp<>(learnStatsService.getUserLearnRank());
    }

    @ApiOperation("个人课程学习数量统计")
    @GetMapping("/person-stats-detail/count")
    public BaseRsp<PersonalStatsDetail4CountBo> personalStatsDetail4Count(@RequestParam("user_id") String resourceId) {
        return new BaseRsp<>(learnStatsService.getPersonalStatsDetail4Count(resourceId));
    }

    @ApiOperation("个人近30天学习记录(弃用)")
    @GetMapping("/person-stats-detail/month")
    public BaseRsp<PersonalStatsDetail4MouthBo> personalStatsDetail4Month(@RequestParam(value = "user_id", required = false) String resourceId) {
        return new BaseRsp<>(learnStatsService.getPersonalStatsDetail4Mouth(resourceId));
    }

    @ApiOperation("个人课程学习统计")
    @GetMapping("/person-stats-detail/course")
    public BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>> personalStatsDetail4Course(@RequestParam("user_id") String resourceId) {
        return new BaseRsp<>(learnStatsService.getPersonalStatsDetail4Course(resourceId));
    }

}
