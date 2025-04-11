package com.cyberpeace.oj.practice.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.*;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.bo.ClusterImagePageReq;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneOperationReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.controller.req.*;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.OperationBo;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.impl.PracticeValidateHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 管理实践实操Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PublicController
 * @since 1.0.0
 */
@Api(tags = "oj-后台实践操作练习相关api")
@RestController
@RequestMapping("/oj/admin/practice/operation")
@RequiresAuthentication
@Slf4j
public class AdminPracticeOperationController extends PublicController<PracticeOperationService, PracticeOperation> {

	/**
	 * 实践实操Service
	 *
	 * @see PracticeOperationService
	 */
	@Autowired
	private PracticeOperationService practiceOperationService;

	/**
	 * 实践实操Mapper
	 *
	 * @see PracticeOperationMapper
	 */
	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	/**
	 * 实践类别Service
	 *
	 * @see PracticeCategoryService
	 */
	@Autowired
	private PracticeCategoryService practiceCategoryService;

	/**
	 * 附件Service
	 *
	 * @see AttachmentService
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 实践验证处理器
	 *
	 * @see PracticeValidateHandler
	 */
	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	/**
	 * 远程日志Service
	 *
	 * @see RemoteLogService
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 远程终端Service
	 *
	 * @see RemoteTerminalService
	 */
	@Autowired
	private RemoteTerminalService remoteTerminalService;

	@Autowired
	private UtilService utilService;

	/**
	 * 获取
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<OperationBo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<OperationBo>
	 */
	@ApiOperation("详细信息查询")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<OperationBo> get(@ApiParam(name = "resource_id",
			value = "资源编号") @RequestParam("resource_id") String resourceId) {

		OperationBo operationBo = practiceOperationService.selectById(resourceId,
				AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false));
		return new BaseRsp(operationBo);
	}

	/**
	 * 新增
	 *
	 * @param addReq 新增请求体
	 * @return {@code BaseRsp }
	 * @throws
	 * @see OperationAddReq
	 * @see BaseRsp
	 */
	@ApiOperation("实践操作习题新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp add(@Validated(Create.class) OperationAddReq addReq) {

		addReq.validate();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		PracticeOperation addOperation = BeanUtils.copy(addReq, PracticeOperation.class);
		addOperation.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		Integer result = practiceOperationService.addPracticeOperation(addOperation, addReq.getTarget(), authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.CREATE, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.EXERCISE.getMsg(), addReq.getTitle()));

		return new BaseRsp(result);
	}

	/**
	 * 上传附件
	 *
	 * @param multipartFile Multipart 文件
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see MultipartFile
	 * @see BaseRsp<String>
	 */
	@ApiOperation("实操习题附件上传")
	@PostMapping("/upload_attachment")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<String>
			uploadAttachment(@RequestParam("attachment") MultipartFile multipartFile) {

		AttachmentBo attachmentBo = attachmentService
				.uploadDest(AttachmentPath.PRACTICE_ATTACHMENT.getDesc(), true, multipartFile);
		return new BaseRsp<>(attachmentBo.getResourceId());
	}

	/**
	 * 更新
	 *
	 * @param updateReq 更新请求体
	 * @return {@code BaseRsp<Integer> }
	 * @throws
	 * @see OperationUpdateReq
	 * @see BaseRsp<Integer>
	 */
	@ApiOperation("实践操作习题修改")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<Integer> update(@Validated(Update.class) OperationUpdateReq updateReq) {

		updateReq.validate();
		PracticeOperation updateOperation = BeanUtils.copy(updateReq, PracticeOperation.class);
		updateOperation.setAttack(updateReq.getAttackTag());
		updateOperation.setDefense(updateReq.getDefenseTag());

		Integer result = practiceOperationService.updateOperation(updateOperation,
				updateReq.getTarget());

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.UPDATE, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.EXERCISE.getMsg(), updateReq.getTitle()));

		return new BaseRsp(result);
	}

	/**
	 * 删除
	 *
	 * @param delReq 戴尔请求体
	 * @return {@code BaseRsp<Integer> }
	 * @throws
	 * @see ResourceDelReq
	 * @see BaseRsp<Integer>
	 */
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

	/**
	 * 列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<AdminOperationListBo>> }
	 * @throws
	 * @see AdminOperationListReq
	 * @see BaseRsp<ListRsp<AdminOperationListBo>>
	 */
	@ApiOperation("分页查询实践操作习题")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_PRACTICE_OPERATION)
	public BaseRsp<ListRsp<AdminOperationListBo>> list(AdminOperationListReq listReq) {

		OperationListQueryBo listQuery = BeanUtils.copy(listReq, OperationListQueryBo.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		IPage<AdminOperationListBo> iPage = practiceOperationService.pageByQuery(page, listQuery,
				authContext);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	/**
	 * 公开报道
	 *
	 * @param publicReq 公共请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ResourcePublicReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 场景拓扑
	 *
	 * @param operationId 实操id
	 * @return {@code BaseRsp<TopoSceneDataBo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<TopoSceneDataBo>
	 */
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

	/**
	 * 更新场景共享
	 *
	 * @param sceneShareModeReq 场景共享模式请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneShareModeReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 开始场景
	 *
	 * @param operationReq 实操请求体
	 * @return {@code BaseRsp<JSONObject> }
	 * @throws
	 * @see SceneOperationReq
	 * @see BaseRsp<JSONObject>
	 */
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

	/**
	 * 删除场景
	 *
	 * @param sceneOperationReq 场景实操请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneOperationReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 场景节点重新启动
	 *
	 * @param sceneNodeReq 场景节点请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneNodeReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 场景节点重建
	 *
	 * @param sceneNodeReq 场景节点请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneNodeReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 场景更新状态
	 *
	 * @param sceneStatusReq 场景状态请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneStatusReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 获取实操由节点
	 *
	 * @param operationByNodeReq 实操由节点请求体
	 * @return {@code BaseRsp<List<AdminOperationListBo>> }
	 * @throws
	 * @see OperationByNodeReq
	 * @see BaseRsp<List<AdminOperationListBo>>
	 */
	@ApiOperation("根据镜像id获取实操题列表")
	@PostMapping("/list/by_node")
	public BaseRsp<List<AdminOperationListBo>> getOperationByNode(@RequestBody OperationByNodeReq operationByNodeReq) {
		return new BaseRsp<>(practiceOperationService.getOperationByBodeIds(operationByNodeReq.getNodeIds()));
	}

	/**
	 * 获取图像分页
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp }
	 * @throws
	 * @see ClusterImagePageReq
	 * @see BaseRsp
	 */
	@ApiOperation("获取镜像列表")
	@GetMapping("/images")
	public BaseRsp getImagePage(@ApiParam ClusterImagePageReq req){
		JSONObject res = remoteTerminalService.pageImages(req);
		return new BaseRsp(res);
	}

	/**
	 * 导出理论题
	 *
	 * @param ids      id列表
	 * @param response 响应
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 * @see List<String>
	 */
	@ApiOperation("实操习题资源导出")
	@GetMapping("/batch_dumps")
	public BaseRsp<Boolean> exportPracticeSet(
			@ApiParam(name = "ids",
					value = "编号") @RequestParam("ids") @NotEmpty List<String> ids,
			HttpServletResponse response) {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		File dumpFile = practiceOperationService.dumpBatch(ids);
		FileUtil.setZipStream(dumpFile, true, response);
		return new BaseRsp(Boolean.TRUE);
	}

	/**
	 * 导入实践设置
	 *
	 * @param loadReq 加载请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see LoadResourceReq
	 * @see LoadResourceReq
	 */
	@ApiOperation("实操资源数据导入")
	@PostMapping("/batch_loads")
	public BaseRsp<Boolean> importPracticeSet(@Validated LoadResourceReq loadReq) throws IOException {

		FileUtil.validateSuffix(loadReq.getFile(), FileSunffixConstant.ZIP.getDesc());
		// 1. save file to temp dir
		File localFile = FileUtil.saveFile(loadReq.getFile(), ZipUtil.generatePath());
		String realPath = localFile.getAbsolutePath();
		// 文件加载
		Boolean result;
		try {
			result = practiceOperationService.loadBatch(realPath, UserInfoContext.getUserContext().getUserId());
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				throw e;
			}
			log.error("error file:{}:{}", realPath, e);
			throw new BusinessException(CrBusinessErrorCode.INVALID_RESOURCE_ZIP);
		}
		return new BaseRsp(result);
	}

}
