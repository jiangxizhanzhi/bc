package com.cyberpeace.cr.evaluation.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.evaluation.controller.req.EvalMissionEvaluationListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalStudentScoreReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSubmitEvaluationReq;
import com.cyberpeace.cr.evaluation.service.EvalRbRoomService;
import com.cyberpeace.cr.evaluation.service.TrainEvalService;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbSituationScoreRsp;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.cr.redvsblue.service.context.XYData;
import com.cyberpeace.integratation.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    private TrainEvalService trainEvalService;

    @Autowired
    private CrTaskService crTaskService;

    @Autowired
    private RbRoomScoreService rbRoomScoreService;
    @Autowired
    private EvalRbRoomService evalRbRoomService;

    @Autowired
    private RbRoomService rbRoomService;

    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private CrScoreController crScoreController;

    @Autowired
    private RbSituationService rbSituationService;


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
            MissionEvaluationRsp missionEvaluationRsp = BeanUtils.copy(missionEvaluationContext,
                    MissionEvaluationRsp.class);

            MissionEvaluationRsp.SubmitInfo submitInfo = missionEvaluationRsp.new SubmitInfo();

            submitInfo.setCreateTime(missionEvaluationContext.getCreateTime());
            submitInfo.setUserAnswer(missionEvaluationContext.getAnswer());

            missionEvaluationRsp
                    .setEvaluatorName(userInfoMap.get(missionEvaluationContext.getEvaluatorId()));
            missionEvaluationRsp.setEvaluator(missionEvaluationContext.getEvaluatorId());
            missionEvaluationRsp.setSubmitUserRole(missionEvaluationContext.getSubmitUserRole());
            missionEvaluationRsp
                    .setSubmitUserRoleResourceId(missionEvaluationContext.getSubmitUserRoleId());
            missionEvaluationRsp
                    .setSubmitUserName(userInfoMap.get(missionEvaluationContext.getSubmitUser()));
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
        List<UserRole> data = rbRoomService.getUserList(resource_id);
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
        MissionDynamicRsp missionDynamicRsp = crScoreController.getUserMissionDynamic(resource_id,
                student_id);

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
    public BaseRsp updateStudentScore(@ApiParam(name = "studentScoreReq",value = "学员评分") @RequestBody EvalStudentScoreReq studentScoreReq) {
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
            @ApiParam(name = "eval_template_id", value = "评估id", required = true)@RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
        JSONObject data = evalRbRoomService.getRbScoreProgress(evalTemplateId,roomId, roleType);
        return new BaseRsp(data);
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
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();

//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);
        //todo  修改得分
        JSONObject data = evalRbRoomService.getRbMembersRanking(evalTemplateId, roomId, roleType);
        return new BaseRsp(data);
    }

    /**
     *  红方/蓝方成绩页面 右侧中折线图 任务进展
     *     红蓝实时战况页面 左右两侧 任务进展
     *     队内任务下发和完成任务数进展
     * @return
     */
    @ApiOperation("获取任务下发和完成任务数进展")
    @GetMapping("/team_mission_progress")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getRbMissionProgress(
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {

        JSONObject data = rbRoomScoreService.getRbMissionProgress(roomId, role_type);
        return new BaseRsp(data);
    }

    /**
     * 红蓝实时战况页面 左右两侧 任务分类 柱状图
     *     获取实例下的角色任务分类分布数据
     * @return
     */
    @ApiOperation("获取实例下的角色任务分类分布数据")
    @GetMapping("/team_mission_category")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<XYData> getRbMissionCategory(
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
        XYData data = rbRoomScoreService.getRbMissionCategory(roomId, role_type);
        return new BaseRsp(data);
    }

    /**
     * 红蓝实时战况页面 左右两侧 任务分类 柱状图
     *     获取实例下的角色任务分类分布数据
     * @return
     */
    @ApiOperation("获取实例下的任务评估数据统计")
    @GetMapping("/team_mission_evaluation")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<JSONObject> getRbMissionEvaluation(
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer role_type) {
        JSONObject data = rbRoomScoreService.getRbMissionEvaluation(roomId, role_type);
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
            @ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();
//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);
        //todo  修改得分
        JSONObject data = evalRbRoomService.getRbLeaderBoard(evalTemplateId,roomId, roleType);

        return new BaseRsp(data);
    }


    @ApiOperation("获取红蓝对抗队伍得分")
    @GetMapping("/score")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    public BaseRsp<RbSituationScoreRsp> score(
            @ApiParam(name = "eval_template_id", value = "评估id", required = true) @RequestParam("eval_template_id") String evalTemplateId,
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam("room_id") String roomId,
            @ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
        return new BaseRsp<>(evalRbRoomService.getTeamScore(evalTemplateId,roomId, roleType));
    }

}
