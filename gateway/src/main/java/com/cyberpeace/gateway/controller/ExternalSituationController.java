package com.cyberpeace.gateway.controller;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.json.JSONObject;
import com.cyberpeace.gateway.service.bo.CommandExecutor;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.gateway.controller.req.*;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.gateway.controller.res.DashboardTitleRes;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.dao.mapper.DashboardTemplateMapper;
import com.cyberpeace.situation.dao.mapper.SituationTopoMapper;
import com.cyberpeace.situation.model.outer.Command;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.gateway.controller.res.ControlRes;
import com.cyberpeace.gateway.controller.res.ScreenPathRes;
import com.cyberpeace.integratation.constant.OrderType;
import com.cyberpeace.integratation.model.situation.StatusOrderContext;
import com.cyberpeace.integratation.model.situation.TaskEventContext;
import com.cyberpeace.integratation.service.bo.situation.BooleanRes;
import com.cyberpeace.situation.constant.dashboard.PublicStatus;
import com.cyberpeace.situation.constant.dashboard.SituationInstanceStatus;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.model.ws.DashboardMessage;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsTaskStatus;
import com.cyberpeace.situation.service.SituationGisService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 外部接口-态势相关api
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Api(tags = "外部接口-态势相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/situation")
public class ExternalSituationController {

	@Autowired
	private UtilService utilService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	@Autowired
	private SituationGisService situationGisService;

	@Autowired
	private SituationTopoMapper situationTopoMapper;

	@Autowired
	private DashboardAttributeMapper dashboardAttributeMapper;

	private String CALL_BACK_URL;

	@PostConstruct
	public void init() {
		CALL_BACK_URL = "http://" + SystemPropUtil.getProp("PG_HOST") + ":" + SystemPropUtil.getProp("PG_PORT") + ConfigUtil.getContextProperty("pg.call.back");
	}

	/**
	 * 接收任务指挥命令
	 *
	 * @param controlCommandReq 指挥命令
	 * @return {@code BaseRsp<ControlRes>}
	 */
	@CrossOrigin("*")
	@ApiOperation("接收任务指挥命令")
	@PostMapping("control")
	public BaseRsp<ControlRes>
			controlCmd(@RequestBody @Validated ControlCommandReq controlCommandReq) {

		log.info(String.format("成功接收指令：%s", JsonUtil.toJsonString(controlCommandReq)));

		OrderType orderType = Arrays.stream(OrderType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(controlCommandReq.getOrderType())).findFirst()
				.orElse(OrderType.QT);

		List<SituationPublishInstance> situationPublishInstances = situationPublishInstanceMapper
				.selectDashboardBySubjectId(controlCommandReq.getTaskId(),
						controlCommandReq.getSubjectId());
		if (situationPublishInstances.isEmpty() || orderType.getAction() == null) {
			return new BaseRsp<>(new ControlRes("1", "0"));
		}
		situationPublishInstances.forEach(situationPublishInstance -> {
			sendMessage(orderType, situationPublishInstance);
		});
		return new BaseRsp<>(new ControlRes("1", "0"));
	}

	/**
	 * 推送消息至大屏
	 *
	 * @param orderType                命令类型
	 * @param situationPublishInstance 大屏实例
	 */
	private void sendMessage(OrderType orderType,
			SituationPublishInstance situationPublishInstance) {

		// 如果该命令类型需要推送消息弹窗，首先推送弹窗消息
		if (orderType.getAction().equals("message")) {
			WsMessage<DashboardMessage> message = new WsMessage<>(MessageType.Message.getDesc(),
					new DashboardMessage(
							situationPublishInstance.getTaskName() + orderType.getName(), 5));
			situationHandlerService.pushDataToDashboard(message,
					situationPublishInstance.getResourceId());
			return;
		}

		// 推送任务命令本身的消息
		WsMessage<WsTaskStatus> wsMessage = new WsMessage<>(MessageType.TaskStatus.getDesc(),
				new WsTaskStatus(orderType.getAction()));
		situationHandlerService.pushDataToDashboard(wsMessage,
				situationPublishInstance.getResourceId());
	}

	/**
	 * 接收可视化方案的新建、编辑
	 *
	 * @param schemeBo 可视化方案信息
	 * @return {@link BaseRsp}<{@link ScreenPathRes}>
	 */
	@CrossOrigin("*")
	@ApiOperation("接收可视化方案的新建、编辑")
	@PostMapping("/scheme")
	public BaseRsp<ScreenPathRes>
			receiveScheme(@RequestBody @Validated SituationSchemeBo schemeBo) {

		// 提取请求中的大屏ID参数
		String dashboardId = schemeBo.getDashboardId();

		// 传入大屏ID时，直接跳转编辑页面
		if (StringUtils.isNotBlank(dashboardId)) {
			return new BaseRsp<>(new ScreenPathRes(dashboardId));
		}

		// 判断是否存在已有想定大屏
		String xdDashboardId = situationPublishInstanceMapper
				.selectXdInstanceBySchemeId(schemeBo.getSchemeId());

		if (StringUtils.isNotBlank(xdDashboardId)) {
			return new BaseRsp<>(new ScreenPathRes(xdDashboardId));
		}

		// 如果没有找到大屏，进行新增
		SituationPublishInstanceAddReq situationPublishInstanceAddReq = BeanUtils.copy(schemeBo,
				SituationPublishInstanceAddReq.class);

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.insertOne(situationPublishInstanceAddReq, true);
		dashboardId = situationPublishInstance.getResourceId();
		log.info("成功接收可视化方案：{}", JsonUtil.toJsonString(schemeBo));

		return new BaseRsp<>(new ScreenPathRes(dashboardId));
	}

	/**
	 * 接收想定下发大屏指令
	 *
	 * @param statusOrderContextString 想定下发大屏指令
	 * @return {@link BaseRsp}<{@link ControlRes}>
	 */
	@CrossOrigin("*")
	@ApiOperation("接收想定下发大屏指令")
	@PostMapping("/emission")
	public BaseRsp<ControlRes>
			receiveEmission(@RequestBody @Validated String  statusOrderContextString ) {
		log.info("成功接收想定指令：{}", statusOrderContextString);

 		StatusOrderContext statusOrderContext = JsonUtil.toJsonObject(statusOrderContextString , StatusOrderContext.class);

		 if(statusOrderContext.getYwxtArr().contains(StatusOrderContext.KSH_SYS_ID)){
			 statusOrderContext.setKshfaid();
 			 SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
					 .selectXdDashboardBySchemeId(statusOrderContext.getKshfaid());
			 if (situationPublishInstance != null) {
				 PublicStatus publicStatus = PublicStatus
						 .getPublicStatusByName(statusOrderContext.getFazt().trim());
				 situationPublishInstanceService.changeInstancePublicStatus(situationPublishInstance,
						 publicStatus);
				 situationPublishInstanceService.updateById(situationPublishInstance);
			 }
		 }


		return new BaseRsp<>(new ControlRes("1", "0"));
	}

	/**
	 * 接收节点迁移信息
	 *
	 * @param req 节点迁移信息
	 * @return {@link BaseRsp}<{@link BooleanRes}>
	 */
	@CrossOrigin("*")
	@ApiOperation("接收节点迁移信息")
	@PostMapping("/migration/")
	public BaseRsp<BooleanRes> receiveMigration(@RequestBody @Validated NodeMigrationReq req) {

		BooleanRes res = new BooleanRes();
		res.setResult(true);

		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
				.selectXdDashboardBySubjectId(req.getTaskId(), req.getSubjectId());

		if (situationPublishInstance == null) {
			return new BaseRsp<>(res);
		}

		WsMessage<NodeMigrationReq> wsMessage = new WsMessage<>(MessageType.NodeMigration.getDesc(),
				req);
		situationHandlerService.pushDataToDashboard(wsMessage,
				situationPublishInstance.getResourceId());

		return new BaseRsp<>(res);
	}

	/**
	 * 接收第三方大屏绑定
	 *
	 * @param thirdDashboardReq 第三方大屏信息
	 * @return {@link BaseRsp}<{@link BooleanRes}>
	 */
	@CrossOrigin("*")
	@ApiOperation("接收第三方大屏绑定")
	@PostMapping("/third_dashboard/")
	public BaseRsp<BooleanRes> bindThirdDashboard(ThirdDashboardReq thirdDashboardReq) {

		// 上传图片文件
		FileUtil.validateFileSuffix(thirdDashboardReq.getThumbnail().getOriginalFilename(),
				FileTypeContant.PICTURE_TYPE);
		AttachmentBo attachmentBo = attachmentService.uploadFile(
				AttachmentPath.UPLOAD_IMAGE.getCode(), true, thirdDashboardReq.getThumbnail(),
				true);
		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());

		// 创建大屏实例
		SituationPublishInstance situationPublishInstance = BeanUtils.copy(thirdDashboardReq,
				SituationPublishInstance.class);
		situationPublishInstance.setThumbnail(viewPath);

		// 构造基本信息
		situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());
		situationPublishInstance.setPublicStatus(PublicStatus.COMPLETE.getCode());
		situationPublishInstance.setEdit(false);
		situationPublishInstance.setCategory("26c4cf546d1f4557ac29f9fa8e8a6c3a");
		situationPublishInstanceMapper.insert(situationPublishInstance);
		BooleanRes res = new BooleanRes();
		res.setResult(true);
		return new BaseRsp<>(res);
	}

	/**
	 * 接收任务实时动效
	 *
	 * @param context 任务实时动效
	 * @return {@link BaseRsp}<{@link Boolean}>
	 */
	@CrossOrigin("*")
	@ApiOperation("接收任务实时动效")
	@PostMapping("/event/task/")
	public BaseRsp<Boolean> eventTask(@RequestBody @Validated TaskEventContext context) {
		log.info(String.format("成功接收任务事件：%s", JsonUtil.toJsonString(context)));
		List<SituationPublishInstance> situationPublishInstances = situationPublishInstanceMapper
				.selectSynthesizeDashboard();
		situationPublishInstances.forEach(situationPublishInstance -> {
			WsMessage<TaskEventContext> wsMessage = new WsMessage<>(MessageType.TaskEvent.getDesc(),
					context);
			situationHandlerService.pushDataToDashboard(wsMessage,
					situationPublishInstance.getResourceId());
		});
		return new BaseRsp<>(true);
	}

	/**
	 * 地图静态资源鉴权
	 *
	 * @param response 响应
	 * @return {@link BaseRsp}<{@link Boolean}>
	 */
	@ApiOperation("地图静态资源鉴权")
	@GetMapping("/map/resource/auth/")
	public BaseRsp<Boolean> authMapResource(HttpServletResponse response) {
		if (Boolean.TRUE.equals(situationGisService.checkAuthorizationIp(CommonContext.getIp()))) {
			return new BaseRsp<>(true);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			log.error("地图静态资源鉴权失败,请求IP: {}", CommonContext.getIp());
			return new BaseRsp<>(false);
		}
	}

	/**
	 * 通过id和任务id，获取env_id
	 *
	 * @param subjectId 对象id
	 * @param taskId    任务id
	 * @return {@link BaseRsp}<{@link String}>
	 */
	@ApiOperation("根据subjectId和taskId返回envId")
	@GetMapping("/get_env_id")
	public BaseRsp<String> getEnvIdBySubjectIdAndTaskId(
			@ApiParam(required = true, name = "subject_id", value = "科目id") @RequestParam("subject_id") String subjectId,
			@ApiParam(required = true, name = "task_id", value = "任务id") @RequestParam("task_id") String taskId) {
		return new BaseRsp<>(situationTopoMapper.getEnvIdBySubjectIdAndTaskId(subjectId,taskId));
	}


	@ApiOperation("获取大屏title")
	@PostMapping("/get_title")
	public BaseRsp<Map<String, List<DashboardTitleRes>>>
	getDashboardTitle(@RequestBody String dashboardJson) {

		JSONObject object = JsonUtil.toJsonObject(dashboardJson, JSONObject.class);
		String dashboardId = object.getStr("dashboardId");
		// 获取属性对象
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(dashboardId);

		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId(situationPublishInstances.getAttributeId());
		DashboardTitleRes dashboardTitleRes = null;

		if (dashboardAttribute != null) {
			dashboardTitleRes = BeanUtils
					.deepCopy(dashboardAttribute, DashboardTitleRes.class);
		}
		Map<String, List<DashboardTitleRes>> map = new HashMap<>();
		List<DashboardTitleRes> dashboardTitleResList = new ArrayList<>();
		dashboardTitleResList.add(dashboardTitleRes);
		map.put("title", dashboardTitleResList);
		return new BaseRsp<>(map);
	}

	@ApiOperation("获取大屏time")
	@PostMapping("/get_time")
	public BaseRsp<Map<String, Long>>
	getDashboardTime(@RequestBody String dashboardJson) {

		JSONObject object = JsonUtil.toJsonObject(dashboardJson, JSONObject.class);
		String dashboardId = object.getStr("dashboardId");
		// 获取属性对象
		SituationPublishInstance situationPublishInstances = situationPublishInstanceMapper
				.selectById(dashboardId);

		if (null == situationPublishInstances) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId(situationPublishInstances.getAttributeId());

		Map<String, Long> map = new HashMap<>();

		map.put("exec_time", TimeUtil.getIntervalSeconds(dashboardAttribute.getStartTime(), new Date()) );
		map.put("least_time", TimeUtil.getIntervalSeconds(new Date(), dashboardAttribute.getEndTime()) );

		return new BaseRsp<>(map);
	}

	@ApiOperation("提示大屏接口进行刷新")
	@PostMapping("/refresh")
	public BaseRsp<Boolean> refresh(@RequestBody SituationSchemeBo situationSchemeBo) {
		List<String> dashboardIds = situationPublishInstanceMapper.getDashboardIdBySubjectIdAndTaskId(situationSchemeBo.getSubjectId(), situationSchemeBo.getTaskId());
		log.info("subjectId: [{}] taskId: [{}]",situationSchemeBo.getSubjectId(), situationSchemeBo.getTaskId());
		WsMessage<String> message = new WsMessage<>(MessageType.REFRESH.getDesc(), null);
		for (String dashboardId : dashboardIds) {
			situationHandlerService.pushDataToDashboard(message, dashboardId);
		}
		return new BaseRsp<>(true);
	}

	@ApiOperation("更新拓扑数据")
	@PostMapping("/update_topo")
	public BaseRsp<Boolean> topoRefresh(@RequestBody UpdateTopoInfoReq updateTopoInfoReq) {
		situationPublishInstanceService.updatePosition(updateTopoInfoReq.getSubRangeId(), updateTopoInfoReq.getTaskId(),
				updateTopoInfoReq.getSubjectId(), updateTopoInfoReq.getEnvId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("想定")
	@PostMapping("/xd")
	public BaseRsp<Command> execCommand(@RequestBody JSONObject jsonObject) {
		log.info("xd:[{}]", jsonObject.toString());
		Command result;
		String commandSubtype = jsonObject.getStr("commandSubtype");

		if (commandSubtype.equals("getPlanParams")) {
			//获取配置项
			result = situationPublishInstanceService.getTemplateList(jsonObject);
		} else if (commandSubtype.equals("getPlanStatus")) {
			//查看方案状态
			result = new CommandExecutor(jsonObject.getStr("configTaskId"), CALL_BACK_URL, "方案检测指令执行失败") {

				@Override
				protected Command doBusiness() {
					return situationPublishInstanceService.checkDashboard(jsonObject);
				}
			}.execute(jsonObject);
		} else if (commandSubtype.equals("setPlanInfo")) {
			//新增、修改可视化方案
			result = new CommandExecutor(jsonObject.getStr("configTaskId"), CALL_BACK_URL, "方案下发指令执行失败") {

				@Override
				protected Command doBusiness() {
					return situationPublishInstanceService.addDashboard(jsonObject);
				}
			}.execute(jsonObject);
		} else if (commandSubtype.equals("deletePlanInfo")) {
			//删除可视化方案
			result = new CommandExecutor(jsonObject.getStr("configTaskId"), CALL_BACK_URL, "方案删除指令执行失败") {

				@Override
				protected Command doBusiness() {
					return situationPublishInstanceService.deleteDashboard(jsonObject);
				}
			}.execute(jsonObject);
		} else {
			throw new BusinessException(SituationErrorCode.NOT_KNOWN_COMMAND);
		}
		return new BaseRsp<>(result);
	}

}
