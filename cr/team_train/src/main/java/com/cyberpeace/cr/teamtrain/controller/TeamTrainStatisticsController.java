package com.cyberpeace.cr.teamtrain.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamCapabilityRsp;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cr.teamtrain.service.TeamTrainStatisticsService;
import com.cyberpeace.cr.teamtrain.service.bo.TeamOrganizationStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamStatisticsDetailBo;
import com.cyberpeace.integratation.model.mp.MpFlowAttackCount;
import com.cyberpeace.integratation.service.RemoteSceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "团队训练统计")
@RestController
@RequestMapping("/cr/admin/team_train_statistics")
@RequiresAuthentication
@Slf4j
public class TeamTrainStatisticsController {

    @Autowired
    TeamTrainStatisticsService teamTrainStatistics;

    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private TeamRoomService teamRoomService;

    @Autowired
    private TeamSceneService teamSceneService;

    @Autowired
    private CrRoleService crRoleService;


    @GetMapping("/personal_rank")
    @ApiOperation("团队训练个人排名TOP5")
    @RequiresPermissions(value = {MenuConstant.CMS_CR_RED_TEAM_TRAIN_STATISTICS, MenuConstant.CMS_CR_BLUE_TEAM_TRAIN_STATISTICS}, logical = Logical.OR)
    public BaseRsp<List<TeamPersonalStatisticsBo>> getAllPersonalRank(
            @ApiParam(name = "team_category", value = "队伍训练类型, 1=红;2=蓝")
            @RequestParam("team_category") Integer teamCategory) {
        List<TeamPersonalStatisticsBo> rsp = teamTrainStatistics.getAllPersonalRankStatistics(teamCategory);
        return new BaseRsp<>(rsp);
    }

    @GetMapping("/organization_rank")
    @ApiOperation("团队训练组织排名TOP5")
    @RequiresPermissions(value = {MenuConstant.CMS_CR_RED_TEAM_TRAIN_STATISTICS, MenuConstant.CMS_CR_BLUE_TEAM_TRAIN_STATISTICS}, logical = Logical.OR)
    public BaseRsp<List<TeamOrganizationStatisticsBo>> getOrganizationRank(
            @ApiParam(name = "team_category", value = "队伍训练类型, 1=红;2=蓝")
            @RequestParam("team_category") Integer teamCategory) {
        List<TeamOrganizationStatisticsBo> rsp = teamTrainStatistics.getOrganizationRankStatistics(teamCategory);
        return new BaseRsp<>(rsp);
    }


    @GetMapping("/detail")
    @ApiOperation("团队训练统计详情")
    @RequiresPermissions(value = {MenuConstant.CMS_CR_RED_TEAM_TRAIN_STATISTICS, MenuConstant.CMS_CR_BLUE_TEAM_TRAIN_STATISTICS}, logical = Logical.OR)
    public BaseRsp<ListRsp<TeamStatisticsDetailBo>> getAllDetail(TeamTrainStatisticsDetailReq detailReq) {
        Page page = detailReq.toPageParam();
        ListRsp<TeamStatisticsDetailBo> rsp = teamTrainStatistics.getAllStatisticsDetail(detailReq, page, detailReq.getTeamCategory());
        return new BaseRsp<>(rsp);
    }


    @ApiOperation("3D态势-战队能力")
    @GetMapping("/flow_attack")
    @RequiresPermissions(MenuConstant.CMS_CR_RED_TEAM_TRAIN_STATISTICS)
    public BaseRsp<List<TeamCapabilityRsp>> getFlowAttackCount(
            @ApiParam(name = "room_id", value = "训练id", required = true) @RequestParam("room_id") String roomId) {

        SceneViewContext context = teamSceneService.viewScene(roomId, null);
        List<CrRole> crRoles = crRoleService.selectRbStudentsRoleByRoomId(roomId);

        Map<String, String> roleIpMap = new HashMap<>();
        for(CrRole role: crRoles){
            List<JSONObject> visibleNodes = JSONObject.parseObject(role.getNodes()).getObject("visibleNodes",List.class);
            for(JSONObject visibleNode: visibleNodes){
                String nodeId = visibleNode.getString("id");
                roleIpMap.put(role.getName(),nodeId);
            }
        }
        List<TeamCapabilityRsp> result = new ArrayList<>();
        List<MpFlowAttackCount> flowAttackCountList = remoteSceneService.getFlowAttackCount(context.getMpSceneId());
        Map<String, Integer> attackMap = StreamApiUtils.toMap(flowAttackCountList, MpFlowAttackCount::getNodeName, MpFlowAttackCount::getTimes);

//        for (Map.Entry<String, String> entry : roleIpMap.entrySet()) {
//            TeamCapabilityRsp teamCapabilityRsp = new TeamCapabilityRsp();
//            teamCapabilityRsp.setRoleName(entry.getValue());
////            teamCapabilityRsp.setList();
//            result.add(teamCapabilityRsp);
//        }

        return new BaseRsp<>(result);

    }

}
