package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.service.TrainStatisticsService;
import com.cyberpeace.cr.crbase.service.context.statistics.StatisticsDetail;
import com.cyberpeace.cr.redvsblue.service.RbStatisticsService;
import com.cyberpeace.cr.redvsblue.service.bo.RbOrganizationStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbPersonalStatisticsBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 红蓝对抗统计学Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "红蓝对抗统计")
@RestController
@RequestMapping("/cr/admin/red_vs_blue_statistics")
@RequiresAuthentication
@Slf4j
public class RbStatisticsController {

    /**
     * 红蓝对抗统计学Service
     */
    @Autowired
    RbStatisticsService rbStatisticsService;
    /**
     * 训练统计学Service
     */
    @Autowired
    TrainStatisticsService trainStatisticsService;

    /**
     * 获取全部个人排
     *
     * @return {@code BaseRsp<List<RbPersonalStatisticsBo>> }
     */
    @GetMapping("/personal_rank")
    @ApiOperation("红蓝对抗个人排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_RB_TRAIN_STATISTICS)
    public BaseRsp<List<RbPersonalStatisticsBo>> getAllPersonalRank() {
        List<RbPersonalStatisticsBo> rsp = rbStatisticsService.getAllPersonalRankStatistics();
        return new BaseRsp<>(rsp);
    }

    /**
     * 获取组织排
     *
     * @return {@code BaseRsp<List<RbOrganizationStatisticsBo>> }
     */
    @GetMapping("/organization_rank")
    @ApiOperation("红蓝对抗组织排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_RB_TRAIN_STATISTICS)
    public BaseRsp<List<RbOrganizationStatisticsBo>> getOrganizationRank() {
        List<RbOrganizationStatisticsBo> rsp = rbStatisticsService.getOrganizationRankStatistics();
        return new BaseRsp<>(rsp);
    }


    /**
     * 获取详情全部
     *
     * @param detailReq 详情请求体
     * @return {@code BaseRsp<ListRsp<StatisticsDetail>> }
     */
    @GetMapping("/detail")
    @ApiOperation("红蓝对抗统计详情")
    @RequiresPermissions(MenuConstant.CMS_CR_RB_TRAIN_STATISTICS)
    public BaseRsp<ListRsp<StatisticsDetail>> getDetailAll(TrainStatisticsDetailReq detailReq) {
        Page<StatisticsDetail> page = detailReq.toPageParam();
        ListRsp<StatisticsDetail> rsp = trainStatisticsService.getAllStatisticsDetail(detailReq, page, BusinessType.RED_VS_BLUE.getCode());
        return new BaseRsp<>(rsp);
    }

}
