package com.cyberpeace.cr.report_score.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreUpdateScoreReq;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "后台-打分列表")
@RestController
@RequestMapping("/cr/admin/report_score")
@RequiresAuthentication
public class ReportScoreController {

    @Autowired
    private ReportScoreService reportScoreService;

    @ApiOperation("打分列表")
    @GetMapping("/list")
    public BaseRsp<ListRsp<ReportScoreRsp>> getList(
            @ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
        Page page = pageReqBase.toPageParam();
        ReportScoreListReq reportScoreListReq = BeanUtils.copy(pageReqBase, ReportScoreListReq.class);
        IPage<ReportScoreRsp> reportScoreRsp = reportScoreService.selectPage(page, reportScoreListReq);
        return new BaseRsp(PageUtil.getListResult(reportScoreRsp));
    }

    @ApiOperation("打分详情")
    @GetMapping
    public BaseRsp<ReportScoreRsp> getDetail(
            @ApiParam(name = "resource_id", value = "查询id", required = true)
            @RequestParam("resource_id") @NotBlank String resourceId) {
        ReportScoreRsp reportScoreRsp = reportScoreService.selectById(resourceId);
        return new BaseRsp<>(reportScoreRsp);
    }

    @ApiOperation("更新打分")
    @PostMapping
    public BaseRsp<String> update(@RequestBody ReportScoreUpdateScoreReq reportScoreUpdateReq) {
        ReportScore reportScore = BeanUtils.copy(reportScoreUpdateReq, ReportScore.class);
        reportScoreService.updateById(reportScore);
        return new BaseRsp<>();
    }
}
