package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.ClassroomScore;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

public interface ClassroomScoreService extends IService<ClassroomScore> {

	Boolean initTaskClassroom(String taskId, String classroomId);

	void setLessonStatus(LessonInfoBo lessonInfoBo);

}
