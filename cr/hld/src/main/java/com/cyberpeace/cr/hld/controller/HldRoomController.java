package com.cyberpeace.cr.hld.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.hld.controller.req.HldRoomAddReq;
import com.cyberpeace.cr.hld.controller.req.HldRoomModifyReq;
import com.cyberpeace.cr.hld.controller.rsp.HldRoomIdRsp;
import com.cyberpeace.cr.hld.controller.rsp.HldRoomRsp;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cr.hld.service.context.CrRoleConfigHld;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import com.cyberpeace.cr.hld.service.context.HldTaskContext;
import com.cyberpeace.integratation.service.HldTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "葫芦岛房间")
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
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private HldTaskService hldTaskService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<HldRoomIdRsp> create(@RequestBody @Validated HldRoomAddReq addReq) {

		// 构造初始数据
		HldRoomContext hldRoomContext = reqToHldRoomContext(addReq);

		// 执行创建
		HldRoom hldRoom = hldRoomService.create(hldRoomContext);

		// 远程写入映射关系
		updateHldTaskZt(addReq.getTaskId(), hldRoom.getResourceId());

		// 构造返回参数
		HldRoomIdRsp hldRoomIdRsp = new HldRoomIdRsp();
		hldRoomIdRsp.setRoomId(hldRoom.getResourceId());
		return new BaseRsp<>(hldRoomIdRsp);
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<HldRoomRsp> get(
			@ApiParam(name = "room_id", value = "葫芦岛房间id", required = true) String room_id,
			@ApiParam(name = "role_name", value = "角色名称", required = true) String role_name) {

		HldRoom hldRoom = hldRoomService.getById(room_id);
		SceneViewContext context = hldSceneService.viewScene(room_id, role_name);
		HldRoomRsp hldRoomRsp = BeanUtils.copy(context, HldRoomRsp.class);
		hldRoomRsp.setRoomStatus(hldRoom.getStatus());
		hldRoomRsp.setTemplateId(hldRoom.getTemplateId());
		return new BaseRsp<>(hldRoomRsp);
	}

	@ApiOperation("修改")
	@PatchMapping()
	public BaseRsp<HldRoomIdRsp> update(@RequestBody @Validated HldRoomModifyReq modifyReq) {
		String resourceId = modifyReq.getResourceId();
		HldRoom hldRoom = hldRoomService.getById(resourceId);

		// 不存在的训练直接异常
		if (hldRoom == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		// 已开启的训练不允许修改
		if (!hldRoom.isUnopened()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_HAS_BEGUN);
		}

		BusinessTemplate businessTemplate = businessTemplateService.getById(hldRoom.getTemplateId());

		// 如果业务模板不存在，返回异常
		if (businessTemplate == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		// 构造初始数据
		HldRoomContext hldRoomContext = reqToHldRoomContext(modifyReq);

		// 更新业务数据
		hldRoomService.update(hldRoomContext, hldRoom);

		// 构造返回参数
		HldRoomIdRsp hldRoomIdRsp = new HldRoomIdRsp();
		hldRoomIdRsp.setRoomId(hldRoom.getResourceId());
		return new BaseRsp<>(hldRoomIdRsp);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		List<String> businessTemplateIds = hldRoomMapper.listBusinessTemplateIds(deleteReq.getIds());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.batchDelete(businessTemplateIds);
			}
		});

		return new BaseRsp<>();
	}

	@ApiOperation("查询葫芦岛角色配置")
	@GetMapping("/roles")
	public BaseRsp<CrRoleConfigHld> getRoleConfig(
			@ApiParam(name = "task_id", value = "葫芦岛任务id", required = true) String task_id) {
		HldTaskContext hldTaskContext = getHldTaskContext(task_id);
		CrRoleConfigHld crRoleConfigHld = hldRoomService.getHldRoleConfig(hldTaskContext);
		return new BaseRsp<>(crRoleConfigHld);
	}

	private HldTaskContext getHldTaskContext(String taskId){
		JSONObject jsonObject = hldTaskService.getTaskInfo(taskId);
		HldTaskContext hldTaskContext = jsonObject.getObject("baseInfo", HldTaskContext.class);
		return hldTaskContext;
	}

	private Boolean updateHldTaskZt(String taskId, String sceneId){
		hldTaskService.updateTaskZt(taskId, sceneId);
		return true;
	}

	private HldRoomContext reqToHldRoomContext(HldRoomAddReq addReq) {

		// 提取角色信息
		CrRoleConfigHld roleConfigHld = BeanUtils.copy(addReq, CrRoleConfigHld.class);

		// 提取基础信息
		HldTaskContext hldTaskContext = getHldTaskContext(addReq.getTaskId());
		BaseInfo baseInfo = hldRoomService.getHldBaseInfo(hldTaskContext);
		baseInfo.setSceneConfigData(addReq.getSceneConfigData());

		// 构造初始数据
		HldRoomContext hldRoomContext = new HldRoomContext();
		hldRoomContext.setRoleConfig(CrCommonUtil.roleConfig2DbJson(roleConfigHld));
		hldRoomContext.setBaseInfo(baseInfo);
		hldRoomContext.setTaskId(addReq.getTaskId());

		return hldRoomContext;
	}

}
