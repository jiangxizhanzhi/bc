package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CourseSlimDumpBo {

	private List<Course> courseList;

	private List<CourseLesson> lessonList;

	private List<LessonConfig> configList;

	private List<PracticeTheoryRef> refList;

}
