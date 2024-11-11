package com.cyberpeace.cr.crbase.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.NameValidReq;
import com.cyberpeace.cr.crbase.controller.req.ShareReq;
import com.cyberpeace.cr.crbase.controller.req.TemplateReq;
import com.cyberpeace.cr.crbase.controller.req.resource.BatchDumpReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.TestSceneReq;
import com.cyberpeace.cr.crbase.controller.rsp.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SceneConfigRsp;
import com.cyberpeace.cr.crbase.controller.rsp.StartSceneRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.Share;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

/**
 * 业务模板控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "基础模板")
@RestController
@Slf4j
@RequestMapping("/admin/ceBase/crEvents")
@RequiresAuthentication
public class BusinessTemplateController {

	/**
	 * 业务模板Service
	 */
	@Autowired
	private BusinessTemplateService businessTemplateService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 远程日志Service
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 获取所有靶场的业务模板
	 *
	 * @param listReq 列表请求体
	 * @return
	 */
	@ApiOperation("查询模板列表")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp<ListRsp<BusinessTemplateRsp>> getTemplate(TemplateReq listReq) {
		BusinessTemplateQuery businessTemplateQuery = BeanUtils.copy(listReq,
				BusinessTemplateQuery.class);
		businessTemplateQuery.setSort("-cr_business_template.create_time");

		IPage<BusinessTemplateData> data = businessTemplateService.page(businessTemplateQuery);

		List<String> userIds = Lists.newArrayList();
		for (BusinessTemplateData businessTemplateData : data.getRecords()) {
			userIds.add(businessTemplateData.getCreateUserId());
		}
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

		data.convert(businessTemplateData -> {
			BusinessTemplateRsp businessTemplateRsp = BeanUtils.copy(businessTemplateData,
					BusinessTemplateRsp.class);
			businessTemplateRsp.setDifficult(businessTemplateData.getDifficulty());
			businessTemplateRsp
					.setCreateUser(idNameMap.get(businessTemplateData.getCreateUserId()));
			businessTemplateRsp.setId(businessTemplateData.getResourceId());
			businessTemplateRsp.setType(businessTemplateData.getBusinessType());
			businessTemplateRsp.setSceneManagerId(businessTemplateData.getTopoId());
			businessTemplateRsp.setShare(businessTemplateData.getOperationAuthType());
			businessTemplateRsp.setShareTeachers(JsonUtil
					.toJsonObjectArray(businessTemplateData.getOperationAuthIds(), String.class));
			SceneConfigRsp sceneConfigInfo = new SceneConfigRsp();
			sceneConfigInfo.setNodeCount(businessTemplateData.getNodesNum());
			sceneConfigInfo.setResourceOccupiedNums(businessTemplateData.getResourceOccupiedNums());
			sceneConfigInfo
					.setHasScene(StringUtils.isNotBlank(businessTemplateData.getLocalSceneId()));
			businessTemplateRsp.setSceneConfigInfo(sceneConfigInfo);

			return businessTemplateRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	/**
	 * 获取靶场业务模板的操作权限
	 *
	 * @param resource_id 资源id
	 * @return
	 */
	@ApiOperation("查询操作权限")
	@GetMapping("/getShares")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp<Share> getShares(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {
		Share data = businessTemplateService.getShares(resource_id);

		return new BaseRsp(data);
	}

	/**
	 * 设置靶场业务模板的操作权限
	 *
	 * @param shareReq 共享请求体
	 * @return
	 */
	@ApiOperation("批量修改操作权限")
	@PostMapping("/batchSetShares")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp<String> batchSetShares(@RequestBody @Validated ShareReq shareReq) {
		ShareContext shareContext = BeanUtils.copy(shareReq, ShareContext.class);

		businessTemplateService.batchSetShares(shareContext);

		return new BaseRsp();
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@DeleteMapping()
	@ApiOperation("批量删除")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		List<BusinessTemplate> businessTemplateList = businessTemplateService.listByIds(deleteReq.getIds());

		Boolean flag = businessTemplateService.batchDelete(deleteReq.getIds());

		businessTemplateList.forEach(businessTemplate -> {
			if (businessTemplate != null) {
				String templateName = businessTemplate.getName();
				Integer businessType = businessTemplate.getBusinessType();

				OperationModule module = null;
				String msg = "";

				if (Objects.equals(businessType, BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode())) {
					module = OperationModule.SINGLE_TRAIN;
					msg = LogContent.SINGLE_TRAIN_TEMPLATE.getMsg();
				} else if (Objects.equals(businessType, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode())) {
					module = OperationModule.TEAM_TRAIN;
					msg = LogContent.TEAM_TRAIN_TEMPLATE.getMsg();
				} else if (Objects.equals(businessType, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode())) {
					module = OperationModule.RED_VS_BLUE;
					msg = LogContent.RB_ROOM_TEMPLATE.getMsg();
				}


				remoteLogService.addOjLog(UserInfoContext.getUserContext(), module,
						OperationType.DELETE, templateName,
						LogContent.CR_FORMAT.format(OperationType.DELETE, msg, templateName));
			}
		});

		return new BaseRsp<>(flag);
	}

	/**
	 * 验证名称修改
	 *
	 * @param nameValidReq 名称有效请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("校验模板名字")
	@PatchMapping("/validate_name_patch/")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp<String> validateNamePatch(@RequestBody @Validated NameValidReq nameValidReq) {
		businessTemplateService.validateName(nameValidReq.getName(), nameValidReq.getType(),
				nameValidReq.getId());
		return new BaseRsp();
	}

	/**
	 * 测试场景
	 *
	 * @param testSceneReq 测试场景请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("启动后台测试场景")
	@PostMapping("/testScene")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp testScene(@RequestBody TestSceneReq testSceneReq) {

		String templateId = testSceneReq.getBusinessTemplateId();

		// 增加重复性校验
		LocalScene instanceId = localSceneService.getByBusinessInstanceId(templateId);
		if (null != instanceId) {
			log.error("Scene start repeat! templateId = [{}]", templateId);
			throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
		}

		// 1、检查权限
		checkBeforeTestingScene(templateId);
		// 2、获取模板
		BusinessTemplate businessTemplate = ensureTemplateExist(templateId);

		// 3、创建场景管理器
		LocalScene localScene = businessTemplateService.createLocalScene(businessTemplate);

		// 4、发送mp请求，创建远程场景
		String sceneId = localSceneService.testScene(localScene);
		localScene.setMpSceneId(sceneId);
		// 5、关联模板和场景管理器
		businessTemplate.setLocalSceneId(localScene.getResourceId());

		// 6、保存本地数据
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					localSceneService.save(localScene);
					businessTemplateService.updateById(businessTemplate);
				}
			});
		} catch (DuplicateKeyException e) {
			log.error("Data duplication. catch error:", e);
			throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
		} catch (Exception e) {
			// 保存失败，删除远程场景
			log.error("db error when testing scene: ", e);
			localSceneService.deleteRemoteScene(localScene);
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}
		return new BaseRsp(new StartSceneRsp(sceneId));
	}

	/**
	 * 获取场景
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<SceneViewContext> }
	 */
	@ApiOperation("查看后台测试场景")
	@GetMapping("/testScene")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp<SceneViewContext> getScene(
			@ApiParam(name = "resource_id", value = "业务模板id", required = true) String resource_id) {

		BusinessTemplate template = ensureTemplateExist(resource_id);
		SceneViewContext context = businessTemplateService.viewScene(template);

		return new BaseRsp<>(context);
	}

	/**
	 * 停测试场景
	 *
	 * @param testSceneReq 测试场景请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("关闭后台场景测试")
	@DeleteMapping("/testScene")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp stopTestScene(@RequestBody TestSceneReq testSceneReq) {
		// 1.获取模板
		String templateId = testSceneReq.getBusinessTemplateId();
		BusinessTemplate template = ensureTemplateExist(templateId);
		// 2.鉴权
		checkBeforeTestingScene(templateId);
		// 3.关闭远程场景
		LocalScene localScene = localSceneService.getByBusinessInstanceId(templateId);
		localSceneService.stopScene(localScene);
		// 4.清除本地场景数据
		businessTemplateService.clearLocal(template);

		return new BaseRsp();
	}

	/**
	 * 重启节点
	 *
	 * @param nodeOpsReq 节点操作 计划请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("后台测试场景重启节点")
	@PostMapping("/testScene/node/restart")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		BusinessTemplate template = businessTemplateService.getById(nodeOpsReq.getResourceId());
		if (template == null || StringUtils.isBlank(template.getLocalSceneId())) {
			log.error("Scene not start! resourceId = [{}]", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.restartNode(template.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("Node restart failure! noteId = [{}]", nodeOpsReq.getNodeId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RESTART_NODE);
		}
		return new BaseRsp();
	}

	/**
	 * 重新创建节点
	 *
	 * @param nodeOpsReq 节点操作 计划请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("后台测试场景重建节点")
	@PostMapping("/testScene/node/recreate")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp recreateNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		BusinessTemplate template = businessTemplateService.getById(nodeOpsReq.getResourceId());
		if (template == null || StringUtils.isBlank(template.getLocalSceneId())) {
			log.error("Scene not start! resourceId = [{}]", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(template.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("Node recreate failure! noteId = [{}]", nodeOpsReq.getNodeId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp();
	}

	/**
	 * 创建节点
	 *
	 * @param nodeOpsReq 节点操作 计划请求体
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("创建启动节点")
	@PostMapping("/node/create")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp createNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		BusinessTemplate template = businessTemplateService.getById(nodeOpsReq.getResourceId());
		if (template == null || StringUtils.isBlank(template.getLocalSceneId())) {
			log.error("Scene not start! resourceId = [{}]", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.createNode(template.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("Node recreate failure! noteId = [{}]", nodeOpsReq.getNodeId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_CREATE_NODE);
		}
		return new BaseRsp();
	}

	/**
	 * 批量转 储
	 *
	 * @param response 响应
	 * @param dumpReq  转 储请求体
	 */
	@ApiOperation("批量导出场景模板")
	@PostMapping("/batchDump")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public void batchDump(HttpServletResponse response, @RequestBody BatchDumpReq dumpReq) {

		List<String> templateIds = dumpReq.getIds();
		// 1. generate dump file
		String tempDir = ZipUtil.generatePath();
		try {
			String zipPath = businessTemplateService.makeDumpFile(templateIds, tempDir);
			// 2. generate file response
			File zip = new File(zipPath);
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			log.error("failed to dump business template", e);
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_DUMP_TEMPLATE);
		} finally {
			// 3. delete temp dir
			if (!FileUtil.deleteDir(new File(tempDir))) {
				log.error("failed to remove temp dir :{}", tempDir);
			}
		}
	}

	/**
	 * 批量加载
	 *
	 * @param file 文件
	 * @return {@code BaseRsp }
	 * @throws IOException  io异常
	 * @throws ZipException zip 异常
	 */
	@ApiOperation("批量导入场景模板")
	@PutMapping("/batchLoad")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN }, logical = Logical.OR)
	public BaseRsp batchLoad(@RequestParam("file") MultipartFile file)
			throws IOException, ZipException {

		FileUtil.validateSuffix(file, FileSunffixConstant.ZIP.getDesc());
		// 1. save file to temp dir
		File localFile = FileUtil.saveFile(file, ZipUtil.generatePath());
		// 2. unzip file
		String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
		// 3. load resources
		businessTemplateService.batchLoad(unZipPath);
		// 4. delete temp dir
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}
		return new BaseRsp();
	}

	/**
	 * 分页简单
	 *
	 * @param btPageReq BT系列分页请求体
	 * @return {@code BaseRsp<List<BusinessTemplateSimple>> }
	 */
	@ApiOperation("职业框架-场景模板列表")
	@GetMapping("/list/simple")
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.CMS_CR_TEAM_TRAIN,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_ATTACK_FRAME,
			MenuConstant.CMS_ENGAGE_FRAME }, logical = Logical.OR)
	public BaseRsp<List<BusinessTemplateSimple>> pageSimple(@Validated BTPageReq btPageReq) {
		ListRsp<BusinessTemplateSimple> res = businessTemplateService.pageSimple(btPageReq);

		return new BaseRsp(res);
	}

	/**
	 * 检查模板访问权限
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("创建训练时校验用户是否拥有模板权限")
	@GetMapping("/check/template/permission")
	@RequiresPermissions(
			value = { MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN },
			logical = Logical.OR)
	public BaseRsp<Boolean> checkTemplateAccessPermission(
			@ApiParam(value = "训练模板id", name = "resource_id") String resource_id) {
		CrCommonValidator.validateId(resource_id);
		boolean res = businessTemplateService.checkPermission(resource_id);
		if (!res) {
			log.error(
					"template deleted or current user not permitted: template_id:[{}], userId:[{}]",
					resource_id, UserInfoContext.getUserContext().getUserId());
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_DELETED_OR_NOT_PERMITTED);
		}
		return new BaseRsp<>(true);
	}

	/**
	 * 检查以前测试场景
	 *
	 * @param templateId 模板id
	 */
	private void checkBeforeTestingScene(String templateId) {
		// 暂无
	}

	/**
	 * 确保模板存在
	 *
	 * @param templateId 模板id
	 * @return {@code BusinessTemplate }
	 */
	private BusinessTemplate ensureTemplateExist(String templateId) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(templateId);
		if (businessTemplate == null) {
			log.error("Template does not exist! templateId = [{}]", templateId);
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NOT_EXIST);
		}

		return businessTemplate;
	}
}
