package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;

public interface ClassroomGroupInfoService extends IService<ClassroomGroupInfo> {

	/**
	 * 查询课堂场景 前台使用后台直接返回为空
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	ClassroomGroupInfo selectByLessonAndUser(String lessonId, AuthContext authContext);

}
