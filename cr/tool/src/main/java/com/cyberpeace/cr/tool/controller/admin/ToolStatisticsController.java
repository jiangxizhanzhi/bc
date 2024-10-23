package com.cyberpeace.cr.tool.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.tool.controller.res.ToolTypeCount;
import com.cyberpeace.cr.tool.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "工具库统计")
@RestController
@RequestMapping("/cr/admin/tools/statistics")
public class ToolStatisticsController {

	@Autowired
	private ToolService toolService;

	@ApiOperation("工具统计")
	@GetMapping("")
	public BaseRsp<List<ToolTypeCount>> statistics() {
		return new BaseRsp<>(toolService.statistics());
	}
}
