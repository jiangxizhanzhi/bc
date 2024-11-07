package com.cyberpeace.cr.tool.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Web 工具类别控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "前台-资源管理-工具库-分类管理")
@RestController
@RequestMapping("cr/tool/category")
@RequiresAuthentication
public class WebToolCategoryController {

    /**
     * 工具类别Service
     */
    @Autowired
    private ToolCategoryService toolCategoryService;

    /**
     * 获取列表
     *
     * @param pageReqBase 分页请求体基础
     * @return {@code BaseRsp<List<WebToolCategoryRsp>> }
     */
    @GetMapping("/list")
    @ApiOperation("工具分类列表")
    @RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
    public BaseRsp<List<WebToolCategoryRsp>> getList(
            @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
        List<WebToolCategoryRsp> webToolCategoryRsps = toolCategoryService.webSelectAll();

        return new BaseRsp<>(webToolCategoryRsps);
    }

}
