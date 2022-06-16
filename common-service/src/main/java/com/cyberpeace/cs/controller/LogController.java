package com.cyberpeace.cs.controller;

import com.cyberpeace.common.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Api(tags = "后台日志相关api")
@RestController
@RequestMapping("/cs/admin/log")
@RequiresAuthentication
public class LogController {

	@ApiOperation("下载日志")
	@GetMapping("/downloadLog")
	public void download(HttpServletResponse response , @ApiParam(name = "file_name",value = "文件名称",required = true) @RequestParam("file_name") String name){
		String logLootPath = System.getProperty("catalina.base") + "/logs/";
		File file = new File(logLootPath+name);
		FileUtil.setResponse(file,false,response);
	}

}
