package com.cyberpeace.cr.tool.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前台-资源管理-工具库-分类管理")
@RestController
@RequestMapping("cr/tool/category")
@RequiresAuthentication
public class WebToolCategoryController {

    @Autowired
    private ToolCategoryService toolCategoryService;

    @GetMapping("/list")
    @ApiOperation("工具分类列表")
    @RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
    public BaseRsp<List<WebToolCategoryRsp>> getList(
            @ApiParam(name = "type", value = "工具类别") String type) {
        List<WebToolCategoryRsp> webToolCategoryRsps = toolCategoryService.webSelectAll(type);

        return new BaseRsp<>(webToolCategoryRsps);
    }

}
