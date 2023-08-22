package com.cyberpeace.cr.redvsblue.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.redvsblue.controller.req.RbStatisticsDetailReq;
import com.cyberpeace.cr.redvsblue.service.RbStatisticsService;
import com.cyberpeace.cr.redvsblue.service.bo.RbOrganizationStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbPersonalStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbStatisticsDetailBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "红蓝对抗统计")
@RestController
@RequestMapping("/cr/admin/red_vs_blue_statistics")
@RequiresAuthentication
@Slf4j
public class RbStatisticsController {

    @Autowired
    RbStatisticsService RbTrainStatistics;


    @GetMapping("/personal_rank")
    @ApiOperation("所有人员排名")
    @RequiresPermissions(MenuConstant.CMS_CR_RB_TRAIN_STATISTICS)
    public BaseRsp<List<RbPersonalStatisticsBo>> getAllPersonalRank() {
        List<RbPersonalStatisticsBo> rsp = RbTrainStatistics.getAllPersonalRankStatistics();
        return new BaseRsp<>(rsp);
    }

    @GetMapping("/organization_rank")
    @ApiOperation("所有组织排名")
    @RequiresPermissions(MenuConstant.CMS_CR_RB_TRAIN_STATISTICS)
    public BaseRsp<List<RbOrganizationStatisticsBo>> getOrganizationRank() {
        List<RbOrganizationStatisticsBo> rsp = RbTrainStatistics.getOrganizationRankStatistics();
        return new BaseRsp<>(rsp);
    }


    @GetMapping("/detail")
    @ApiOperation("红蓝对抗统计详情")
    @RequiresPermissions(MenuConstant.CMS_CR_RB_TRAIN_STATISTICS)
    public BaseRsp<ListRsp<RbStatisticsDetailBo>> getDetailAll(RbStatisticsDetailReq detailReq) {
        Page page = detailReq.toPageParam();
        ListRsp<RbStatisticsDetailBo> rsp = RbTrainStatistics.getAllStatisticsDetail(detailReq, page);
        return new BaseRsp<>(rsp);
    }

}
