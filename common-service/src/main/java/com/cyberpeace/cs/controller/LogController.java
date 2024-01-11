package com.cyberpeace.cs.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.controller.req.LogReq;
import com.cyberpeace.cs.dao.entity.LogEntity;
import com.cyberpeace.cs.service.LogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "后台日志相关api")
@RestController
@RequestMapping("/cs/admin/log")
public class LogController {

	@Resource
	private LogService logService;
	@ApiOperation("下载日志")
	@GetMapping("/downloadLog")
	public void download(HttpServletResponse response,
			@ApiParam(name = "file_name", value = "文件名称",
					required = true) @RequestParam("file_name") String name,
			@ApiParam(name = "module", value = "文件模块编码",
					required = true) @RequestParam("module") Integer module) {

		// 遍历LogEntity.Module获取相应code的枚举值
		String logLootPath = LogEntity.Module.values()[module - 1].getPath();
		File file = new File(logLootPath + name);
		FileUtil.setResponse(file, false, response);
	}

	@ApiOperation("运行日志")
	@GetMapping("/logs")
	public BaseRsp getRunLogs(LogReq logReq){
		return new BaseRsp(logService.getRunLogs(logReq));
	}

	@ApiOperation("模块列表")
	@GetMapping("/modules")
	public BaseRsp getModules(){
		return new BaseRsp(logService.getModules());
	}

}
