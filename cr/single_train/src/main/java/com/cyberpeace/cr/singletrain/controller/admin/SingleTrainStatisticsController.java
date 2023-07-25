package com.cyberpeace.cr.singletrain.controller.admin;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.TrainStatisticsService;
import com.cyberpeace.cr.crbase.service.context.statistics.StatisticsDetail;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainStatisticsDetailReq;
import com.cyberpeace.cr.singletrain.service.SingleTrainStatisticsService;
import com.cyberpeace.cr.singletrain.service.bo.SingleOrganizationStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SinglePersonalStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleStatisticsDetailBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "单兵训练统计")
@RestController
@RequestMapping("/cr/admin/single_train_statistics")
@RequiresAuthentication
@Slf4j
public class SingleTrainStatisticsController {

    @Autowired
    SingleTrainStatisticsService singleTrainStatistics;
    @Autowired
    TrainStatisticsService trainStatisticsService;


    @GetMapping("/personal_rank")
    @ApiOperation("单兵训练个人排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN_STATISTICS)
    public BaseRsp<List<SinglePersonalStatisticsBo>> getAllPersonalRank() {
        List<SinglePersonalStatisticsBo> rsp = singleTrainStatistics.getAllPersonalRankStatistics();
        return new BaseRsp<>(rsp);
    }

    @GetMapping("/organization_rank")
    @ApiOperation("单兵训练组织排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN_STATISTICS)
    public BaseRsp<List<SingleOrganizationStatisticsBo>> getOrganizationRank() {
        List<SingleOrganizationStatisticsBo> rsp = singleTrainStatistics.getOrganizationRankStatistics();
        return new BaseRsp<>(rsp);
    }


    @GetMapping("/detail")
    @ApiOperation("单兵训练统计详情")
    @RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN_STATISTICS)
    public BaseRsp<ListRsp<StatisticsDetail>> getAllDetail(TrainStatisticsDetailReq detailReq) {
        Page page = detailReq.toPageParam();
        ListRsp<StatisticsDetail> rsp = trainStatisticsService.getAllStatisticsDetail(detailReq, page, BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
        return new BaseRsp<>(rsp);
    }

}
