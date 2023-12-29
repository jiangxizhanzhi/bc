package com.cyberpeace.oj.person.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.person.controller.res.PracticeCountRes;
import com.cyberpeace.oj.practice.service.PracticeCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "oj-后台学习监控api")
@RestController
@RequestMapping("/oj/monitor")
public class StudyMonitorController {

	@Autowired
	private PracticeCountService practiceCountService;

	@ApiOperation("习题练习统计")
	@GetMapping("/practice_count")
	public BaseRsp<PracticeCountRes> getPracticeCount() {
		PracticeCountRes practiceCountRes = new PracticeCountRes();
		practiceCountRes.setPeopleCount(practiceCountService.getAllPeopleCount());
		practiceCountRes.setPracticeCount(practiceCountService.getAllPracticeCount());
		if (practiceCountRes.getPeopleCount() == 0){
			practiceCountRes.setAverageCount(0);
		}else {
			practiceCountRes.setAverageCount(practiceCountRes.getPracticeCount()/ practiceCountRes.getPeopleCount());
		}
		return new BaseRsp<>(practiceCountRes);
	}
}
