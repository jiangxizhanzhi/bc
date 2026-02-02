package com.cyberpeace.cr.teamtrain.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.AuthKeyUser;
import com.cyberpeace.common.annoations.AuthOjKey;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.enums.TeamRoleGroup;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.RoomBaseInfo;
import com.cyberpeace.cr.crbase.service.context.TrainReportContext;
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.teamtrain.controller.req.TeamRoomListReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.XdTeamTrainStatisticsService;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonalStatisticsBo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 接口管理
 *
 * @author SN
 * @date 2025/11/24
 */
@Api(tags = "接口管理对外开放的接口")
@AuthKeyUser
@RestController
@RequestMapping("/cr/out_interface")
@Slf4j
public class OutInterfaceController {

    @Autowired
    XdTeamTrainStatisticsService xdTeamTrainStatisticsService;
    @Autowired
    private TeamRoomService teamRoomService;
    @Autowired
    private TrainRecordService trainRecordService;
    @Autowired
    private CrScoreService crScoreService;
    @Autowired
    private TeamTemplateMapper teamTemplateMapper;
    @Autowired
    private CrConstantsService crConstantsService;
    @Autowired
    private CrRoleService crRoleService;
    /**
     * 靶场任务Service
     */
    @Autowired
    private CrTaskService crTaskService;

    @AuthOjKey
    @ApiOperation("训练学员统计列表查询")
    @GetMapping("/train_detail")
    public BaseRsp<ListRsp<XdTeamPersonalStatisticsBo>> getAllDetail(TrainStatisticsDetailReq detailReq) {
        Page<XdTeamPersonalStatisticsBo> page = detailReq.toPageParam();
        ListRsp<XdTeamPersonalStatisticsBo> rsp = xdTeamTrainStatisticsService.getAllStatisticsDetail(detailReq, page, BusinessType.TRAINING_TEAM.getCode());
        return new BaseRsp<>(rsp);
    }

    @AuthOjKey
    @ApiOperation("实验列表查询")
    @GetMapping("/team_train_list")
    public BaseRsp<ListRsp<TeamRoomRsp>> list(@Validated TeamRoomListReq listReq) {
        ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
        listQuery.setPer_page(listReq.getPerPage());
        ListRsp<TeamRoomRsp> rsp = teamRoomService.listRoom(listQuery, true);
        if (CollectionUtils.isNotEmpty(rsp.getList())) {
            for (TeamRoomRsp temp : rsp.getList()) {
                TaskStatisticsInfo taskStatisticsInfo = crTaskService.getTaskStatisticsInfoByRoomId(temp.getResourceId());
                temp.setSubmitMissionCount(taskStatisticsInfo.getSubmitMissionCount() == null ? 0 : taskStatisticsInfo.getSubmitMissionCount());
                int userCount = 0;
                if (temp.getTrainingRoleData() != null && CollectionUtils.isNotEmpty(temp.getTrainingRoleData().getRoleDataList())) {
                    for (RoleUsers roleUsers : temp.getTrainingRoleData().getRoleDataList()) {
                        if (CollectionUtils.isNotEmpty(roleUsers.getUsers())) {
                            userCount += roleUsers.getUsers().size();
                        }
                    }
                }
                temp.setUserCount(userCount);
            }
        }
        return new BaseRsp<>(rsp);
    }

    @AuthOjKey
    @ApiOperation("实验训练报告")
    @GetMapping("/getTrainReports")
    public BaseRsp<TrainReportContext> getTrainReports(
            @ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
            @ApiParam(name = "scoreCount", value = "节点数", required = true) Integer scoreCount) {
        TrainRecord trainRecord = trainRecordService.getByRoomId(resource_id);

        TeamRoom teamRoom = teamRoomService.getById(resource_id);
        if (scoreCount == null || scoreCount == 0) {
            scoreCount = 10;
        }
        ScoreTrendReq scoreTrendReq = new ScoreTrendReq(trainRecord, teamRoom.getStartTime(),
                teamRoom.getEndTime(), scoreCount, teamRoom.getReplaySceneId());
        // 得分趋势
        TrainReportContext trainReport = crScoreService.getTrainReport(resource_id,
                Lists.newArrayList(TeamRoleGroup.STUDENT.getCode()), scoreTrendReq);
        // 训练未开始，训练时间显示的是创建训练填写的训练开始时间，训练时长是训练模板设置的推荐时长
        // 训练已开始，训练时间显示的启动训练时间，训练时长是当前时间-启动训练时间
        // 训练已结束，训练时间显示的启动训练时间，训练时长是结束训练-启动训练时间
        teamRoom.setStartTime(scoreTrendReq.getStartTime());
        teamRoom.setEndTime(scoreTrendReq.getEndTime());
        initTeamReport(teamRoom, trainReport);
        if (trainRecord == null) {
            String timeCost = trainReport.getRoomBaseInfo().getDurationTime() == null
                    ? CommonConstant.SHORT_TRANSVERSE_LINE
                    : trainReport.getRoomBaseInfo().getDurationTime()
                    + TrainingReport.TIME.HOUR.getMsg();
            trainReport.getRoomBaseInfo().setTimeCost(timeCost);
        }
        return new BaseRsp<TrainReportContext>(trainReport);
    }

    /**
     * 初始化团队报告
     *
     * @param room        房间
     * @param trainReport 训练报告
     */
    public void initTeamReport(TeamRoom room, TrainReportContext trainReport) {
        // 基础信息
        RoomBaseInfo roomBaseInfo = BeanUtils.copy(room, RoomBaseInfo.class);
        TeamTemplate template = teamTemplateMapper.selectById(room.getTeamTemplateId());
        if (template != null) {
            roomBaseInfo.setDurationTime(template.getDurationTime());
        }
        roomBaseInfo.initExportString();
        roomBaseInfo.setDifficultyString(crConstantsService.geCnNameByTypeAndCode(
                CrConstantsConfig.ConstantsType.DIFFICULTY.getCode(),
                roomBaseInfo.getDifficulty()));
        trainReport.setRoomBaseInfo(roomBaseInfo);
        List<UserRole> teachers = crRoleService.getUserRolesByRoomIdAndRoleGroups(
                room.getResourceId(), Lists.newArrayList(TeamRoleGroup.TEACHER.getCode()));
        trainReport.setRoomBaseInfo(roomBaseInfo);
        trainReport.setTeacherName(UserRole.getName(UserRole::getName, teachers));
        trainReport.initRbGetScoreRsps(false);
    }

}
