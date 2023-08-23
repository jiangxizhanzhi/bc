package com.cyberpeace.oj.practice.controller.admin;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PatternReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneOperationReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.controller.req.*;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.OperationBo;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.impl.PracticeValidateHandler;

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
	private PracticeValidateHandler practiceValidateHandler;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("详细信息查询")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<OperationBo> get(@ApiParam(name = "resource_id",
			value = "资源编号") @RequestParam("resource_id") String resourceId) {

		OperationBo operationBo = practiceOperationService.selectById(resourceId,
				AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false));
		return new BaseRsp(operationBo);
	}

	@ApiOperation("实践操作习题新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp add(@Validated(Create.class) OperationAddReq addReq) {

		addReq.validate();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		PracticeOperation addOperation = BeanUtils.copy(addReq, PracticeOperation.class);
		addOperation.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		Integer result = practiceOperationService.addPracticeOperation(addOperation,
				JsonUtil.toJsonObject(addReq.getSceneConfigData(), JSONObject.class), authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.CREATE, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.EXERCISE.getMsg(), addReq.getTitle()));

		return new BaseRsp(result);
	}

	@ApiOperation("实操习题附件上传")
	@PostMapping("/upload_attachment")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<String>
			uploadAttachment(@RequestParam("attachment") MultipartFile multipartFile) {

		AttachmentBo attachmentBo = attachmentService
				.uploadDest(AttachmentPath.PRACTICE_ATTACHMENT.getDesc(), true, multipartFile);
		return new BaseRsp<>(attachmentBo.getResourceId());
	}

	@ApiOperation("实践操作习题修改")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Integer> update(@Validated(Update.class) OperationUpdateReq updateReq) {

		updateReq.validate();
		PracticeOperation updateOperation = BeanUtils.copy(updateReq, PracticeOperation.class);

		Integer result = practiceOperationService.updateOperation(updateOperation,
				JsonUtil.toJsonObject(updateReq.getSceneConfigData(), JSONObject.class));

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.UPDATE, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.EXERCISE.getMsg(), updateReq.getTitle()));

		return new BaseRsp(result);
	}

	@ApiOperation("实践操作习题删除")
	@DeleteMapping("")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Integer> delete(@RequestBody @Validated ResourceDelReq delReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		List<PracticeOperation> practiceOperationList = practiceOperationService.listByIds(delReq.getResourceIds());

		Integer result = practiceOperationService.batchDelete(delReq.getResourceIds(), authContext);

		practiceOperationList.forEach(practiceOperation -> {
			if (practiceOperation != null) {
				remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
						OperationType.DELETE, LogContent.EXERCISE.getMsg(),
						LogContent.CR_FORMAT.format(OperationType.DELETE, LogContent.EXERCISE.getMsg(), practiceOperation.getTitle()));
			}

		});

		return new BaseRsp<>(result);
	}

	@GetMapping("/knowledge/list")
	@ApiOperation("获取知识点列表，按频次排序")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<List<String>> listKnowledge(PatternReq pattern) {
		List<String> knowledgeList = practiceOperationService
				.getKnowledgeListByFrequency(pattern, false);
		return new BaseRsp<>(knowledgeList);
	}

	@ApiOperation("分页查询实践操作习题")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<ListRsp<AdminOperationListBo>> list(AdminOperationListReq listReq) {

		OperationListQueryBo listQuery = BeanUtils.copy(listReq, OperationListQueryBo.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		// 知识点
		listQuery.setKnowledgeList(listReq.getKnowledge());

		if (StringUtils.isNotBlank(listReq.getCategoryId())) {
			List<String> categoryIds = practiceCategoryService
					.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);
			listQuery.setCategoryIds(categoryIds);
		}

		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		IPage<AdminOperationListBo> iPage = practiceOperationService.pageByQuery(page, listQuery,
				authContext);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	@ApiOperation("批量公开/隐藏题解")
	@PatchMapping("/writeup_public")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
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
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<TopoSceneDataBo> sceneTopo(@ApiParam(name = "resource_id",
			value = "习题编号") @RequestParam("resource_id") String operationId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		TopoSceneDataBo topoScene = practiceOperationService.getTopoScene(operationId, authContext);
		return new BaseRsp(topoScene);
	}

	@ApiOperation("隔离模式设置")
	@PatchMapping("/scene_share_mode")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Boolean>
			updateSceneShare(@RequestBody @Validated SceneShareModeReq sceneShareModeReq) {

		// 数据校验
		PracticeOperation operation = practiceValidateHandler
				.validateOperationExist(sceneShareModeReq.getResourceId());

		if (operation.getDynamicEnv() && null != operation.getSceneShareMode()
				&& StringUtils.isNotBlank(operation.getTopoId())) {
			practiceValidateHandler.validateOperationUpdateShareModeSceneExist(operation);
		}

		operation.setSceneShareMode(sceneShareModeReq.getSceneShareMode());

		int result = practiceOperationMapper.updateById(operation);

		return new BaseRsp(result);
	}

	@ApiOperation("后台场景启动测试")
	@PostMapping("/start_scene")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<JSONObject> startScene(@RequestBody @Validated SceneOperationReq operationReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		JSONObject scene = practiceOperationService.startScene(operationReq.getResourceId(),
				authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.START, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.START, LogContent.EXERCISE.getMsg(), practiceOperationMapper.selectById(operationReq.getResourceId()).getTitle()));

		return new BaseRsp<>(scene);

	}

	@ApiOperation("后台场景删除")
	@DeleteMapping("/delete_scene")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated SceneOperationReq sceneOperationReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = practiceOperationService.deleteScene(sceneOperationReq.getResourceId(),
				authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.STOP, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.STOP, LogContent.EXERCISE.getMsg(), practiceOperationMapper.selectById(sceneOperationReq.getResourceId()).getTitle()));

		return new BaseRsp<>(result);
	}

	@ApiOperation("后台场景节点重启")
	@PostMapping("/scene_node_restart")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Boolean> sceneNodeRestart(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = practiceOperationService.nodeRestart(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台场景节点重建")
	@PostMapping("/scene_node_recreate")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Boolean> sceneNodeRecreate(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		Boolean result = practiceOperationService.nodeRecreate(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);

		return new BaseRsp<>(result);
	}

	@ApiOperation("后台场景暂停与恢复")
	@PatchMapping("/scene_update_status")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Boolean> sceneUpdateStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = practiceOperationService.pauseScene(sceneStatusReq.getResourceId(),
				sceneStatusReq.getStatus(), authContext);
		return new BaseRsp(result);
	}

}
