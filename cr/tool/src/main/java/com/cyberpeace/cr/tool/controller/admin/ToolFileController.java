package com.cyberpeace.cr.tool.controller.admin;

import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.service.ToolFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Api(tags = "工具附件")
@RestController
@RequestMapping("cr/tools/file")
@RequiresAuthentication
public class ToolFileController {

	@Autowired
	private ToolFileService toolFileService;

	private final static String TOOL_PATH = "tool/";

	@ApiOperation("上传工具附件")
	@PostMapping("/upload_tool_file")
	public BaseRsp<ToolFile> updateToolFile(@RequestParam("file") MultipartFile file)
			throws IOException {
		if (file.isEmpty()) {
			throw new InvalidParamException("上传文件为空");
		}

		String basePath = ConfigUtil.getContextProperty("cr.local.upload.path") + TOOL_PATH;
		File newFile = FileUtil.saveFile(file, basePath);

		ToolFile toolFile = new ToolFile();
		toolFile.setFileName(file.getOriginalFilename());
		toolFile.setSavePath(TOOL_PATH + newFile.getName());
		toolFileService.save(toolFile);
		return new BaseRsp<>(toolFile);
	}
}
