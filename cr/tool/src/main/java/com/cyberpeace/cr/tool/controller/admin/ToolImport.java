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
			required = true) @RequestParam("path") @NotBlank String path) {
		toolResourceImportService.importResource(path);
		return new BaseRsp<>();
	}

}
