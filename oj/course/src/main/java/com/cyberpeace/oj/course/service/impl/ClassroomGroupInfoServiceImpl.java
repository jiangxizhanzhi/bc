package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassroomGroupInfoServiceImpl extends ServiceImpl<ClassroomGroupInfoMapper, ClassroomGroupInfo> implements ClassroomGroupInfoService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Override
	public ClassroomGroupInfo selectByLessonAndUser(String lessonId, AuthContext authContext) {
		if (authContext.getIsWeb()) {
			// 查询课堂信息
			Classroom classroom = classroomMapper
					.selectByOrgAndLesson(authContext.getOrganization(), lessonId);
			if (null == classroom) {
				return null;
			}

			// 查询分组信息
			ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
					.selectByRoomAndUser(classroom.getResourceId(), authContext.getUserId());
			if (null == groupInfo) {
				return null;
			}

			return groupInfo;
		}

		return null;
	}
}
