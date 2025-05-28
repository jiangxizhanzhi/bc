/**
 * 这段代码是一个Spring Boot控制器类 `ToolFileController`，用于处理与工具文件相关的HTTP请求。以下是对其主要部分的简要分析： 
 * ### 包和导入 
 * - `com.cyberpeace.cr.tool.controller.admin`：该类所在的包。 
 * - 导入了多个常用的Spring框架、Shiro安全框架以及自定义工具类。 
 * ### 类注解 
 * - `@Slf4j`：使用Lombok插件自动生成日志记录器。 
 * - `@RestController`：表示这是一个RESTful风格的控制器。 
 * - `@RequestMapping("cr/tools/file")`：定义了该控制器处理的所有请求的基本路径。 
 * - `@RequiresAuthentication`：需要用户通过身份验证才能访问该控制器下的所有方法。 
 * ### 方法注解 
 * - `@PostMapping("/upload_tool_file")`：定义了一个POST请求的处理器，处理路径为`/cr/tools/file/upload_tool_file`的请求。 
 * - `@RequiresPermissions(MenuConstant.CMS_TOOL)`：需要具有指定权限（`MenuConstant.CMS_TOOL`）才能访问该方法。 
 * ### 方法 
 * - `updateToolFile`：处理上传工具文件的请求。 
 * - 参数：`@RequestParam("file") MultipartFile file`，表示从请求中获取上传的文件。 
 * - 检查文件是否为空，如果为空则抛出业务异常。 
 * - 验证文件后缀名是否符合要求。 
 * - 检查文件大小是否超过500MB，如果超过则抛出业务异常。 
 * - 调用 `toolFileService.saveFile(file)` 方法保存文件，并返回包含工具文件信息的响应对象。 
 * ### 主要功能 
 * - **文件上传**：允许用户上传工具文件，并进行必要的验证和限制。 
 * - **权限控制**：确保只有经过身份验证并且具有相应权限的用户才能访问该接口。 
 * ### 异常处理 
 * - 使用自定义的 `BusinessException` 处理业务逻辑错误。 
 * - 定义了两个具体的错误码 `ErrorCode.EMPTY_FILE` 和 `ErrorCode.FILE_SIZE_EXCEED_UPPER_LIMIT`。 
 * ### 总结 
 * 这个控制器类主要用于处理工具文件的上传操作，包括文件验证、大小限制等，并通过权限控制确保安全性。 
**/
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
 * 工具文件Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Api(tags = "工具附件")
@RestController
@RequestMapping("cr/tools/file")
@RequiresAuthentication
public class ToolFileController {

	/**
	 * 工具文件Service
	 */
	@Autowired
	private ToolFileService toolFileService;

	/**
	 * 更新工具文件
	 *
	 * @param file 文件
	 * @return {@code BaseRsp<ToolFile> }
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
