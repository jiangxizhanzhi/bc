package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.rtks.controller.res.OccupationCourseRes;
import com.cyberpeace.rtks.service.CourseTrainService;
import com.cyberpeace.rtks.service.bo.CourseKnowledgeBo;
import com.cyberpeace.rtks.service.bo.RoleCourseBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "rtks-课程训练相关")
@RestController
@RequestMapping("/course/train")
@RequiresAuthentication
public class CourseTrainController {


    @Autowired
    private CourseTrainService courseTrainService;

    @ApiOperation("课程训练-职业课程课程学习进度")
    @GetMapping("/knowledge")
    public BaseRsp<OccupationCourseRes> courseKnowledge() {
        String userId = UserInfoContext.getUserContext().getUserId();
        String roleId = UserInfoContext.getUserContext().getRoleId();
        List<CourseKnowledgeBo> courseKnowledges = courseTrainService.getCourseKnowledge(userId,
                roleId);
        return new BaseRsp<>(calculatePercent(courseKnowledges));
    }

    /**
     * 计算进度
     * @param courseKnowledges
     * @return
     */
    private OccupationCourseRes calculatePercent(List<CourseKnowledgeBo> courseKnowledges) {

        OccupationCourseRes courseRes = new OccupationCourseRes();
        // 计算理论课时进度
        int theoryCount = courseKnowledges.stream().mapToInt(CourseKnowledgeBo::getTheoryCount)
                .sum();
        int completeTheory = courseKnowledges.stream()
                .mapToInt(CourseKnowledgeBo::getCompleteTheory).sum();
        courseRes.setTheoryPercent(MathUtils.ceilDivide(Double.valueOf(completeTheory * 100),
                Double.valueOf(theoryCount)));

        // 计算实验课时进度
        int exerciseCount = courseKnowledges.stream().mapToInt(CourseKnowledgeBo::getExerciseCount)
                .sum();
        int completeExercise = courseKnowledges.stream()
                .mapToInt(CourseKnowledgeBo::getCompleteExercise).sum();
        courseRes.setExercisePercent(MathUtils.ceilDivide(Double.valueOf(completeExercise * 100),
                Double.valueOf(exerciseCount)));

        // 计算总进度
        int count = courseKnowledges.stream().mapToInt(CourseKnowledgeBo::getLessonCount).sum();
        int completeCount = courseKnowledges.stream().mapToInt(CourseKnowledgeBo::getCompleteCount)
                .sum();
        courseRes.setCoursePercent(
                MathUtils.ceilDivide(Double.valueOf(completeCount * 100), Double.valueOf(count)));

        courseRes.setKnowledgeBos(courseKnowledges);
        return courseRes;
    }

    @ApiOperation("职业路径课程")
    @GetMapping("/role/path")
    public BaseRsp<List<RoleCourseBo>> getRoleCourse() {
        String roleId = UserInfoContext.getUserContext().getRoleId();

        BaseRsp<List<RoleCourseBo>> rsp = new BaseRsp<>();
        if (StringUtils.isNotBlank(roleId)) {
            List<RoleCourseBo> courses = courseTrainService.getRoleCoursePath(roleId);
            rsp.setData(courses);
        }
        return rsp;
    }

}
