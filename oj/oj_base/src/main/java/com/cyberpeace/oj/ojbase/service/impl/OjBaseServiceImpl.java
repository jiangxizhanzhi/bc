package com.cyberpeace.oj.ojbase.service.impl;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.constant.DumpModule;
import com.cyberpeace.oj.ojbase.service.DumpDataStatistics;
import com.cyberpeace.oj.ojbase.service.OJBaseService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
@Slf4j
public class OjBaseServiceImpl implements OJBaseService {

	public static Map<DumpModule, DumpDataStatistics> register = Maps.newHashMap();

	@Override
	public File rMCourseDump(String path, AuthContext authContext) {
		File file = new File("default");
		for (Map.Entry<DumpModule, DumpDataStatistics> entry : register.entrySet()) {
			DumpModule key = entry.getKey();
			if (key == DumpModule.COURSE) {
				DumpDataStatistics value = entry.getValue();
				file = value.getCourseFile(authContext.getUserId());
			} else {
				DumpDataStatistics value = entry.getValue();
				file = value.getCtfPracticeFile(authContext.getUserId());

			}
		}
		return file;
	}

}
