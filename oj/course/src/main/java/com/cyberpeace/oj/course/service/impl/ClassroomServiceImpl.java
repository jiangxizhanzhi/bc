package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@Override
	public List<TimeScheduleBo> listByTime(Date startOfDay, Date endOfDay) {

		List<Classroom> classrooms = classroomMapper.listByTime(startOfDay, endOfDay);
		return null;
	}

	@Override
	public Classroom create(Classroom classroom) {
		classroom.setResourceId(IDGeneratorUtil.getID());
		classroomMapper.insert(classroom);
		return classroom;
	}

	@Override
	public List<ClassroomBo> selectPage(ClassRoomListQuery roomListQuery, AuthContext authContext) {

		List<Classroom> classrooms = classroomMapper.selectPage(authContext.getUserId(),
				roomListQuery);

		return StreamApiUtils.map(classrooms, classroom -> entityToBo(classroom));
	}

	@Override
	public List<GroupInfoBo> getGroupInfo(String classroomId, AuthContext authContext) {

		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.error("Classroom not found! classroom_id=[{}]", classroomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}



		// todo
		return null;
	}

	/**
	 * 实体类转换为bo
	 * @param classroom
	 * @return
	 */
	private ClassroomBo entityToBo(Classroom classroom) {

		ClassroomBo classroomBo = BeanUtils.copy(classroom, ClassroomBo.class);

		// 课程数据组装
		Course course = courseMapper.selectById(classroom.getCourseId());
		if (course == null) {
			log.error("Course not found! course_id=[{}]", classroom.getCourseId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		classroomBo.setCourseName(course.getName());
		classroomBo.setCourseType(course.getCourseType());

		// 课时数据组装
		CourseLesson lesson = courseLessonMapper.selectById(classroom.getLessonId());
		if (lesson == null) {
			log.error("Lesson not found! lesson_id=[{}]", classroom.getLessonId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		classroomBo.setLessonName(lesson.getName());
		classroomBo.setLessonType(lesson.getType());

		// 组织信息
		String orgName = remoteOrganizationService.orgNameById(classroom.getOrganizationId());
		classroomBo.setOrganizationName(orgName);
		return classroomBo;
	}

}
