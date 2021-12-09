package com.cyberpeace.cr.tool.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.controller.res.WebToolRsp;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "前台-资源中心-工具库")
@RestController
@RequestMapping("/cr/tools")
@RequiresAuthentication
public class WebToolController {

    @Autowired
    private ToolService toolService;

    @ApiOperation("工具库列表")
    @GetMapping("/list")
    public BaseRsp<ListRsp<WebToolRsp>> getList(
            @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
        Page page = pageReqBase.toPageParam();
        IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, pageReqBase);
        IPage<WebToolRsp> convert = toolBoListRsp.convert(webTool -> BeanUtils.copy(webTool, WebToolRsp.class));
        return new BaseRsp<>(PageUtil.getListResult(convert));
    }

    @ApiOperation("工具详情")
    @GetMapping()
    public BaseRsp<ToolRsp> getDetail(
            @ApiParam(name = "resource_id", value = "查询id", required = true)
            @RequestParam("resource_id") @NotBlank String resourceId) {
        ToolRsp toolRsp = toolService.selectById(resourceId);
        return new BaseRsp<>(toolRsp);
    }

}
