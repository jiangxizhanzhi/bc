package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.service.ClassroomSignService;

import java.util.Date;

@Slf4j
@Service
public class ClassroomSignServiceImpl extends ServiceImpl<ClassroomSignMapper, ClassroomSign> implements ClassroomSignService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	@Override
	public Boolean signIn(String classroomId, AuthContext authContext) {

		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.info("Classroom not found! classroom_id=[{}]", classroomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (!classroom.getOrganizationIds().contains(authContext.getOrganization())) {
			log.error("Current user is not permission! user_org_id=[{}]",
					authContext.getOrganization());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		ClassroomSign sign = ClassroomSign.builder().signIn(CommonConstant.TRUE)
				.classroomId(classroomId).resourceId(IDGeneratorUtil.getID()).signInTime(new Date())
				.userId(authContext.getUserId()).build();
		try {
			classroomSignMapper.insert(sign);
		} catch (DuplicateKeyException e) {
			return true;
		}

		return true;
	}
}
