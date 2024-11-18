package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.service.CrBadgeService;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionRsp;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.redvsblue.controller.req.RbMissionEvaluationListReq;
import com.cyberpeace.cr.redvsblue.service.RbRefereeService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.evaluation.RbRoleIdNameType;
import com.cyberpeace.cs.service.context.BadgeBo;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * RB Room 裁判控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Api(tags = "红蓝对抗裁判")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/referee/")
public class RbRoomRefereeController {

    /**
     * RB房间Service
     *
     * @see RbRoomService
     */
    @Autowired
	private RbRoomService rbRoomService;

    /**
     * 靶场任务Service
     *
     * @see CrTaskService
     */
    @Autowired
	private CrTaskService crTaskService;

    /**
     * 远程用户Service
     *
     * @see RemoteUserService
     */
    @Autowired
	private RemoteUserService remoteUserService;

    /**
     * 靶场常见Service
     *
     * @see CrCommonService
     */
    @Autowired
	private CrCommonService crCommonService;

    /**
     * RB裁判Service
     *
     * @see RbRefereeService
     */
    @Autowired
	private RbRefereeService rbRefereeService;

    /**
     * 靶场徽章Service
     *
     * @see CrBadgeService
     */
    @Autowired
	private CrBadgeService crBadgeService;

    /**
     * 靶场角色Service
     *
     * @see CrRoleService
     */
    @Autowired
	private CrRoleService crRoleService;

    /**
     * 获取角色列表
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<List<RbRoleIdNameType>> }
     * @throws
     * @see String
     * @see BaseRsp<List<RbRoleIdNameType>>
     */
    @ApiOperation("获取角色列表")
	@GetMapping("/roles")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbRoleIdNameType>> getRoleList(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id) {
		List<RbRoleIdNameType> res = rbRefereeService.getRoleList(resource_id);
		return new BaseRsp<>(res);
	}

    /**
     * 获取评估列表
     *
     * @param req 请求体
     * @return {@code BaseRsp<List<EvaluationListRsp>> }
     * @throws
     * @see EvaluationListReq
     * @see BaseRsp<List<EvaluationListRsp>>
     */
    @ApiOperation("获取学员的评估列表")
	@GetMapping("/evaluation/list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<EvaluationListRsp>> getEvaluationList(@Validated EvaluationListReq req) {
		// 鉴权
		rbRoomService.checkRefereePermission(req.getRoom_id(),
				UserInfoContext.getUserContext().getUserId());
		// get evaluation list
		List<EvaluationListRsp> res = rbRefereeService.getEvaluationList(req);
		return new BaseRsp<>(res);
	}

    /**
     * 获取评估详情
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<EvaluationMissionRsp> }
     * @throws
     * @see String
     * @see BaseRsp<EvaluationMissionRsp>
     */
    @ApiOperation("获取学员单个评估和任务详情，用于裁决页面")
	@GetMapping("/evaluation/detail")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<EvaluationMissionRsp> getEvaluationDetail(
			@ApiParam(name = "resource_id", value = "评估编号", required = true) String resource_id) {
		EvaluationMissionRsp res = crTaskService.getEvaluationById(resource_id);
		return new BaseRsp(res);
	}

    /**
     * 5.4弃用
     * 更换为不分页的评估列表接口
     *
     * @param missionEvaluationListReq 任务评价列表请求体
     * @return {@code BaseRsp<List<MissionEvaluationRsp>>}
     * @throws
     * @see RbMissionEvaluationListReq
     * @see BaseRsp<List<MissionEvaluationRsp>>
     */
    @ApiOperation("获取学员的评估提交记录-弃用")
	@GetMapping("/mission_evaluation")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MissionEvaluationRsp>>
			getMissionEvaluations(@Validated RbMissionEvaluationListReq missionEvaluationListReq) {

		MissionEvaluationQuery missionEvaluationQuery = BeanUtils.copy(missionEvaluationListReq,
				MissionEvaluationQuery.class);
		missionEvaluationQuery.setPer_page(missionEvaluationListReq.getPerPage());
		missionEvaluationQuery.setStudentHash(missionEvaluationListReq.getSubmitUser());

		rbRoomService.checkRefereePermission(missionEvaluationListReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		IPage<MissionEvaluationContext> data = crTaskService
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

    /**
     * 查询红蓝对抗学员用户列表
     *
     * @param resource_id 资源id
     * @return
     * @throws
     * @see String
     * @see BaseRsp<List<UserRole>>
     */
    @ApiOperation("查询红蓝对抗学员用户列表")
	@GetMapping("/get_user_list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<UserRole>> getUserList(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		List<UserRole> data = rbRoomService.getUserList(resource_id);

		return new BaseRsp<>(data);
	}

    /**
     * 获取统计数据
     *
     * @param resource_id 资源id
     * @return
     * @throws
     * @see String
     * @see BaseRsp<TaskStatisticsInfo>
     */
    @ApiOperation("获取统计数据")
	@GetMapping("/get_statistical_info")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TaskStatisticsInfo> getStatisticalInfo(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		TaskStatisticsInfo res = crTaskService.getTaskStatisticsInfoByRoomId(resource_id);
		return new BaseRsp<>(res);
	}

    /**
     * 更新提交评估
     *
     * @param submitEvaluationReq 提交评估请求体
     * @return {@code BaseRsp<String> }
     * @throws
     * @see SubmitEvaluationReq
     * @see BaseRsp<String>
     */
    @ApiOperation("提交任务的人工评估")
	@PostMapping("/submit_evaluation")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String> updateSubmitEvaluation(@ApiParam(name = "submitEvaluationReq",
			value = "学员评分") @RequestBody SubmitEvaluationReq submitEvaluationReq) {
		rbRoomService.checkRoomOpening(submitEvaluationReq.getRoomId());
		rbRoomService.checkRefereePermission(submitEvaluationReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		crTaskService.submitEvaluation(submitEvaluationReq.getSubmitId(),
				submitEvaluationReq.getScore(), submitEvaluationReq.getDetail(),
				submitEvaluationReq.getReason(), submitEvaluationReq.getEvaluateStatus(),
				UserInfoContext.getUserContext().getUserId());

		crTaskService.pushMissionWS(submitEvaluationReq.getRoomId(),
				MissionWsCode.RVB_MISSION_CODE);
		return new BaseRsp<>();
	}

    /**
     * 获取会话
     *
     * @param room_id 房间id
     * @return {@code BaseRsp<List<MonitorSession>> }
     * @throws
     * @see String
     * @see BaseRsp<List<MonitorSession>>
     */
    @ApiOperation("获取监控")
	@GetMapping("/sessions")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MonitorSession>> getSessions(
			@ApiParam(name = "room_id", value = "训练实例id", required = true) String room_id) {
		CrCommonValidator.validateId(room_id);
		List<MonitorSession> res = rbRefereeService.getMonitorSessions(room_id);
		return new BaseRsp<>(res);
	}

    /**
     * 获取得分列表
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<List<StudentScoreListRsp>> }
     * @throws
     * @see String
     * @see BaseRsp<List<StudentScoreListRsp>>
     */
    @ApiOperation("获取评分列表")
	@GetMapping("/score/list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<StudentScoreListRsp>> getScoreList(
			@ApiParam(name = "resource_id", value = "训练房间id", required = true) String resource_id) {
		rbRoomService.checkRefereePermission(resource_id,
				UserInfoContext.getUserContext().getUserId());
		List<StudentScoreListRsp> res = rbRefereeService.getScoreList(resource_id);
		return new BaseRsp<>(res);
	}

    /**
     * 如果显示评估徽章
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<BadgeBo> }
     * @throws
     * @see String
     * @see BaseRsp<BadgeBo>
     */
    @ApiOperation("任务裁决红点是否显示")
	@GetMapping("/evaluation/badge")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<BadgeBo> ifShowEvaluationBadge(
			@ApiParam(name = "resource_id", value = "训练房间id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		BadgeBo res = crBadgeService.getEvaluationBadge(resource_id);
		return new BaseRsp<>(res);
	}

    /**
     * 点击评估徽章
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp }
     * @throws
     * @see String
     * @see BaseRsp
     */
    @ApiOperation("任务裁决红点点击取消")
	@GetMapping("/evaluation/badge/click")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp clickEvaluationBadge(
			@ApiParam(name = "resource_id", value = "训练房间id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		crBadgeService.clickEvaluationBadge(resource_id);
		return new BaseRsp<>();
	}

}
