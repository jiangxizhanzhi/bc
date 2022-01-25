package com.cyberpeace.cr.report_score.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreAddReq;
import com.cyberpeace.cr.report_score.controller.req.WebReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.res.WebReportScoreDetailRsp;
import com.cyberpeace.cr.report_score.controller.res.WebReportScoreRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Api(tags = "前台-报告列表")
@RestController
@RequestMapping("/cr/report_score")
@RequiresAuthentication
public class WebReportScoreController {

    @Autowired
    private ReportScoreService reportScoreService;

    @ApiOperation("提交报告")
    @PostMapping()
    public BaseRsp<String> create(@RequestBody ReportScoreAddReq reportScoreAddReq) {
        ReportScore reportScore = BeanUtils.copy(reportScoreAddReq, ReportScore.class);

        reportScore.setSubmitTime(new Date());

        String userId = UserInfoContext.getUserContext().getUserId();
        String userName = UserInfoContext.getUserContext().getUserName();
        reportScore.setUserId(userId);
        reportScore.setUserName(userName);

        reportScoreService.validateReportScoreNameExist(reportScore);
        reportScoreService.save(reportScore);
        return new BaseRsp<>();
    }

    @ApiOperation("报告列表")
    @GetMapping("/list")
    public BaseRsp<ListRsp<WebReportScoreRsp>> getList(
            @ApiParam(name = "listReq", value = "查询条件") @Validated WebReportScoreListReq webReportScoreListReq) {
        String userId = UserInfoContext.getUserContext().getUserId();
        Page page = webReportScoreListReq.toPageParam();
        IPage<WebReportScoreRsp> webReportScoreRsp = reportScoreService.selectWebPage(page, webReportScoreListReq, userId);
        return new BaseRsp<>(PageUtil.getListResult(webReportScoreRsp));
    }

    @ApiOperation("报告详情")
    @GetMapping()
    public BaseRsp<WebReportScoreDetailRsp> getDetail(
            @ApiParam(name = "resource_id", value = "查询id", required = true)
            @RequestParam("resource_id") @NotBlank String resourceId) {
        WebReportScoreDetailRsp webReportScoreDetailRsp = reportScoreService.selectWebById(resourceId);
        return new BaseRsp<>(webReportScoreDetailRsp);
    }

    @ApiOperation("上传报告附件")
    @PostMapping("/upload_report_file")
    public BaseRsp<String> uploadReportFile(@RequestParam("file") MultipartFile file) throws IOException {
        String res = reportScoreService.saveFile(file);
        return new BaseRsp<>(res);
    }

    @ApiOperation("报告状态统计")
    @GetMapping("/status/count")
    public BaseRsp<List<HashMap<String, String>>> getStatusCount() {
        String userId = UserInfoContext.getUserContext().getUserId();
        List<HashMap<String, String>> result = reportScoreService.selectWebCountByStatus(userId);
        return new BaseRsp<>(result);
    }

    @ApiOperation("排名得分统计")
    @GetMapping("/rank_score")
    public BaseRsp<HashMap<String, String>> getRankScore() {
        String userId = UserInfoContext.getUserContext().getUserId();
        HashMap<String, String> result = reportScoreService.getRankScore(userId);
        return new BaseRsp<>(result);
    }

}
