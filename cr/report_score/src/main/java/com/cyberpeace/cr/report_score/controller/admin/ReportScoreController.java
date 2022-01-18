package com.cyberpeace.cr.report_score.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return new BaseRsp<>();
    }
}
