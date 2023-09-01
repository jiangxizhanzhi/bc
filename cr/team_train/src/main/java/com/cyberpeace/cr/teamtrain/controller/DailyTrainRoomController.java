package com.cyberpeace.cr.teamtrain.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.teamtrain.controller.req.DailyTrainRoomReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainRoomModifyReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRoleRes;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.context.RoomRole;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "日常训练实例")
@RestController
@Slf4j
@RequestMapping("/cr/daily_train/room")
@RequiresAuthentication
public class DailyTrainRoomController {

    @Autowired
    private TeamRoomService teamRoomService;

    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private RemoteLogService remoteLogService;

    @ApiOperation("新增")
    @PostMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_DAILY_TRAIN)
    public BaseRsp<TeamRoomRsp> create(@RequestBody @Validated DailyTrainRoomReq teamTrainRoomReq) {
        // 校验
        teamTrainRoomReq.validate();
        TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomReq,TeamTrainRoomContext.class);
        List<RoomRole> roles = BeanUtils.deepCopyList(teamTrainRoomReq.getRoleConfig(),RoomRole.class);
        teamTrainRoomContext.setRoleConfig(roles);
        teamTrainRoomContext.setPreSkill(new ArrayList<>());
        teamTrainRoomContext.setTrainSkill(new ArrayList<>());
        teamTrainRoomContext.setTrainCategory(TeamRoom.TrainCategory.DAILY.getCode());
        TeamRoom teamRoom = teamRoomService.create(teamTrainRoomContext);
        remoteLogService.addOjLog(UserInfoContext.getUserContext(), teamRoom.getModule(),
                OperationType.CREATE, teamTrainRoomReq.getName(),
                LogContent.CR_FORMAT.format(OperationType.CREATE, teamRoom.getLogMsg(), teamTrainRoomReq.getName()));

        return getTeamRoom(teamRoom.getResourceId());
    }

    @ApiOperation("修改")
    @PutMapping()
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_TRAIN, MenuConstant.WEB_CR_BLUE_TRAIN, MenuConstant.WEB_CR_DAILY_TRAIN}, logical = Logical.OR)
    public BaseRsp<TeamRoomRsp>
    update(@RequestBody @Validated TeamTrainRoomModifyReq teamTrainRoomModifyReq) {
        // 校验
        teamTrainRoomModifyReq.validate();
        TeamRoom teamRoom = teamRoomService.getById(teamTrainRoomModifyReq.getResourceId());

        if (teamRoom == null) {
            throw new ResourceNotFoundException(teamTrainRoomModifyReq.getResourceId());
        }
        TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomModifyReq,TeamTrainRoomContext.class);
        List<RoomRole> roomRoleList = JsonUtil.toJsonObjectArray(teamTrainRoomModifyReq.getRoleConfig(), RoomRole.class);
        teamTrainRoomContext.setRoleConfig(roomRoleList);
        teamRoomService.updateTeamRoom(teamTrainRoomContext, teamRoom);

        teamRoom = teamRoomService.getById(teamTrainRoomModifyReq.getResourceId());
        remoteLogService.addOjLog(UserInfoContext.getUserContext(), teamRoom.getModule(),
                OperationType.UPDATE, teamRoom.getName(),
                LogContent.CR_FORMAT.format(OperationType.UPDATE, teamRoom.getLogMsg(), teamRoom.getName()));

        return getTeamRoom(teamTrainRoomModifyReq.getResourceId());
    }

    @ApiOperation("查询")
    @GetMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_DAILY_TRAIN)
    public BaseRsp<TeamRoomRsp> getTeamRoom(
            @ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
        String userId = UserInfoContext.getUserContext().getUserId();
        crRoleService.checkPermission(resource_id, UserInfoContext.getUserContext().getUserId());

        TeamRoomData teamRoomData = teamRoomService.getTeamRoomDataByRoomId(resource_id);

        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setRoomId(teamRoomData.getResourceId());

        List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

        TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
        teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userId));

        teamRoomRsp.setRolesData(getTeamRoomRoleRes(crRoles));

        teamRoomRsp.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
        teamRoomRsp.setTrainSkill(JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));

        return new BaseRsp<>(teamRoomRsp);
    }

    private List<TeamRoomRoleRes> getTeamRoomRoleRes(List<CrRole> crRoles) {
        List<TeamRoomRoleRes> teamRoomRoleResList = Lists.newArrayList();
        for (CrRole crRole : crRoles) {
            TeamRoomRoleRes teamRoomRoleRes = BeanUtils.copy(crRole, TeamRoomRoleRes.class);
            teamRoomRoleRes.setUsersData(crRoleService.getUserDatas(crRole.getUserIdList()));
            teamRoomRoleRes.setUsers(crRole.getUserIdList());

            teamRoomRoleRes.setTrainingTeam(crRole.getRoomId());
            teamRoomRoleResList.add(teamRoomRoleRes);
        }
        return teamRoomRoleResList;
    }

    // 获取单个实例中自己的角色信息，只包括自己角色的基本信息
    private BaseRole getSelfRole(List<CrRole> crRoles, String userId) {
        BaseRole baseRole = new BaseRole();
        crRoles.forEach(crRole -> {
            if (crRole.containsUser(userId)) {
                baseRole.setResourceId(crRole.getResourceId());
                baseRole.setName(crRole.getName());

                baseRole.setDescription(crRole.getDescription());
                baseRole.setRoleGroup(crRole.getRoleGroup());
            }
        });
        return baseRole;
    }

}
