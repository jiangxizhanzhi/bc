package com.cyberpeace.cr.teamtrain.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;

import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.service.TeamTrainStatisticsService;
import com.cyberpeace.cr.teamtrain.service.bo.TeamOrganizationStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamStatisticsDetailBo;
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

/**
 * 团队训练统计学控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "团队训练统计")
@RestController
@RequestMapping("/cr/admin/team_train_statistics")
@RequiresAuthentication
@Slf4j
public class TeamTrainStatisticsController {

    /**
     * 团队训练统计学
     */
    @Autowired
    TeamTrainStatisticsService TeamTrainStatistics;


    /**
     * 获取都个人排
     *
     * @return {@code BaseRsp<List<TeamPersonalStatisticsBo>> }
     */
    @GetMapping("/personal_rank")
    @ApiOperation("团队训练个人排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN_STATISTICS)
    public BaseRsp<List<TeamPersonalStatisticsBo>> getAllPersonalRank() {
        List<TeamPersonalStatisticsBo> rsp = TeamTrainStatistics.getAllPersonalRankStatistics();
        return new BaseRsp<>(rsp);
    }

    /**
     * 获取组织排
     *
     * @return {@code BaseRsp<List<TeamOrganizationStatisticsBo>> }
     */
    @GetMapping("/organization_rank")
    @ApiOperation("团队训练组织排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN_STATISTICS)
    public BaseRsp<List<TeamOrganizationStatisticsBo>> getOrganizationRank() {
        List<TeamOrganizationStatisticsBo> rsp = TeamTrainStatistics.getOrganizationRankStatistics();
        return new BaseRsp<>(rsp);
    }


    /**
     * 获取都详情
     *
     * @param detailReq 详情请求体
     * @return {@code BaseRsp<ListRsp<TeamStatisticsDetailBo>> }
     */
    @GetMapping("/detail")
    @ApiOperation("团队训练统计详情")
    @RequiresPermissions(MenuConstant.CMS_CR_TEAM_TRAIN_STATISTICS)
    public BaseRsp<ListRsp<TeamStatisticsDetailBo>> getAllDetail(TeamTrainStatisticsDetailReq detailReq) {
        Page page = detailReq.toPageParam();
        ListRsp<TeamStatisticsDetailBo> rsp = TeamTrainStatistics.getAllStatisticsDetail(detailReq, page);
        return new BaseRsp<>(rsp);
    }

}
