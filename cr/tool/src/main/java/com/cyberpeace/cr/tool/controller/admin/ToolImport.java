/**
 * 这段代码是一个Spring Boot控制器类，用于处理与工具资源导入相关的HTTP请求。以下是详细的分析和总结： 
 * ### 类定义和注解 
 * - **类名**：`ToolImport` 
 * - **包名**：`com.cyberpeace.cr.tool.controller.admin` 
 * - **注解**： 
 * - `@RestController`：表明这是一个RESTful风格的控制器。 
 * - `@RequestMapping("/cr/resource")`：设置该控制器处理的所有请求的基准URL为`/cr/resource`。 
 * ### 成员变量 
 * - **成员变量**：`private ToolResourceImportService toolResourceImportService;` 
 * - **注解**： 
 * - `@Autowired`：表示该服务由Spring容器自动装配。 
 * ### 方法 
 * #### 方法1：`importTool` 
 * - **路径**：`/cr/resource/tool/import` 
 * - **HTTP方法**：`GET` 
 * - **参数**： 
 * - `path`：存放服务器路径（必填） 
 * - `type`：工具类型（必填），可以是`probe`、`attack`、`control`或`defense` 
 * - **返回值**：`BaseRsp<String>`，表示操作结果 
 * - **功能**：调用`toolResourceImportService.importResource(path, type)`方法来导入工具资源。 
 * #### 方法2：`importTool2` 
 * - **路径**：`/cr/resource/tool/import2` 
 * - **HTTP方法**：`GET` 
 * - **参数**： 
 * - `excelPath`：Excel文件路径（必填） 
 * - `toolsPath`：工具文件路径（必填） 
 * - `publicPhotoPath`：公共图片路径（必填） 
 * - **返回值**：`BaseRsp<String>`，表示操作结果 
 * - **功能**：调用`toolResourceImportService.importResource2(excelPath, toolsPath, publicPhotoPath)`方法来导入工具资源。 
 * ### 总结 
 * - **用途**：这个控制器类主要用于处理工具资源的导入请求。 
 * - **功能**：提供了两个导入工具资源的方法，分别通过不同的路径和参数进行处理。 
 * - **框架**：使用了Spring Boot框架，并且集成了Swagger文档注解（`io.swagger.annotations`）来生成API文档。 
 * - **输入验证**：使用了`@NotBlank`注解来确保所有必填参数不为空。 
 * ### 改进建议 
 * - **异常处理**：建议增加异常处理逻辑，以便更好地处理可能发生的错误情况。 
 * - **安全性**：考虑增加身份验证和授权机制，确保只有授权用户才能访问这些接口。 
 * - **日志记录**：增加日志记录功能，以便于调试和监控系统运行状态。 
**/
package com.cyberpeace.cr.tool.controller.admin;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.tool.service.ToolResourceImportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 工具导入
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "资源库导入")
@RestController
@RequestMapping("/cr/resource")
public class ToolImport {

	/**
	 * 工具资源导入Service
	 */
	@Autowired
	private ToolResourceImportService toolResourceImportService;

	/**
	 * 导入工具
	 *
	 * @param path 路径
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("工具导入")
	@GetMapping("tool/import")
	public BaseRsp<String> importTool(@ApiParam(name = "path", value = "存放服务器路径",
			required = true) @RequestParam("path") @NotBlank String path,
									  @ApiParam(name = "type", value = "类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类",
		    required = true) @RequestParam("type") @NotBlank String type) {

		toolResourceImportService.importResource(path, type);
		return new BaseRsp<>();
	}

	@ApiOperation("工具导入2")
	@GetMapping("tool/import2")
	public BaseRsp<String> importTool2(@ApiParam(name = "excelPath", value = "excel文件路径",
			required = true) @RequestParam("excelPath") @NotBlank String excelPath,
									   @ApiParam(name = "toolsPath", value = "工具文件路径",
			required = true) @RequestParam("toolsPath") @NotBlank String toolsPath,
									   @ApiParam(name = "publicPhotoPath", value = "公共图片路径",
			required = true) @RequestParam("publicPhotoPath") @NotBlank String publicPhotoPath) {

		toolResourceImportService.importResource2(excelPath, toolsPath, publicPhotoPath);
		return new BaseRsp<>();
	}
}
