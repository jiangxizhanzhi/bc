package com.cyberpeace.demo.ws;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.model.ResourceRsp;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "资源统计相关api")
@RestController
@RequestMapping("/resource/statistic/")
@RequiresAuthentication
public class ResourceStatisticsController {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private RemoteAdService remoteAdService;

	@GetMapping("")
	@ApiOperation("获取首页资源统计")
	public BaseRsp<List<ResourceRsp>> listDirection() {
		List<ResourceRsp> rsp = new ArrayList<>();
		Integer courseCnt = courseMapper.selectCount(null);
		Integer theoryCnt = practiceTheoryMapper.selectCount(null);
		Integer operationCnt = practiceOperationMapper.selectCount(null);
		Integer practiceCnt = theoryCnt + operationCnt;
		Integer adPracticeCnt = remoteAdService.getAdPracticeCnt();
		ResourceRsp courseRsp = new ResourceRsp();
		ResourceRsp practiceCounts = new ResourceRsp();
		courseRsp.setResourceName("course");
		courseRsp.setCount(courseCnt);
		practiceCounts.setCount(adPracticeCnt + practiceCnt);
		practiceCounts.setResourceName("practice");
		rsp.add(courseRsp);
		rsp.add(practiceCounts);
		return new BaseRsp(rsp);
	}

}
