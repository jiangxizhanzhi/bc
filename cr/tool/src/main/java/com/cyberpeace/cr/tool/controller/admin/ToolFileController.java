package com.cyberpeace.cr.tool.controller.admin;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.service.ToolFileService;
import com.cyberpeace.cs.constant.FileTypeContant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 工具文件控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Slf4j
@Api(tags = "工具附件")
@RestController
@RequestMapping("cr/tools/file")
@RequiresAuthentication
public class ToolFileController {

	/**
	 * 工具文件Service
	 *
	 * @see ToolFileService
	 */
	@Autowired
	private ToolFileService toolFileService;

	/**
	 * 更新工具文件
	 *
	 * @param file 文件
	 * @return {@code BaseRsp<ToolFile> }
	 * @throws
	 * @see MultipartFile
	 * @see BaseRsp<ToolFile>
	 */
	@ApiOperation("上传工具附件")
	@PostMapping("/upload_tool_file")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ToolFile> updateToolFile(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			throw new BusinessException(ErrorCode.EMPTY_FILE);
		}

		// 文件类型校验
		FileUtil.validateFileSuffix(file.getOriginalFilename(), FileTypeContant.TOOL_ATTACHMENT);

		// 限制上传附件大小为500M
		if(file.getSize() > CommonConstant.TOOL_ATTACHMENT_FILE_SIZE){
			log.error("file > 500M");
			throw new BusinessException(ErrorCode.FILE_SIZE_EXCEED_UPPER_LIMIT);
		}

		ToolFile toolFile = toolFileService.saveFile(file);
		return new BaseRsp<>(toolFile);
	}
}
