package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.practice.controller.req.*;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.OperationBo;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-后台实践操作练习相关api")
@RestController
@RequestMapping("/oj/admin/practice/operation")
@RequiresAuthentication
public class AdminPracticeOperationController extends PublicController<PracticeOperationService, PracticeOperation> {

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@ApiOperation("详细信息查询")
	@GetMapping("")
	public BaseRsp<OperationBo> get(@ApiParam(name = "resource_id",
			value = "资源编号") @RequestParam("resource_id") String resourceId) {

		OperationBo operationBo = practiceOperationService.selectById(resourceId);
		return new BaseRsp(operationBo);
	}

	@ApiOperation("实践操作习题新增")
	@PostMapping("")
	public BaseRsp add(@Validated(Create.class) OperationAddReq addReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		PracticeOperation addOperation = convertReq(addReq);
		if (null != addReq.getFile()) {
			AttachmentBo attachmentBo = attachmentService.uploadFile(
					AttachmentPath.PRACTICE_ATTACHMENT.getCode(), true, addReq.getFile(), true);
			addOperation.setAttachmentId(attachmentBo.getResourceId());
		}
		Integer result = practiceOperationService.addPracticeOperation(addOperation,
				JsonUtil.toJsonObject(addReq.getOjSceneConfig(), JSONObject.class), authContext);

		return new BaseRsp(result);
	}

	/**
	 * 将实践操作增加和删除的请求值转换为实体类
	 * @param addReq
	 * @return
	 */
	private PracticeOperation convertReq(OperationAddReq addReq) {

		PracticeOperation practiceOperation = BeanUtils.copy(addReq, PracticeOperation.class);

		practiceOperation.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		return practiceOperation;
	}

	@ApiOperation("实践操作习题修改")
	@PatchMapping("")
	public BaseRsp<Integer> update(@Validated(Update.class) OperationUpdateReq updateReq) {

		PracticeOperation updateOperation = convertReq(updateReq);

		if (null != updateReq.getFile()) {
			AttachmentBo attachmentBo = attachmentService.uploadFile(
					AttachmentPath.PRACTICE_ATTACHMENT.getCode(), true, updateReq.getFile(), true);
			updateOperation.setAttachmentId(attachmentBo.getResourceId());
		}
		Integer result = practiceOperationService.updateOperation(updateOperation,
				JsonUtil.toJsonObject(updateReq.getOjSceneConfig(), JSONObject.class));
		return new BaseRsp(result);
	}

	@ApiOperation("实践操作习题删除")
	@DeleteMapping("")
	public BaseRsp<Integer> delete(@RequestBody @Validated ResourceDelReq delReq) {

		int result = practiceOperationMapper.deleteBatchIds(delReq.getResourceIds());
		return new BaseRsp<>(result);
	}

	@ApiOperation("分页查询实践操作习题")
	@GetMapping("/list")
	public BaseRsp<ListRsp<AdminOperationListBo>> list(AdminOperationListReq listReq) {

		OperationListQueryBo listQuery = BeanUtils.copy(listReq, OperationListQueryBo.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		if (StringUtils.isNotBlank(listReq.getCategoryId())) {
			List<String> categoryIds = practiceCategoryService
					.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);
			listQuery.setCategoryIds(categoryIds);
		}

		Page page = listReq.toPageParam();

		IPage<AdminOperationListBo> iPage = practiceOperationService.pageByQuery(page, listQuery,
				authContext);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	@ApiOperation("批量公开/隐藏题解")
	@PatchMapping("/writeup_public")
	public BaseRsp<Boolean> writeupPublic(@RequestBody @Validated ResourcePublicReq publicReq) {

		List<PracticeOperation> operations = StreamApiUtils.map(publicReq.getResourceIds(),
				resourceId -> {
					PracticeOperation operation = new PracticeOperation();
					operation.setResourceId(resourceId);
					operation.setWriteupPublic(publicReq.getIsPublic());
					return operation;
				});

		boolean result = practiceOperationService.updateBatchById(operations);
		return new BaseRsp<>(result);

	}

	@ApiOperation("获取场景拓扑信息")
	@GetMapping("/scene_topo")
	public BaseRsp sceneTopo(@RequestParam("resource_id") String resourceId) {

		PracticeOperation practiceOperation = practiceOperationMapper.selectById(resourceId);
		if (null != practiceOperation && practiceOperation.getDynamicEnv()
				&& StringUtils.isNotBlank(practiceOperation.getTopoId())) {
			JSONObject sceneConfigData = remoteTopoService.getTopoById(resourceId);
			return new BaseRsp(sceneConfigData);
		}

		return new BaseRsp();
	}

	@ApiOperation("隔离模式设置")
	@PatchMapping("/scene_share_mode")
	public BaseRsp<Boolean>
			updateSceneShare(@RequestBody @Validated SceneShareModeReq sceneShareModeReq) {

		PracticeOperation operation = new PracticeOperation();
		operation.setResourceId(sceneShareModeReq.getResourceId());
		operation.setSceneShareMode(sceneShareModeReq.getSceneShareMode());

		int result = practiceOperationMapper.updateById(operation);

		return new BaseRsp(result);
	}

	@ApiOperation("后台场景启动测试")
	@PostMapping("/start_scene")
	public BaseRsp<JSONObject> startScene(@RequestParam("resourceId") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		JSONObject scene = practiceOperationService.startScene(resourceId, authContext);
		return new BaseRsp<>(scene);

	}

	@ApiOperation("后台场景删除")
	@PostMapping("/delete_scene")
	public BaseRsp deleteScene(@RequestParam("resource_id") String resourceId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		practiceOperationService.deleteScene(resourceId, authContext);
		return new BaseRsp<>();
	}

	@ApiOperation("后台场景节点重启")
	@PostMapping("/scene_node_restart")
	public BaseRsp<Boolean> sceneNodeRestart(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = practiceOperationService.nodeRestart(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台场景节点重建")
	@PostMapping("/scene_node_recreate")
	public BaseRsp<Boolean> sceneNodeRecreate(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		Boolean result = practiceOperationService.nodeRecreate(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);

		return new BaseRsp<>(result);
	}

	@ApiOperation("后台场景暂停与恢复")
	@PatchMapping("/scene_update_status")
	public BaseRsp sceneUpdateStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = practiceOperationService.pauseScene(sceneStatusReq.getResourceId(),
				sceneStatusReq.getStatus(), authContext);
		return new BaseRsp(result);
	}

}
