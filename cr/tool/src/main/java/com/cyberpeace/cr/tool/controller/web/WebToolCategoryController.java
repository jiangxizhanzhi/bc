package com.cyberpeace.cr.tool.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "前台-资源管理-工具库-分类管理")
@RestController
@RequestMapping("cr/tool/category")
@RequiresAuthentication
@RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
public class WebToolCategoryController {

    @Autowired
    private ToolCategoryService toolCategoryService;

    @GetMapping("/list")
    @ApiOperation("工具分类列表")
    public BaseRsp<List<ToolCategory>> getList(
            @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
        List<ToolCategory> courseInfos = toolCategoryService.selectAll();
        return new BaseRsp<>(courseInfos);
    }

}
