package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.oj.course.service.CourseDumpService;
import com.cyberpeace.oj.ojbase.constant.DumpModule;
import com.cyberpeace.oj.ojbase.service.DumpDataStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CourseDumpDataStatistics extends DumpDataStatistics {

	@Autowired
	CrRoleMapper crRoleMapper;

	@Autowired
	RbRoomMapper rbRoomMapper;

	@Autowired
	private CourseDumpService courseDumpService;

	@Override
	public DumpModule getCode() {
		return DumpModule.COURSE;
	}

	@Override
	public String getCourseFile(String resource_id, Boolean is_dump_topo) throws IOException {
		return courseDumpService.rMCourseDump(resource_id,is_dump_topo);
	}
}
