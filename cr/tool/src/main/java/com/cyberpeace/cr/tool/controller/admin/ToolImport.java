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
