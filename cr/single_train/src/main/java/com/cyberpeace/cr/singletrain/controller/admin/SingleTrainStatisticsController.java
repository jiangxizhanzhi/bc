package com.cyberpeace.cr.singletrain.controller.admin;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
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

/**
 * 单训练统计学控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Api(tags = "单兵训练统计")
@RestController
@RequestMapping("/cr/admin/single_train_statistics")
@RequiresAuthentication
@Slf4j
public class SingleTrainStatisticsController {

    /**
     * 单训练统计学
     *
     * @see SingleTrainStatisticsService
     */
    @Autowired
    SingleTrainStatisticsService singleTrainStatistics;


    /**
     * 获取都个人排
     *
     * @return {@code BaseRsp<List<SinglePersonalStatisticsBo>> }
     * @throws
     * @see BaseRsp<List<SinglePersonalStatisticsBo>>
     */
    @GetMapping("/personal_rank")
    @ApiOperation("单兵训练个人排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN_STATISTICS)
    public BaseRsp<List<SinglePersonalStatisticsBo>> getAllPersonalRank() {
        List<SinglePersonalStatisticsBo> rsp = singleTrainStatistics.getAllPersonalRankStatistics();
        return new BaseRsp<>(rsp);
    }

    /**
     * 获取组织排
     *
     * @return {@code BaseRsp<List<SingleOrganizationStatisticsBo>> }
     * @throws
     * @see BaseRsp<List<SingleOrganizationStatisticsBo>>
     */
    @GetMapping("/organization_rank")
    @ApiOperation("单兵训练组织排名TOP5")
    @RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN_STATISTICS)
    public BaseRsp<List<SingleOrganizationStatisticsBo>> getOrganizationRank() {
        List<SingleOrganizationStatisticsBo> rsp = singleTrainStatistics.getOrganizationRankStatistics();
        return new BaseRsp<>(rsp);
    }


    /**
     * 获取都详情
     *
     * @param detailReq 详情请求体
     * @return {@code BaseRsp<ListRsp<SingleStatisticsDetailBo>> }
     * @throws
     * @see SingleTrainStatisticsDetailReq
     * @see BaseRsp<ListRsp<SingleStatisticsDetailBo>>
     */
    @GetMapping("/detail")
    @ApiOperation("单兵训练统计详情")
    @RequiresPermissions(MenuConstant.CMS_CR_SINGLE_TRAIN_STATISTICS)
    public BaseRsp<ListRsp<SingleStatisticsDetailBo>> getAllDetail(SingleTrainStatisticsDetailReq detailReq) {
        Page page = detailReq.toPageParam();
        ListRsp<SingleStatisticsDetailBo> rsp = singleTrainStatistics.getAllStatisticsDetail(detailReq, page);
        return new BaseRsp<>(rsp);
    }

}
