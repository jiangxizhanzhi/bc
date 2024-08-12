package com.cyberpeace.iov.index.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.iov.index.service.IovIndexService;
import com.cyberpeace.iov.index.service.bo.index.ResourceCountBo;
import com.cyberpeace.iov.index.service.bo.index.RightDemo;
import com.cyberpeace.iov.index.service.bo.index.TaskBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "首页")
@RestController
@RequestMapping("/iov/index/")
@RequiresAuthentication
public class IndexController {

	@Autowired
	private IovIndexService iovIndexService;

	@ApiOperation("首页右侧信息")
	@GetMapping("/right")
	public BaseRsp<RightDemo> getDemo(HttpServletRequest request) {
		RightDemo res = iovIndexService.getDemo(request);
		return new BaseRsp<>(res);
	}

	@ApiOperation("任务列表")
	@GetMapping("/task")
	public BaseRsp<List<TaskBo>> getTaskList() {
		List<TaskBo> res = iovIndexService.getTaskList();
		return new BaseRsp<>(res);
	}

	@ApiOperation("资源统计")
	@GetMapping("/resource")
	public BaseRsp<ResourceCountBo> getResourceCount() {
		ResourceCountBo bo = iovIndexService.getResourceCount();
		return new BaseRsp<>(bo);
	}

}
