package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.ClassroomScore;

public interface ClassroomScoreService extends IService<ClassroomScore> {

	Boolean initTaskClassroom(String taskId, String classroomId);

}
