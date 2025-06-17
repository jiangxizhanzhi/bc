package com.cyberpeace.ai.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.ai.model.dto.CourseHistoryReq;
import com.cyberpeace.ai.model.vo.CourseForFunctionCallVO;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-提供给llm 方法调用的接口")
@RestController
@RequiresAuthentication
@RequestMapping("/ai/function_call")
public class FunctionCallController {

    @Autowired
    private LessonRecordMapper lessonRecordMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;



    @ApiOperation("查询用户的历史课程学习记录")
    @PostMapping("/history/course")
    public BaseRsp<List<CourseForFunctionCallVO>> getHistoryCourse(@RequestBody(required = false) CourseHistoryReq courseHistoryReq) {
        String userId = UserInfoContext.getUserContext().getUserId();
        int count = 1;
        if (Objects.nonNull(courseHistoryReq)) {
            count = courseHistoryReq.getCount();
        }
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "用户认证信息异常，无法获取到用户信息");
        }
        List<LessonRecord> lessonRecords = lessonRecordMapper.selectRecentCoursesByUser(userId, count);
        if (CollectionUtils.isEmpty(lessonRecords)) {
            throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前用户没有任何课程学习记录");
        }

        Map<String, Integer> courseOrderMap = lessonRecords.stream().collect(Collectors.toMap(LessonRecord::getCourseId, lessonRecords::indexOf));



        List<Course> courses = courseMapper.selectList(new LambdaQueryWrapper<Course>().select(Course::getResourceId, Course::getDescription,
                Course::getName, Course::getCategoryId).in(Course::getResourceId, (Object[]) lessonRecords.stream().map(LessonRecord::getCourseId).toArray(String[]::new)));

        if (CollectionUtils.isEmpty(courses)) {
            throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前用户没有任何课程学习记录");
        }
        Map<String, String> directionIdToNameMap = courseCategoryMapper.selectList(new LambdaQueryWrapper<CourseCategory>().select(CourseCategory::getName, CourseCategory::getResourceId)
                .in(CourseCategory::getResourceId, (Object[]) courses.stream().map(Course::getCategoryId).toArray(String[]::new)))
                .stream().collect(Collectors.toMap(CourseCategory::getResourceId, CourseCategory::getName));

        List<CourseForFunctionCallVO> courseForFunctionCallVOS = courses.stream().map(course -> {
            CourseForFunctionCallVO courseForFunctionCallVO = new CourseForFunctionCallVO();
            courseForFunctionCallVO.setResourceId(course.getResourceId());
            courseForFunctionCallVO.setName(course.getName());
            courseForFunctionCallVO.setDescription(course.getDescription());
            courseForFunctionCallVO.setDirection(directionIdToNameMap.get(course.getCategoryId()));
            return courseForFunctionCallVO;
        }).collect(Collectors.toList());

        courseForFunctionCallVOS.sort(Comparator.comparingInt(o -> courseOrderMap.get(o.getResourceId())));

        return new BaseRsp<>(courseForFunctionCallVOS);
    }
}
