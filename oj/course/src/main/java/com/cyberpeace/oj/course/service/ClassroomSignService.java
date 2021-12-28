package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;

public interface ClassroomSignService extends IService<ClassroomSign> {

	/**
	 * 课堂签到
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	Boolean signIn(String classroomId, AuthContext authContext);
}
