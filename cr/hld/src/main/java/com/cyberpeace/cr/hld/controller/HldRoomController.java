package com.cyberpeace.cr.hld.controller;

import java.util.List;

import com.cyberpeace.cr.hld.controller.rsp.HldTaskRsp;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.hld.controller.req.HldRoomAddReq;
import com.cyberpeace.cr.hld.controller.req.HldRoomModifyReq;
import com.cyberpeace.cr.hld.controller.rsp.HldRoomIdRsp;
import com.cyberpeace.cr.hld.controller.rsp.HldRoomRsp;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cr.hld.service.context.HldRolesConfig;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import com.cyberpeace.cr.hld.service.context.HldTaskContext;
import com.cyberpeace.cr.hld.service.context.TaskRoleConfig;
import com.cyberpeace.integratation.service.HldTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "HLD房间")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/hld/rooms/")
public class HldRoomController {

	@Autowired
	private HldRoomService hldRoomService;

	@Autowired
	private HldRoomMapper hldRoomMapper;

	@Autowired
	private HldSceneService hldSceneService;

	@Autowired
	private HldTaskService hldTaskService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<HldRoomIdRsp> create(@RequestBody @Validated HldRoomAddReq addReq) {

		// 构造初始数据
		HldRoomContext hldRoomContext = reqToHldRoomContext(addReq);

		// 执行创建
		HldRoom hldRoom = hldRoomService.create(hldRoomContext);

		// 远程写入映射关系
		if (!hldTaskService.updateTaskZt(addReq.getTaskId(), hldRoom.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_UPDATE_TASK_ZT);
		}

		return new BaseRsp<>(new HldRoomIdRsp(hldRoom.getResourceId()));
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<HldRoomRsp>
			get(@ApiParam(name = "room_id", value = "HLD房间id", required = true) String room_id) {

		// 获取当前用户角色
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole userRole = hldRoomService.getUserRole(room_id, userId);

		HldRoom hldRoom = hldRoomService.getById(room_id);
		SceneViewContext context = hldSceneService.viewScene(room_id, userRole);

		// 构造返回数据
		HldRoomRsp hldRoomRsp = BeanUtils.copy(context, HldRoomRsp.class);
		hldRoomRsp.setRoomStatus(hldRoom.getStatus());
		hldRoomRsp.setTemplateId(hldRoom.getTemplateId());
		hldRoomRsp.setTaskName(hldRoom.getTaskName());

		return new BaseRsp<>(hldRoomRsp);
	}

	@ApiOperation("查询测试模板id")
	@GetMapping("/template_id")
	public BaseRsp<HldRoomRsp> getTemplateId(
			@ApiParam(name = "room_id", value = "HLD房间id", required = true) String room_id) {

		HldRoom hldRoom = hldRoomService.getById(room_id);
		HldRoomRsp hldRoomRsp = BeanUtils.copy(hldRoom, HldRoomRsp.class);
		hldRoomRsp.setRoomId(hldRoom.getResourceId());

		return new BaseRsp<>(hldRoomRsp);
	}

	@ApiOperation("修改")
	@PatchMapping()
	public BaseRsp<HldRoomIdRsp> update(@RequestBody @Validated HldRoomModifyReq modifyReq) {
		String resourceId = modifyReq.getResourceId();
		HldRoom hldRoom = hldRoomService.getById(resourceId);

		// 不存在的训练直接抛出异常
		if (hldRoom == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		// 已开启的训练不允许修改
		if (!hldRoom.isUnopened()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_HAS_BEGUN);
		}

		// 如果业务模板不存在，抛出异常
		if (businessTemplateService.getById(hldRoom.getTemplateId()) == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		// 构造更新数据
		HldRoomContext hldRoomContext = reqToHldRoomContext(modifyReq);

		// 更新业务数据
		hldRoomService.update(hldRoomContext, hldRoom);

		return new BaseRsp<>(new HldRoomIdRsp(hldRoom.getResourceId()));
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		List<String> businessTemplateIds = hldRoomMapper
				.listBusinessTemplateIds(deleteReq.getIds());
		businessTemplateService.batchDelete(businessTemplateIds);

		return new BaseRsp<>();
	}

	@ApiOperation("查询HLD任务配置")
	@GetMapping("/roles")
	public BaseRsp<HldTaskRsp> getRoleConfig(
			@ApiParam(name = "room_id", value = "HLD房间id") String room_id,
			@ApiParam(name = "task_id", value = "HLD任务id") String task_id) {

		if ( room_id != null && !room_id.isEmpty()) {

			// 获取房间角色信息
			HldRoom hldRoom = hldRoomService.getById(room_id);

			// 未查询到相关房间报错
			if (hldRoom == null) {
				throw new ResourceNotFoundException(room_id);
			}

			HldRolesConfig hldRolesConfig = JsonUtil.toJsonObject(hldRoom.getRoleConfig(),
					HldRolesConfig.class);
			List<TaskRoleConfig> taskRoleConfigs = hldRoomService.getRoomRoleConfig(hldRolesConfig);

			return new BaseRsp<>(new HldTaskRsp(taskRoleConfigs, hldRoom.getTaskName()));

		} else {

			// 获取任务角色信息
			HldTaskContext hldTaskContext = getHldTaskContext(task_id);
			List<TaskRoleConfig> taskRoleConfigs = hldRoomService.getTaskRoleConfig(hldTaskContext);

			return new BaseRsp<>(new HldTaskRsp(taskRoleConfigs, hldTaskContext.getTaskName()));
		}
	}

	private HldTaskContext getHldTaskContext(String taskId) {

		JSONObject jsonObject = hldTaskService.getTaskInfo(taskId);

		// 获取不到数据时抛出异常
		if (null == jsonObject) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_GET_TASK_INFO);
		}

		HldTaskContext hldTaskContext = jsonObject.toJavaObject(HldTaskContext.class);
		hldTaskContext.setTaskName(jsonObject.getJSONObject("baseInfo").getString("taskName"));
		hldTaskContext.setAttackTaskSceneDes(
				jsonObject.getJSONObject("baseInfo").getString("attackTaskSceneDes"));

		return hldTaskContext;
	}

	private HldRoomContext reqToHldRoomContext(HldRoomAddReq addReq) {

		// 提取角色信息
		HldRolesConfig hldRolesConfig = BeanUtils.copy(addReq, HldRolesConfig.class);

		// 提取基础信息
		HldTaskContext hldTaskContext = getHldTaskContext(addReq.getTaskId());
		BaseInfo baseInfo = hldRoomService.getHldBaseInfo(hldTaskContext);
		baseInfo.setSceneConfigData(addReq.getSceneConfigData());

		// 构造初始数据
		HldRoomContext hldRoomContext = new HldRoomContext();
		hldRoomContext.setRoleConfig(JSONObject.toJSONString(hldRolesConfig));
		hldRoomContext.setBaseInfo(baseInfo);
		hldRoomContext.setTaskId(addReq.getTaskId());

		return hldRoomContext;
	}

}
