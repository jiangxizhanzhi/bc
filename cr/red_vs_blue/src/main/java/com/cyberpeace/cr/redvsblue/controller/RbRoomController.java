package com.cyberpeace.cr.redvsblue.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseStageInfo;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomModifyReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateListReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTimeManagementReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTimeManagementRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbDeviceService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.RbDeviceContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RoomRole;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "红蓝对抗实例")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue/")
public class RbRoomController {

    @Autowired
    private RbRoomService rbRoomService;

    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private RemoteTopoService remoteTopoService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RbTimeManagementService rbTimeManagementService;

    @Autowired
    private RbSceneService rbSceneService;

    @Autowired
    private RbDeviceService rbDeviceService;

    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private RbTemplateMapper rbTemplateMapper;

    @Autowired
    private RbRoomMapper rbRoomMapper;

	@Autowired
	private TrainRecordService trainRecordService;

	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp create(@RequestBody @Validated RbRoomReq rbRoomReq) {
		// 校验
		rbRoomReq.validate();
    @Autowired
    private CrScoreService crScoreService;

    @Autowired
    private CrConstantsService crConstantsService;

    @ApiOperation("新增")
    @PostMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbRoomRsp> create(@RequestBody @Validated RbRoomReq rbRoomReq) {
        // 校验
        rbRoomReq.validate();

        RbRoomContext rbRoomContext = BeanUtils.copy(rbRoomReq, RbRoomContext.class);
        rbRoomContext
                .setPreSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getPreSkill(), String.class));
        rbRoomContext
                .setTrainSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getTrainSkill(), String.class));
        rbRoomContext.setRoleConfig(
                JsonUtil.toJsonObjectArray(rbRoomReq.getRoleConfig(), RoomRole.class));

        RbRoom rbRoom = rbRoomService.create(rbRoomContext);

        remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
                OperationType.CREATE, rbRoomReq.getName(),
                LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.RB_TRAIN_ROOM.getMsg(),
                        rbRoomReq.getName()));

        return getRbRoom(rbRoom.getResourceId());
    }

    @ApiOperation("查询")
    @GetMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbRoomRsp> getRbRoom(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId) {
        crRoleService.checkPermission(resourceId, UserInfoContext.getUserContext().getUserId());
        RbRoomRsp rsp = rbRoomService.getRoomRsp(resourceId);
        return new BaseRsp<>(rsp);
    }

    @ApiOperation("查询当前用户角色")
    @GetMapping("get_role")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<BaseRole> getRbRoomRole(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId) {
        CrCommonValidator.validateId(resourceId);
        CrRole role = crRoleService.checkPermission(resourceId,
                UserInfoContext.getUserContext().getUserId());
        BaseRole res = new BaseRole();
        res.setRoleGroup(role.getRoleGroup());
        return new BaseRsp<>(res);
    }

    @ApiOperation("训练说明")
    @GetMapping("train_introduction")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbRoomRsp> getTrainIntroduction(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId) {
        RbRoomData rbRoomData = rbRoomService.getRbRoomDataByRoomId(resourceId);

        RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);

        String copyedTopoId = rbRoomData.getCopyedTopoId();
        List<String> copyedTopoIds = new ArrayList<>();
        copyedTopoIds.add(copyedTopoId);
        // 训练场景拓扑节点数量获取
        Map<String, TopoDigest> topologyNodesStats = remoteTopoService
                .batchGetTopoDigest(copyedTopoIds);

        rbRoomRsp.setNodesCount(
                topologyNodesStats.get(rbRoomData.getCopyedTopoId()).getNodeCounts());
        return new BaseRsp<>(rbRoomRsp);
    }

    @ApiOperation("列表查询")
    @GetMapping("/list")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<ListRsp<RbRoomRsp>> list(@Validated RbTemplateListReq listReq) {
        ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
        listQuery.setPer_page(listReq.getPerPage());
        ListRsp<RbRoomRsp> rsp = rbRoomService.listRoom(listQuery);
        return new BaseRsp<>(rsp);
    }

    /**
     * 查询团队训练学员用户列表
     *
     * @return
     */
    @ApiOperation("获取控制面板信息，好友信息")
    @GetMapping("/dashboard_info")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<DashboardInfo> getDashboardInfo(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId) {
        String userId = UserInfoContext.getUserContext().getUserId();
        CrRole crRole = rbRoomService.checkRoomPermission(resourceId, userId);

        DashboardInfo data = rbRoomService.getDashboardInfo(crRole, resourceId, userId);

        return new BaseRsp<>(data);
    }

    /**
     * 查询红蓝对抗学员用户列表
     *
     * @return
     */
    @ApiOperation("获取群组成员列表")
    @GetMapping("/members_info")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<JSONObject> getMembersInfo(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId,
            @ApiParam(name = "id", value = "角色id", required = true) String id) {
        String userId = UserInfoContext.getUserContext().getUserId();
        rbRoomService.checkRoomPermission(resourceId, userId);

        List<DashboardUser> data = rbRoomService.getMembersInfo(id, resourceId);
        JSONObject json = new JSONObject();
        json.put("list", data);

        return new BaseRsp<>(json);
    }

    @ApiOperation("红蓝对抗-训练手册")
    @GetMapping("/manual")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<Map<String, List<TrainTaskReleaseInfoRsp>>> getManual(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId) {
        if (StringUtils.isBlank(resourceId)) {
            log.error("resource_id_missing");
            throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
        }
        String userId = UserInfoContext.getUserContext().getUserId();
        CrRole crRole = rbRoomService.checkRoomPermission(resourceId, userId);
        if (!crRole.isRvbStaff()) {
            log.error("role is forbidden");
            throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
        }

        Map<String, List<TrainTaskReleaseInfoRsp>> res = new HashMap<>();
		List<TrainTaskReleaseInfoRsp> redManuals = crRoleService.getTrainManual(resourceId,
				RbRoleGroup.RED.getCode());
		redManuals = mergeStageByCreateTime(redManuals);
		List<TrainTaskReleaseInfoRsp> blueManuals = crRoleService.getTrainManual(resourceId,
				RbRoleGroup.BLUE.getCode());
        blueManuals = mergeStageByCreateTime(blueManuals);
        res.put("red", redManuals);
        res.put("blue", blueManuals);
        return new BaseRsp<>(res);
    }

    @ApiOperation("修改")
    @PutMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbRoomRsp> update(@RequestBody @Validated RbRoomModifyReq rbTrainRoomModifyReq) {
        // 校验
        rbTrainRoomModifyReq.validate();

        RbRoom rbRoom = rbRoomService.getById(rbTrainRoomModifyReq.getResourceId());

        if (rbRoom == null) {
            log.error("change red vs blue room error, room not exist, room_id: {}",
                    rbTrainRoomModifyReq.getResourceId());
            throw new ResourceNotFoundException(rbTrainRoomModifyReq.getResourceId());
        }
        RbRoomContext rbRoomContext = BeanUtils.copy(rbTrainRoomModifyReq, RbRoomContext.class);
        List<RoomRole> roomRoleList = JsonUtil
                .toJsonObjectArray(rbTrainRoomModifyReq.getRoleConfig(), RoomRole.class);
        rbRoomContext.setRoleConfig(roomRoleList);
        rbRoomContext.setPreSkill(
                JsonUtil.toJsonObjectArray(rbTrainRoomModifyReq.getPreSkill(), String.class));
        rbRoomContext.setTrainSkill(
                JsonUtil.toJsonObjectArray(rbTrainRoomModifyReq.getTrainSkill(), String.class));

        rbRoomService.updateRbRoom(rbRoomContext, rbRoom);

        rbRoom = rbRoomMapper.selectById(rbTrainRoomModifyReq.getResourceId());
        remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
                OperationType.UPDATE, rbRoom.getName(), LogContent.CR_FORMAT.format(
                        OperationType.UPDATE, LogContent.RB_TRAIN_ROOM.getMsg(), rbRoom.getName()));

        return getRbRoom(rbTrainRoomModifyReq.getResourceId());
    }

    @ApiOperation("删除")
    @DeleteMapping()
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<String> batchDelete(@RequestBody @Validated SceneOpsReq opsReq) {
        String roomId = opsReq.getRoomId();
        if (StringUtils.isBlank(roomId)) {
            log.error("delete red vs blue room error, room id is blank");
            throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
        }

        RbRoom rbRoom = rbRoomService.getById(roomId);
        remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
                OperationType.DELETE, rbRoom.getName(), LogContent.CR_FORMAT.format(
                        OperationType.DELETE, LogContent.RB_TRAIN_ROOM.getMsg(), rbRoom.getName()));

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rbSceneService.stopScene(roomId);
				crRoleService.clearRoomRoles(roomId);
				rbRoomService.removeById(roomId);
				trainRecordService.updateEndTimeByRoomId(roomId, new Date());
			}
		});
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                rbSceneService.stopScene(roomId);
                crRoleService.clearRoomRoles(roomId);
                rbRoomService.removeById(roomId);
            }
        });

        if (StringUtils.isNotBlank(rbRoom.getCopyedTopoId())) {
            try {
                remoteTopoService.deleteById(rbRoom.getCopyedTopoId());
            } catch (Exception e) {
                log.error("Red vs blue delete topo failed! room_id=[{}] topo_id=[{}]", roomId,
                        rbRoom.getCopyedTopoId());
            }
        }

        return new BaseRsp<>();
    }

    /**
     * 获取时间管理数据
     *
     * @return
     */
    @ApiOperation("获取时间管理数据")
    @GetMapping("/get_time_management")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbTimeManagementRsp> getTimeManagement(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId) {

        RbTimeManagement rbTimeManagement = rbTimeManagementService.getByRoomId(resourceId);
        if (rbTimeManagement == null) {
            return new BaseRsp<>(null);
        }
        RbTimeManagementRsp rbTimeManagementRsp = BeanUtils.copy(rbTimeManagement,
                RbTimeManagementRsp.class);

        // 计算当前天文时间
        long time = rbTimeManagement.getAstronomicalTime().getTime() + System.currentTimeMillis()
                - rbTimeManagement.getCreateTime().getTime();
        Date currentAstronomicalTime = new Date(time);

        rbTimeManagementRsp.setCurrentAstronomicalTime(currentAstronomicalTime);
        rbTimeManagementRsp.setCreateUser(rbTimeManagement.getCreateUserId());
        rbTimeManagementRsp.setModifyTime(rbTimeManagement.getUpdateTime());
        rbTimeManagementRsp.setModifyUser(rbTimeManagement.getUpdateUserId());
        rbTimeManagementRsp.setRedVsBlue(rbTimeManagement.getRoomId());
        // 计算当前作战时间
        if (rbTimeManagement.isStopped() || rbTimeManagement.isPaused()) {
            rbTimeManagementRsp.setCurrentCombatTime(rbTimeManagement.getCombatTime());
        } else {
            time = rbTimeManagement.getCombatTime().getTime()
                    + (System.currentTimeMillis() - rbTimeManagement.getUpdateTime().getTime())
                    * rbTimeManagement.getRate();
            Date currentCombatTime = new Date(time);
            rbTimeManagementRsp.setCurrentCombatTime(currentCombatTime);
        }

        return new BaseRsp<>(rbTimeManagementRsp);
    }

    /**
     * 创建时间管理
     *
     * @return
     */
    @ApiOperation("创建时间管理")
    @PostMapping("/create_time_management")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<RbTimeManagementRsp>
    addTimeManagement(@RequestBody @Validated RbTimeManagementReq rbTimeManagementReq) {
        String userId = UserInfoContext.getUserContext().getUserId();
        rbRoomService.checkDirectorPermission(rbTimeManagementReq.getRoomId(), userId);

        if (rbTimeManagementService.getByRoomId(rbTimeManagementReq.getRoomId()) != null) {
            log.error("Rbtrain has begun！roleId=[{}]", rbTimeManagementReq.getRoomId());
            throw new BusinessException(CrBusinessErrorCode.TRAIN_HAS_BEGUN);

        }

        Integer rate = rbTimeManagementReq.getRate();
        if (rate > RbTimeManagement.RateStatus.FIVE.getCode()
                || rate < RbTimeManagement.RateStatus.ONCE.getCode()) {
            log.error("Rate Value Error！rate=[{}]", rate);
            throw new BusinessException(CrBusinessErrorCode.RATE_VALUE_RANGE_ERROR);
        }
        // 创建时间管理
        RbTimeManagement timeManagement = BeanUtils.copy(rbTimeManagementReq,
                RbTimeManagement.class);

        RbRoom rbRoom = rbRoomService.getById(rbTimeManagementReq.getRoomId());

        rbTimeManagementService.addTimeManagement(timeManagement, rbRoom,
                RbTimeManagement.TimeManagementStatus.START.getCode(), userId);

        RbTimeManagementRsp rbTimeManagementRsp = BeanUtils.copy(timeManagement,
                RbTimeManagementRsp.class);
        rbTimeManagementRsp.setRedVsBlue(timeManagement.getRoomId());

        // 记录远程操作日志
        remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
                OperationType.START, rbRoom.getName());

        return new BaseRsp<>(rbTimeManagementRsp);
    }

    @ApiOperation("获取设备占用情况")
    @GetMapping("/device")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbDeviceContext>> getDevices(
			@ApiParam(value = "room_id", name = "红蓝对抗id", required = true) @RequestParam(
					value = "room_id", required = true) String roomId,
			@ApiParam(value = "standard_device", name = "主机", required = false) @RequestParam(
					value = "standard_device", required = false) String standardDevice) {

		List<RbDeviceContext> ret = rbDeviceService.getByRoomIdAndOptionalDevice(roomId,
				standardDevice);

        return new BaseRsp<>(ret);
    }

    @ApiOperation("红蓝对抗-任务导调-训练报告")
    @GetMapping("/mission/getTrainReports")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public BaseRsp<TrainReportContext> getTrainReports(
            @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                    value = "resource_id", required = true) String resourceId,
            @ApiParam(name = "scoreCount", value = "节点数", required = true) Integer scoreCount) {
        //开始时间  结束时间从训练记录表取
		RbTimeManagement management = rbTimeManagementService.getByRoomId(resourceId);
		RbRoom rbRoom = rbRoomService.getById(resourceId);
        if (scoreCount == null || scoreCount == 0) {
            scoreCount = 10;
        }
        ScoreTrendReq scoreTrendReq = initScoreTrendReq(management,
                rbRoom.getStartTime(), rbRoom.getEndTime(), scoreCount, rbRoom.getReplaySceneId());
        TrainReportContext trainReport = crScoreService.getTrainReport(resourceId, Lists
                        .newArrayList(RbRoleGroup.RED.getCode(), RbRoleGroup.BLUE.getCode()),
                scoreTrendReq);
        //训练未开始，训练时间显示的是创建训练填写的训练开始时间，训练时长是训练模板设置的推荐时长
        //训练已开始，训练时间显示的启动训练时间，训练时长是当前时间-启动训练时间
        //训练已结束，训练时间显示的启动训练时间，训练时长是结束训练-启动训练时间
        rbRoom.setStartTime(scoreTrendReq.getStartTime());
        rbRoom.setEndTime(scoreTrendReq.getEndTime());
        initRbReport(rbRoom, trainReport);
        if (management == null) {
            String timeCost = trainReport.getRoomBaseInfo().getDurationTime() == null
                    ? CommonConstant.SHORT_TRANSVERSE_LINE
                    : trainReport.getRoomBaseInfo().getDurationTime()
                    + TrainingReport.TIME.HOUR.getMsg();
            trainReport.getRoomBaseInfo().setTimeCost(timeCost);
        }
        return new BaseRsp<>(trainReport);
    }

    public ScoreTrendReq initScoreTrendReq(RbTimeManagement management, Date roomStartTime, Date roomEndTime, Integer scoreCount, String replaySceneId) {
        ScoreTrendReq res = new ScoreTrendReq();
        if (management != null) {
            res.setStartTime(management.getCreateTime());
            if (RbTimeManagement.TimeManagementStatus.STOP.getCode().equals(management.getStatus())) {
                res.setEndTime(management.getUpdateTime() == null ? new Date() : management.getUpdateTime());
            } else {
                res.setEndTime(new Date());
            }

        } else {
            res.setStartTime(roomStartTime);
            res.setEndTime(roomEndTime);
        }

        res.setScoreCount(scoreCount);
        res.setReplaySceneId(replaySceneId);
        return res;
    }

    @ApiOperation("红蓝对抗-任务导调-训练报告导出")
    @GetMapping("/mission/TrainReportsPdf")
    @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
    public void trainReportsPdf(HttpServletResponse httpServletResponse,
                                @ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
                                        value = "resource_id", required = true) String resourceId,
                                @ApiParam(name = "scoreCount", value = "节点数", required = true) Integer scoreCount) {
        TrainReportContext originData = this.getTrainReports(resourceId, scoreCount).getData();
        TrainReportExportContext data = BeanUtils.deepCopy(originData,
                TrainReportExportContext.class);
        data.initExport();
        Path destHtmlPathObj = Paths.get(AttachmentPath.REDVSBLUE.getDesc(),
                String.format("%s-%s", resourceId, UserInfoContext.getUserContext().getUserId()),
                String.format("%s.html", resourceId));
        FileUtil.copyDirectory(HtmlTemplateEnum.REDBLUE.getTemplatePath() + "/img",
                AttachmentPath.REDVSBLUE.getDesc(), true);
        FileUtil.copyDirectory(HtmlTemplateEnum.REDBLUE.getTemplatePath() + "/js",
                AttachmentPath.REDVSBLUE.getDesc(), true);
        // 临时存放目录
        File directory = destHtmlPathObj.getParent().toFile();
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                log.error("failed_to_make_dir: Path = [{}]", directory.getPath());
                throw new BusinessException(ErrorCode.FAILED_TO_MAKE_DIR, directory.getPath());
            }
        }
        String destHtmlPath = destHtmlPathObj.toString();
        try {
            // 生成html
            HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.REDBLUE, destHtmlPath);

            // 在同目录下生成pdf
            String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
                    + "pdf";
            HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);

            FileUtil.setResponse(new File(destPdfPath), false, httpServletResponse);

        } catch (Exception e) {
            log.error("failed to export pdf: {}", destHtmlPath);
            throw e;
        } finally {
			if (Boolean.FALSE.equals(FileUtil.deleteDir(directory))) {
                log.error("failed to delete dir: {}", directory);
            }
        }

    }

    public void initRbReport(RbRoom rbRoom, TrainReportContext trainReport) {
        // 基础信息
        RoomBaseInfo roomBaseInfo = BeanUtils.copy(rbRoom, RoomBaseInfo.class);
        roomBaseInfo.setPreSkills(rbRoom.getPreSkill());
        roomBaseInfo.setTrainSkills(rbRoom.getTrainSkill());
        RbTemplate rbTemplate = rbTemplateMapper.selectById(rbRoom.getTemplateId());
        if (rbTemplate != null) {
            roomBaseInfo.setDurationTime(rbTemplate.getDurationTime());
        }
        roomBaseInfo.initExportString();
        roomBaseInfo.setDifficultyString(crConstantsService.geCnNameByTypeAndCode(CrConstantsConfig.ConstantsType.DIFFICULTY.getCode(), roomBaseInfo.getDifficulty()));
        trainReport.setRoomBaseInfo(roomBaseInfo);
        List<UserRole> admins = crRoleService.getUserRolesByRoomIdAndRoleGroups(
                rbRoom.getResourceId(), Lists.newArrayList(RbRoleGroup.ADMIN.getCode()));
        List<UserRole> directors = crRoleService.getUserRolesByRoomIdAndRoleGroups(
                rbRoom.getResourceId(), Lists.newArrayList(RbRoleGroup.DIRECTOR.getCode()));
        List<UserRole> referees = crRoleService.getUserRolesByRoomIdAndRoleGroups(
                rbRoom.getResourceId(), Lists.newArrayList(RbRoleGroup.REFEREE.getCode()));
        trainReport.setAdminName(UserRole.getName(UserRole::getName, admins));
        trainReport.setDirectorName(UserRole.getName(UserRole::getName, directors));
        trainReport.setRefereesName(UserRole.getName(UserRole::getName, referees));
        // 红蓝得分趋势分组
        trainReport.initRbGetScoreRsps(true);

    }

    private List<TrainTaskReleaseInfoRsp>
    mergeStageByCreateTime(List<TrainTaskReleaseInfoRsp> manuals) {
        List<TrainTaskReleaseInfoRsp> res = new ArrayList<>(1);

        List<MissionReleaseStageInfo> allStages = new ArrayList<>();
        manuals.stream().filter(item -> CollectionUtils.isNotEmpty(item.getMissionStages()))
                .forEach(item -> allStages.addAll(item.getMissionStages()));
        if (CollectionUtils.isEmpty(allStages)) {
            return res;
        }
        Comparator<MissionReleaseStageInfo> comparator = (o1, o2) -> {
            if (o1 == null || CollectionUtils.isEmpty(o1.getMissionInfos())) {
                return 1;
            }
            if (o2 == null || CollectionUtils.isEmpty(o2.getMissionInfos())) {
                return -1;
            }
            Date o1Time = o1.getMissionInfos().get(0).getCreateTime();
            Date o2Time = o2.getMissionInfos().get(0).getCreateTime();

            if (o1Time.equals(o2Time)) {
                return 0;
            }

            return o1Time.before(o2Time) ? 1 : -1;
        };

        List<MissionReleaseStageInfo> sortedStages = allStages.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        TrainTaskReleaseInfoRsp taskReleaseInfoRsp = new TrainTaskReleaseInfoRsp();
        taskReleaseInfoRsp.setMissionStages(sortedStages);
        res.add(taskReleaseInfoRsp);
        return res;
    }
}
