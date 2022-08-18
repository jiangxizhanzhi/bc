package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.oj.ojbase.constant.DumpModule;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.service.CourseDumpService;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.service.DumpDataStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class CourseDumpDataStatistics extends DumpDataStatistics {

	@Autowired
	CrRoleMapper crRoleMapper;

	@Autowired
	RbRoomMapper rbRoomMapper;

	@Autowired
	private CourseDumpService courseDumpService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public DumpModule getCode() {
		return DumpModule.COURSE;
	}

	@Override
	public File getCourseFile(String userId) {
		String path = "";
		File file = new File(path);
		UserInfo userInfo = remoteUserService.getById(ResourceImportConstant.SUPER_ADMIN_HASH);
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, false);
		Map<String, String> envReqs = new HashMap<>();
		courseDumpService.rMCourseDump(path, authContext);
		return null;
	}

	@Override
	public File getCtfPracticeFile(String userId) {
		return null;
	}
}
