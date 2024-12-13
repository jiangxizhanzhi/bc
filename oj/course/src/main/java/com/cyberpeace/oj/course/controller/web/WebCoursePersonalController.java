package com.cyberpeace.oj.course.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LearnStatsMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.PersonalStatsDetail4CourseBo;
import com.cyberpeace.oj.course.service.bo.RoleStudyBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "oj-前台课程个人相关api")
@RestController
@RequestMapping("/oj/course/person")
public class WebCoursePersonalController {

	@Autowired
	LearnStatsService learnStatsService;

	@Autowired
	LearnStatsMapper learnStatsMapper;

	@Autowired
	CourseMapper courseMapper;

	@Autowired
	CourseLessonMapper lessonMapper;


	@ApiOperation("最近在学")
	@GetMapping("/course")
	public BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>> personalStatsDetail4Course(
			PageReqBase pageReq) {
		return new BaseRsp<>(
				learnStatsService.getPersonalStatsDetail4Course(pageReq.toPageParam(), UserInfoContext.getUserContext().getUserId()));
	}

	@ApiOperation("课程进度")
	@GetMapping("/course/progress")
	public BaseRsp<RoleStudyBo> getProgress() {
		String userId = UserInfoContext.getUserContext().getUserId();
		RoleStudyBo courseInfo = learnStatsMapper.getCourseInfo(userId);
		List<CourseLesson> finishInfo = learnStatsMapper.getCourseFinishInfo(userId);
		Map<String, List<CourseLesson>> group = StreamApiUtils.groupBy(finishInfo, CourseLesson::getCourseId);

		List<CourseLesson> courseGroupInfo = learnStatsMapper.getCourseGroupInfo();
		Map<String, Integer> map = StreamApiUtils.toMap(courseGroupInfo, CourseLesson::getCourseId, CourseLesson::getType);
		for(String item: group.keySet()){
			List<CourseLesson> courseLessons = group.get(item);
			if(courseLessons.size() == map.get(item)){
				courseInfo.setFinishedCourses(courseInfo.getFinishedCourses() + 1);
			}
		}
		List<Course> courses = courseMapper.selectList(null);
		List<String> collect = courses.stream().map(Course::getResourceId).distinct().collect(Collectors.toList());
		List<CourseLesson> courseLessons = lessonMapper.selectList(null);
		if(!collect.isEmpty()){
			LambdaQueryWrapper<CourseLesson> query = new LambdaQueryWrapper<>();
			query.in(CourseLesson::getCourseId, collect);
			courseLessons = lessonMapper.selectList(query);
		}
		courseInfo.setTotalCourses(courses.size());
		courseInfo.setFinishedCourses(courseInfo.getFinishedCourses());

		courseInfo.setCourseProgress(courseLessons.isEmpty() ? 0 : MathUtils.roundDivide(courseInfo.getCourseProgress()*100,  courseLessons.size()));
		return new BaseRsp<>(courseInfo);
	}

}
