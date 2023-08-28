package com.cyberpeace.gateway.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.gateway.service.bo.ResourceRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 资源统计数据控制器
 *
 * @author tangjingjing
 * @date 2023/08/28
 */
@Api(tags = "资源统计相关api")
@RestController
@RequestMapping("/resource/statistic/")
@RequiresAuthentication
public class ResourceStatisticsController {

	@Autowired
	private ToolMapper toolMapper;

	@Autowired
	private CrConstantsService crConstantsService;

	@GetMapping("")
	@ApiOperation("获取首页资源统计")
	public BaseRsp<List<ResourceRsp>> listDirection() {
		List<ResourceRsp> rsp = new ArrayList<>();
		// 读取常量
		List<CrConstantsConfig> configs = crConstantsService
				.getByType(CrConstantsConfig.ConstantsType.GWDK.getCode());
		String courseCount = "0";
		String adCount = "0";
		for (CrConstantsConfig config : configs) {
			if (config.getCnName().equals("课程数量")) {
				courseCount = config.getEnName();
			}
			if (config.getCnName().equals("赛题数量")) {
				adCount = config.getEnName();
			}
		}
		Integer courseCnt = new Integer(courseCount);
		Integer adCnt = new Integer(adCount);

		Integer toolCnt = toolMapper.selectCount(null);
		ResourceRsp courseRsp = new ResourceRsp();
		ResourceRsp practiceCounts = new ResourceRsp();
		ResourceRsp toolCounts = new ResourceRsp();
		// 课程数量
		courseRsp.setResourceName("course");
		courseRsp.setCount(courseCnt);
		// 赛题数量
		practiceCounts.setResourceName("practice");
		practiceCounts.setCount(adCnt);
		// 工具数量
		toolCounts.setResourceName("tool");
		toolCounts.setCount(toolCnt);
		rsp.add(courseRsp);
		rsp.add(practiceCounts);
		rsp.add(toolCounts);
		return new BaseRsp(rsp);
	}

}
