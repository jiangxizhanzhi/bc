package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Override
	public List<TimeScheduleBo> listByTime(Date startOfDay, Date endOfDay) {

		List<Classroom> classrooms = classroomMapper.listByTime(startOfDay,
				endOfDay);
		return null;
	}

	@Override
	public List<ClassroomBo> listByTimeAndOrg(Date startOfDay, Date endOfDay,
											  String organization, String createUser) {
		// TODO 获取下级所有组织，等待组织接口
		List<String> organizationList = null;
		List<Classroom> classrooms = classroomMapper.listByTimeAndOrg(startOfDay,
				endOfDay, organizationList, createUser);
		return StreamApiUtils.simpleMap(classrooms, classroom -> {
			ClassroomBo copy = BeanUtils.copy(classroom, ClassroomBo.class);

			String courseId = classroom.getCourseId();
			Course course = courseMapper.selectById(courseId);
			if (course == null) {
				log.error("该课程{}没有课程名", courseId);
				throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FIND);
			}
			copy.setCourseName(course.getName());

			String lessonId = classroom.getLessonId();
			CourseLesson lesson = courseLessonMapper.selectById(lessonId);
			if (lesson == null) {
				log.error("该课时{}没有课程名", lessonId);
				throw new BusinessException(OjBusinessErrorCode.LESSON_NOT_FIND);
			}
			copy.setLessonName(lesson.getName());
			copy.setLessonType(lesson.getType());
			return copy;
		});
	}
}
