package com.cyberpeace.cr.teamtrain.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.service.context.statistics.StatisticsDetail;
import com.cyberpeace.cr.teamtrain.service.XdTeamTrainStatisticsService;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonScoreStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamTimesStatisticsBo;
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
 * xd项目训练统计Controller
 *
 * @author SN
 * @date 2025/09/14
 */
@Api(tags = "xd项目团队训练统计")
@RestController
@RequestMapping("/cr/admin/xd_statistics")
@RequiresAuthentication
@Slf4j
public class XdTeamTrainStatisticsController {

    /**
     * xd项目团队训练统计service
     */
    @Autowired
    XdTeamTrainStatisticsService xdTeamTrainStatisticsService;

    /**
     * 训练场次统计
     * @return
     */
    @GetMapping("/xd_team_times")
    @ApiOperation("训练场次统计")
    public BaseRsp<List<XdTeamTimesStatisticsBo>> getXdTeamTimes() {
        List<XdTeamTimesStatisticsBo> rsp = xdTeamTrainStatisticsService.getXdTeamTimes();
        return new BaseRsp<>(rsp);
    }

    /**
     * 训练人数统计
     * @return
     */
    @GetMapping("/xd_team_person_num")
    @ApiOperation("训练场次统计")
    public BaseRsp<List<XdTeamPersonStatisticsBo>> getXdTeamPersonNum() {
        List<XdTeamPersonStatisticsBo> rsp = xdTeamTrainStatisticsService.getXdTeamPersonNum();
        return new BaseRsp<>(rsp);
    }

    /**
     * 个人得分排名TOP5
     * @return
     */
    @GetMapping("/xd_person_score")
    @ApiOperation("个人得分排名TOP5")
    public BaseRsp<List<XdTeamPersonScoreStatisticsBo>> getPersonScore() {
        List<XdTeamPersonScoreStatisticsBo> rsp = xdTeamTrainStatisticsService.getPersonScore();
        return new BaseRsp<>(rsp);
    }

    /**
     * 实验个人得分排名TOP5
     * @return
     */
    @GetMapping("/xd_person_score_sy")
    @ApiOperation("实验个人得分排名TOP5")
    public BaseRsp<List<XdTeamPersonScoreStatisticsBo>> getPersonScoreSy() {
        List<XdTeamPersonScoreStatisticsBo> rsp = xdTeamTrainStatisticsService.getPersonScoreSy();
        return new BaseRsp<>(rsp);
    }

    /**
     * 科目个人得分排名TOP5
     * @return
     */
    @GetMapping("/xd_person_score_km")
    @ApiOperation("科目个人得分排名TOP5")
    public BaseRsp<List<XdTeamPersonScoreStatisticsBo>> getPersonScoreKm() {
        List<XdTeamPersonScoreStatisticsBo> rsp = xdTeamTrainStatisticsService.getPersonScoreKm();
        return new BaseRsp<>(rsp);
    }

    /**
     * 综合演训个人得分排名TOP5
     * @return
     */
    @GetMapping("/xd_person_score_zhyx")
    @ApiOperation("综合演训个人得分排名TOP5")
    public BaseRsp<List<XdTeamPersonScoreStatisticsBo>> getPersonScoreZhyx() {
        List<XdTeamPersonScoreStatisticsBo> rsp = xdTeamTrainStatisticsService.getPersonScoreZhyx();
        return new BaseRsp<>(rsp);
    }

    /**
     * 获取全部详情
     *
     * @param detailReq 详情请求体
     * @return {@code BaseRsp<ListRsp<StatisticsDetail>> }
     */
    @GetMapping("/detail")
    @ApiOperation("团队训练统计详情")
    public BaseRsp<ListRsp<XdTeamPersonalStatisticsBo>> getAllDetail(TrainStatisticsDetailReq detailReq) {
        Page<XdTeamPersonalStatisticsBo> page = detailReq.toPageParam();
        ListRsp<XdTeamPersonalStatisticsBo> rsp = xdTeamTrainStatisticsService.getAllStatisticsDetail(detailReq, page, BusinessType.TRAINING_TEAM.getCode());
        return new BaseRsp<>(rsp);
    }

}
