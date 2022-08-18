package com.cyberpeace.oj.ojbase.service.impl;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cs.constant.DumpModule;
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
	public File rMCourseDump(String path, Map<String, String> envReqs, AuthContext authContext) {
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

//class TrainingComparator implements Comparator<TrainingData> {
//
//	private final Boolean asc;
//
//	public TrainingComparator(Boolean asc) {
//		this.asc = asc;
//	}
//
//	@Override
//	public int compare(TrainingData o1, TrainingData o2) {
//		if (asc) {
//			return (int) (o1.getStartTime().getTime() - o2.getStartTime().getTime());
//		} else {
//			return (int) (o2.getStartTime().getTime() - o1.getStartTime().getTime());
//		}
//	}
//}
