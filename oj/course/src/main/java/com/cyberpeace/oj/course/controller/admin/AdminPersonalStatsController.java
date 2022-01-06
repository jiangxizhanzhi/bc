package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.course.controller.req.CourseStatsReq;
import com.cyberpeace.oj.course.controller.req.LessonStatsReq;
import com.cyberpeace.oj.course.controller.req.UserStatsReq;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "后台-学习统计-个人统计")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/admin/course/personal-stats")
@Slf4j
public class AdminPersonalStatsController {
    // todo  ------- 数据准确性待验证 -------

    @Autowired
    LearnStatsService learnStatsService;

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

    @ApiOperation("个人课程学习统计")
    @GetMapping("/person-stats-detail/course")
    public BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>> personalStatsDetail4Course(PageReqBase pageReq, @ApiParam("用户resource_id") @RequestParam(value = "user_id") String userId) {
        return new BaseRsp<>(learnStatsService.getPersonalStatsDetail4Course(pageReq.toPageParam(), userId));
    }

}
