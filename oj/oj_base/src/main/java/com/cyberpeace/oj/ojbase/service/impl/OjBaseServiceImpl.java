package com.cyberpeace.oj.ojbase.service.impl;

import com.cyberpeace.oj.ojbase.constant.DumpModule;
import com.cyberpeace.oj.ojbase.service.DumpDataStatistics;
import com.cyberpeace.oj.ojbase.service.OJBaseService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class OjBaseServiceImpl implements OJBaseService {

	public static Map<DumpModule, DumpDataStatistics> register = Maps.newHashMap();

	@Override
	public String rMCourseDump(String resource_id) {
		String file = "";
		for (Map.Entry<DumpModule, DumpDataStatistics> entry : register.entrySet()) {
			DumpDataStatistics value = entry.getValue();
			file = value.getCourseFile(resource_id);

		}
		return file;
	}

}
