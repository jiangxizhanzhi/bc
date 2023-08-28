package com.cyberpeace.cr.evaluation.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.rsp.*;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.cr.evaluation.controller.req.EvalMissionEvaluationListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalStudentScoreReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSubmitEvaluationReq;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalRole;
import com.cyberpeace.cr.evaluation.dao.mapper.CrEvalRoleMapper;
import com.cyberpeace.cr.evaluation.service.EvalRbRoomService;
import com.cyberpeace.cr.evaluation.service.EvalResultService;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRoleRes;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbSituationScoreRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.TimeDataRsp;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.XYData;
import com.cyberpeace.integratation.service.RemoteUserService;
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

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "训练评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/train_eval")
@Slf4j
public class TrainEvalController {

    @Autowired
    private EvalRbRoomService evalRbRoomService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private CrScoreController crScoreController;

    @Autowired
    private EvalResultService evalResultService;

    @Autowired
    private CrEvalRoleMapper crEvalRoleMapper;

    @ApiOperation("获取学员的评估提交记录")
    @GetMapping("/mission_evaluation")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<List<MissionEvaluationRsp>>
    getMissionEvaluations(@Validated EvalMissionEvaluationListReq req) {

        EvalMissionEvaluationBo missionEvaluationQuery = BeanUtils.copy(req,
                EvalMissionEvaluationBo.class);
        missionEvaluationQuery.setPer_page(req.getPerPage());
        missionEvaluationQuery.setStudentHash(req.getSubmitUser());

        IPage<MissionEvaluationContext> data = evalRbRoomService
                .getRbMissionEvaluationList(missionEvaluationQuery);

        List<String> userIds = data.getRecords().stream()
                .map(MissionEvaluationContext::getSubmitUser).distinct()
                .collect(Collectors.toList());

        userIds.addAll(data.getRecords().stream().map(MissionEvaluationContext::getEvaluatorId)
                .distinct().collect(Collectors.toList()));
        Map<String, String> userInfoMap = remoteUserService.getIdNameMap(userIds);

        data.convert(missionEvaluationContext -> {
            MissionEvaluationRsp missionEvaluationRsp = BeanUtils.copy(missionEvaluationContext,MissionEvaluationRsp.class);

            MissionEvaluationRsp.SubmitInfo submitInfo = missionEvaluationRsp.new SubmitInfo();

            submitInfo.setCreateTime(missionEvaluationContext.getCreateTime());
            submitInfo.setUserAnswer(missionEvaluationContext.getAnswer());

            missionEvaluationRsp.setEvaluatorName(userInfoMap.get(missionEvaluationContext.getEvaluatorId()));
            missionEvaluationRsp.setEvaluator(missionEvaluationContext.getEvaluatorId());
            missionEvaluationRsp.setSubmitUserRole(missionEvaluationContext.getSubmitUserRole());
            missionEvaluationRsp.setSubmitUserRoleResourceId(missionEvaluationContext.getSubmitUserRoleId());
            missionEvaluationRsp.setSubmitUserName(userInfoMap.get(missionEvaluationContext.getSubmitUser()));
            missionEvaluationRsp.setSubmitLogHash(missionEvaluationContext.getMissionHash());
            missionEvaluationRsp.setTotalScore(missionEvaluationContext.getMission().getScore());
            missionEvaluationRsp.setEvaluateTime(missionEvaluationContext.getEvaluateTime());
            missionEvaluationRsp.setSubmitInfo(submitInfo);
            missionEvaluationRsp.setMissionData(missionEvaluationContext.getMission());

            return missionEvaluationRsp;
        });

        return new BaseRsp(PageUtil.getListResult(data));
    }

    @ApiOperation("提交任务的人工评估")
    @PostMapping("/submit_evaluation")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<String> updateSubmitEvaluation(@ApiParam(name = "submitEvaluationReq",
            value = "学员评分") @RequestBody EvalSubmitEvaluationReq req) {
        evalRbRoomService.submitEvaluation(req, UserInfoContext.getUserContext().getUserId());
        return new BaseRsp<>();
    }

    /**
     * 获取统计数据
     *
     * @return
     */
    @ApiOperation("获取统计数据")
    @GetMapping("/get_statistical_info")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getStatisticalInfo(
            @ApiParam(name = "resource_id", value = "评估训练id", required = true) String resource_id) {
        EvalMissionEvaluationBo missionEvaluationQuery = new EvalMissionEvaluationBo();
        missionEvaluationQuery.setEvalTemplateId(resource_id);
        missionEvaluationQuery.setPage(1);

        IPage<MissionEvaluationContext> data = evalRbRoomService.getRbMissionEvaluationList(missionEvaluationQuery);

        Integer successMissionsCount = 0;
        Integer failMissionsCount = 0;
        Integer rejectMissionsCount = 0;
        Integer submitMissionCount = 0;
        Integer waitMissionsCount = 0;

        List<MissionEvaluationContext> taskSubmits = data.getRecords();
        for (MissionEvaluationContext taskSubmit : taskSubmits) {
            submitMissionCount++;

            if (CrTaskSubmit.EvaluateStatus.SUCCESS.getCode()
                    .equals(taskSubmit.getEvaluateStatus())) {
                successMissionsCount++;
            } else if (CrTaskSubmit.EvaluateStatus.FAIL.getCode()
                    .equals(taskSubmit.getEvaluateStatus())) {
                failMissionsCount++;
            } else if (CrTaskSubmit.EvaluateStatus.REJECT.getCode()
                    .equals(taskSubmit.getEvaluateStatus())) {
                rejectMissionsCount++;
            } else {
                waitMissionsCount++;
            }
        }

        JSONObject result = new JSONObject();
        result.put("success_missions_count", successMissionsCount);
        result.put("fail_missions_count", failMissionsCount);
        result.put("reject_missions_count", rejectMissionsCount);
        result.put("submit_mission_count", submitMissionCount);
        result.put("wait_missions_count", waitMissionsCount);

        return new BaseRsp(result);
    }

    /**
     * 查询红蓝对抗学员用户列表
     *
     * @return
     */
    @ApiOperation("查询红蓝对抗学员用户列表")
    @GetMapping("/get_user_list")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<List<UserRole>> getUserList(
            @ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
        List<UserRole> data = evalRbRoomService.getUserList(resource_id);
        return new BaseRsp<>(data);
    }

    /**
     * 获取动态事件
     *
     * @return
     */
    @ApiOperation("获取学员动态事件")
    @GetMapping("/get_user_mission_dynamic")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<MissionDynamicRsp> getUserMissionDynamic(
            @ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
            @ApiParam(name = "student_id", value = "用户id", required = true) String student_id) {
        MissionDynamicRsp missionDynamicRsp = crScoreController.getUserMissionDynamic(resource_id, student_id);

        return new BaseRsp<>(missionDynamicRsp);
    }

    @ApiOperation("查询学员评分")
    @GetMapping("/student_score")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<StudentScoreRsp> getStudentScore(
            @ApiParam(name = "resource_id", value = "评估id", required = true) String resource_id,
            @ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {

        StudentScoreRsp studentScoreRsp = evalRbRoomService.getStudentScore(resource_id, user_hash);
        return new BaseRsp<>(studentScoreRsp);
    }

    @ApiOperation("提交学员评分")
    @PostMapping("/student_score")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp updateStudentScore(@ApiParam(name = "studentScoreReq", value = "学员评分") @RequestBody EvalStudentScoreReq studentScoreReq) {
        evalRbRoomService.updateStudentScore(studentScoreReq);
        return new BaseRsp<>();
    }

    /**
     * 红方/蓝方成绩页面 右侧下折线图 得分趋势
     * 队伍得分趋势统计
     *
     * @return
     */
    @ApiOperation("队伍得分趋势统计")
    @GetMapping("/team_score_progress")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getRbScoreProgress(
            @ApiParam(name = "eval_template_id", value = "评估id", required = true) @RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true)@RequestParam("role_type") Integer roleType) {

        if (CrRole.RbRoleGroup.RED_BLUE.equals(roleType)) {
            return new BaseRsp(getRbScoreProgress_6(roomId, roleType, evalTemplateId));
        }
        JSONObject data = evalRbRoomService.getRbScoreProgress(evalTemplateId, roomId, roleType);
        return new BaseRsp(data);
    }

    private JSONObject getRbScoreProgress_6(String roomId, Integer roleType, String templateId) {
        return evalRbRoomService.getAllRbScoreProgress(roomId, roleType, templateId);
    }

    /**
     * 红蓝实时战况页面 左右两侧 任务分类 柱状图
     * 获取实例下的角色任务分类分布数据
     *
     * @return
     */
    @ApiOperation("获取实例下的角色任务分类分布数据")
    @GetMapping("/team_members_ranking")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getRbMembersRanking(
            @ApiParam(name = "eval_template_id", value = "评估id", required = true) @RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true)@RequestParam("role_type") Integer roleType) {

        JSONObject data = evalRbRoomService.getRbMembersRanking(evalTemplateId, roomId, roleType);
        return new BaseRsp(data);
    }

    /**
     * 红方/蓝方成绩页面 右侧中折线图 任务进展
     * 红蓝实时战况页面 左右两侧 任务进展
     * 队内任务下发和完成任务数进展
     *
     * @return
     */
    @ApiOperation("获取任务下发和完成任务数进展")
    @GetMapping("/team_mission_progress")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getRbMissionProgress(
            @ApiParam(name = "eval_template_id", value = "评估id", required = true) @RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {

        JSONObject data = evalRbRoomService.getRbMissionProgress(evalTemplateId, roomId, role_type);
        return new BaseRsp(data);
    }

    /**
     * 红蓝实时战况页面 左右两侧 任务分类 柱状图
     * 获取实例下的角色任务分类分布数据
     *
     * @return
     */
    @ApiOperation("获取实例下的角色任务分类分布数据")
    @GetMapping("/team_mission_category")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<XYData> getRbMissionCategory(
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
        XYData data =  evalRbRoomService.getRbMissionCategory(roomId, role_type);
        return new BaseRsp(data);
    }

    /**
     * 红蓝实时战况页面 左右两侧 任务分类 柱状图
     * 获取实例下的角色任务分类分布数据
     *
     * @return
     */
    @ApiOperation("获取实例下的任务评估数据统计")
    @GetMapping("/team_mission_evaluation")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getRbMissionEvaluation(
            @ApiParam(name = "eval_template_id", value = "评估id", required = true) @RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam("role_type") Integer roleType) {

        JSONObject data = evalRbRoomService.getRbMissionEvaluation(evalTemplateId,roomId, roleType);
        return new BaseRsp(data);
    }

    /**
     * 指定队伍（红/蓝）排行榜 得分排名 任务进展排名
     *
     * @return
     */
    @ApiOperation("指定队伍（红/蓝）排行榜 得分排名")
    @GetMapping("/team_leader_board")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getRbLeaderBoard(
            @ApiParam(name = "eval_template_id", value = "评估id", required = true) @RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam("role_type") Integer roleType) {

        JSONObject data = evalRbRoomService.getRbLeaderBoard(evalTemplateId, roomId, roleType);
        return new BaseRsp(data);
    }

    @ApiOperation("获取红蓝对抗队伍得分")
    @GetMapping("/score")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<RbSituationScoreRsp> score(
            @ApiParam(name = "eval_template_id", value = "评估id", required = true) @RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
        return new BaseRsp<>(evalRbRoomService.getTeamScore(evalTemplateId, roomId, roleType));
    }

    @ApiOperation("攻击方式统计")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_EVALUATION, MenuConstant.WEB_CR_RED_VS_BLUE}, logical = Logical.OR)
    @GetMapping("/event/count")
    public BaseRsp<List<EvalEventCountBar>> getEventCount(
            @ApiParam(name = "red_vs_blue_id", value = "房间id", required = true) @RequestParam("red_vs_blue_id") String redVsBlueId,
            @RequestParam("role") @NotEmpty Integer type) {
       return new BaseRsp<>(evalResultService.getEventCount(redVsBlueId,type));
    }

    @ApiOperation("评估结果详情-攻防事件-环形图")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/event/percent")
    public BaseRsp<List<EvalEventCountRingGraph>> getEventPercent(
            @ApiParam(name = "red_vs_blue_id", value = "房间id", required = true) @RequestParam("red_vs_blue_id") String redVsBlueId,
            @RequestParam("role") @NotEmpty Integer type) {
        return new BaseRsp<>(evalResultService.getEventPercent(redVsBlueId,type));
    }

    @ApiOperation("当前练习过程的基本信息")
    @GetMapping("/getRbRoom")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<RbRoomRsp> getRbRoom(@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

        String userId = UserInfoContext.getUserContext().getUserId();
        RbRoomData rbRoomData =  evalRbRoomService.getRbRoomDataByRoomId(resource_id);

        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setRoomId(rbRoomData.getResourceId());

        List<CrEvalRole> crRoles = crEvalRoleMapper.selectCrRoleByRoomId(roleQuery);

        RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);
        rbRoomRsp.setRolePermission(getSelfRoleUsers(crRoles, userId));
        rbRoomRsp.setRolesData(getRbRoomRoleRes(crRoles));
        rbRoomRsp.setPreSkill(JsonUtil.toJsonObjectArray(rbRoomData.getPreSkill(), String.class));
        rbRoomRsp.setTrainSkill(
                JsonUtil.toJsonObjectArray(rbRoomData.getTrainSkill(), String.class));

        return new BaseRsp(rbRoomRsp);
    }

    /** 先获取实例列表中自己的角色，再获取所有角色(查看列表用到)
     * @param crRoles
     * @param userId
     * @return
     */
    private BaseRole getSelfRoleUsers(List<CrEvalRole> crRoles, String userId) {
        BaseRole baseRole = new BaseRole();
        List<RoleUsers> roleUsersList = Lists.newArrayList();
        crRoles.forEach(crRole -> {
            if (crRole.containsUser(userId)) {
                baseRole.setResourceId(crRole.getResourceId());
                baseRole.setName(crRole.getName());
                baseRole.setParentHash(crRole.getRoleGroup());
                baseRole.setDescription(crRole.getDescription());
                baseRole.setRoleGroup(crRole.getRoleGroup());
            }

            RoleUsers roleUsers = new RoleUsers();
            roleUsers.setRoleName(crRole.getName());
            roleUsers.setRoleGroup(crRole.getRoleGroup());
            String userNamesByRoleUsers = getUserNamesByRoleUsers(crRole.getUserIdList());
            roleUsers.setUsers(JsonUtil.toJsonObjectArray(userNamesByRoleUsers, String.class));

            roleUsersList.add(roleUsers);

        });

        ChartInfo chartInfo = new ChartInfo();
        chartInfo.setInfoType(ChartInfo.ChartInfoType.NOT_RED_OR_BLUE_ONGOING.getCode());
        chartInfo.setRoleDataList(roleUsersList);

        baseRole.setChartInfo(chartInfo);

        return baseRole;
    }

    public String getUserNamesByRoleUsers(List<String> userList) {
        List<String> userNames = Lists.newArrayList();

        List<UserInfo> userInfoList = remoteUserService.getByIdList(userList);
        userInfoList.forEach(userInfo -> {
            if (userList.contains(userInfo.getUserId())) {
                userNames.add(userInfo.getUserName());
            }
        });

        return JsonUtil.toJsonString(userNames);
    }

    private List<RbRoomRoleRes> getRbRoomRoleRes(List<CrEvalRole> crRoles) {
        List<RbRoomRoleRes> rbRoomRoleResList = Lists.newArrayList();
        List<RbRoomRoleRes> redRoles = Lists.newArrayList();
        List<RbRoomRoleRes> blueRoles = Lists.newArrayList();

        for (CrEvalRole crRole : crRoles) {
            RbRoomRoleRes rbRoomRoleRes = BeanUtils.copy(crRole, RbRoomRoleRes.class);
            rbRoomRoleRes.setUsersData(getUserDatas(crRole.getUserIdList()));
            rbRoomRoleRes.setUsers(crRole.getUserIdList());
            rbRoomRoleRes.setRoleGroup(crRole.getRoleGroup());

            if (crRole.isRedStudent()) {
                redRoles.add(rbRoomRoleRes);
            } else if (crRole.isBlueStudent()) {
                blueRoles.add(rbRoomRoleRes);
            } else {
                rbRoomRoleResList.add(rbRoomRoleRes);
            }
        }
        RbRoomRoleRes redRoleRes = new RbRoomRoleRes();
        redRoleRes.setName(CrRole.RbRoleGroup.RED.getDesc());
        redRoleRes.setResourceId("redResourceId");
        redRoleRes.setRoleGroup(CrRole.RbRoleGroup.RED.getCode());
        redRoleRes.setChildren(redRoles);
        rbRoomRoleResList.add(redRoleRes);

        RbRoomRoleRes blueRoleRes = new RbRoomRoleRes();
        blueRoleRes.setName(CrRole.RbRoleGroup.BLUE.getDesc());
        blueRoleRes.setResourceId("blueResourceId");
        blueRoleRes.setRoleGroup(CrRole.RbRoleGroup.BLUE.getCode());
        blueRoleRes.setChildren(blueRoles);
        rbRoomRoleResList.add(blueRoleRes);

        return rbRoomRoleResList;
    }

    public List<UserData> getUserDatas(List<String> userIdList) {
        List<UserData> userDataList = Lists.newArrayList();
        List<UserInfo> userInfoList = remoteUserService.getByIdList(userIdList);
        for (UserInfo userInfo : userInfoList) {
            UserData userData = new UserData();
            userData.setResourceId(userInfo.getUserId());
            userData.setRepName(userInfo.getName());
            userData.setLogo(userInfo.getAvatarUrl());
            userDataList.add(userData);
        }
        return userDataList;
    }


    /**
     * 查询红蓝实时战况页面 正中间计时器
     * 获取实例运行时间和状态
     * @return
     */
    @ApiOperation("获取实例运行时间和状态")
    @GetMapping("/rb_time_data")
    @RequiresPermissions( MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<TimeDataRsp> getRbTimeData(
            @ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
        TimeData data =  evalRbRoomService.getRbTimeData(resource_id);

        TimeDataRsp timeDataRsp = new TimeDataRsp();
        timeDataRsp.setStartTime((int) (data.getStartTime().getTime() / 1000));
        timeDataRsp.setTimeStamp((int) (System.currentTimeMillis() / 1000));
        timeDataRsp.setRunning(data.getRunning());
        timeDataRsp.setStatus(data.getStatus());

        return new BaseRsp(timeDataRsp);
    }

}
