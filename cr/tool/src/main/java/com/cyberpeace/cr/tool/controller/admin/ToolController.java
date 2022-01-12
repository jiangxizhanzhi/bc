package com.cyberpeace.cr.tool.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.controller.req.DeleteReq;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Api(tags = "后台-资源中心-工具库")
@RestController
@RequestMapping("/cr/admin/tools")
@RequiresAuthentication
public class ToolController {

    @Autowired
    private ToolService toolService;

    @ApiOperation("工具库列表")
    @GetMapping("/list")
    public BaseRsp<ListRsp<ToolRsp>> getList(
            @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
		Boolean isAdmin = true;
        Page page = pageReqBase.toPageParam();
        ToolListQuery listQuery = BeanUtils.copy(pageReqBase, ToolListQuery.class);
        IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, listQuery, isAdmin);
        return new BaseRsp(PageUtil.getListResult(toolBoListRsp));
    }

    @ApiOperation("工具详情")
    @GetMapping()
    public BaseRsp<ToolRsp> getDetail(
            @ApiParam(name = "resource_id", value = "查询id", required = true)
            @RequestParam("resource_id") @NotBlank String resourceId) {
        ToolRsp toolRsp = toolService.selectById(resourceId);
        return new BaseRsp<>(toolRsp);
    }

    @ApiOperation("工具创建")
    @PostMapping()
    public BaseRsp<String> create(@RequestBody ToolAddReq toolReq) {
        Tool tool = BeanUtils.copy(toolReq, Tool.class);
        if (!StringUtils.isBlank(toolReq.getPublishTime())) {
            Date date = DateUtil.StringToDate(toolReq.getPublishTime(), DateUtil.SHOT_PATTERN);
            tool.setPublishTime(date);
        }
        toolService.validateToolNameExist(tool);
        toolService.save(tool);
        return new BaseRsp<>();
    }

    @ApiOperation("工具更新")
    @PatchMapping()
    public BaseRsp<String> update(@RequestBody ToolUpdateReq toolupdateReq) {
        Tool tool = BeanUtils.copy(toolupdateReq, Tool.class);
        if (!StringUtils.isBlank(toolupdateReq.getPublishTime())) {
            Date date = DateUtil.StringToDate(toolupdateReq.getPublishTime(), DateUtil.SHOT_PATTERN);
            tool.setPublishTime(date);
        }
        toolService.updateById(tool);
        return new BaseRsp<>();
    }

    @ApiOperation("工具删除")
    @DeleteMapping()
    public BaseRsp<String> batchRemove(@RequestBody @Validated DeleteReq deleteReq) {
        toolService.removeByIds(deleteReq.getIds());
        return new BaseRsp<>();
    }

}
