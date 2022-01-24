package com.cyberpeace.cr.report_score.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreUpdateScoreReq;
import com.cyberpeace.cr.report_score.controller.req.ScoreRecordListReq;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreDetailRsp;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.controller.res.ScoreRecordRsp;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
            @ApiParam(name = "listReq", value = "查询条件") @Validated ReportScoreListReq reportScoreListReq) {
        Page page = reportScoreListReq.toPageParam();
        IPage<ReportScoreRsp> reportScoreRsp = reportScoreService.selectPage(page, reportScoreListReq);
        return new BaseRsp(PageUtil.getListResult(reportScoreRsp));
    }

    @ApiOperation("打分详情")
    @GetMapping
    public BaseRsp<ReportScoreDetailRsp> getDetail(
            @ApiParam(name = "resource_id", value = "查询id", required = true)
            @RequestParam("resource_id") @NotBlank String resourceId) {
        ReportScoreDetailRsp reportScoreDetailRsp = reportScoreService.selectById(resourceId);
        return new BaseRsp<>(reportScoreDetailRsp);
    }

    @ApiOperation("更新打分")
    @PostMapping
    public BaseRsp<String> update(@RequestBody ReportScoreUpdateScoreReq reportScoreUpdateReq) {
        ReportScore reportScore = BeanUtils.copy(reportScoreUpdateReq, ReportScore.class);
        reportScore.setScoreTime(new Date());
        reportScoreService.updateById(reportScore);
        return new BaseRsp<>();
    }

    @ApiOperation("科目评分记录")
    @GetMapping("/score_record")
    public BaseRsp<ListRsp<ScoreRecordRsp>> getScoreList(
            @ApiParam(name = "listReq", value = "查询条件") @Validated ScoreRecordListReq scoreRecordListReq) {
        Page page = scoreRecordListReq.toPageParam();
        IPage<ScoreRecordRsp> scoreRecordRspIPage = reportScoreService.selectRecordPage(page, scoreRecordListReq);
        return new BaseRsp<>(PageUtil.getListResult(scoreRecordRspIPage));
    }

    @ApiOperation("报告科目统计")
    @GetMapping("/subject/count")
    public BaseRsp<List<HashMap<String, String>>> getSubjectCount() {
        List<HashMap<String, String>> result = reportScoreService.selectCountBySubject();
        return new BaseRsp<>(result);
    }

    @ApiOperation("报告状态统计")
    @GetMapping("/status/count")
    public BaseRsp<List<HashMap<String, String>>> getStatusCount() {
        List<HashMap<String, String>> result = reportScoreService.selectCountByStatus();
        return new BaseRsp<>(result);
    }
}
