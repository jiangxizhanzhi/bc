package com.cyberpeace.cr.crbase.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.MultiSelectReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.NameValidReq;
import com.cyberpeace.cr.crbase.controller.req.ShareReq;
import com.cyberpeace.cr.crbase.controller.req.TemplateReq;
import com.cyberpeace.cr.crbase.controller.req.scene.TestSceneReq;
import com.cyberpeace.cr.crbase.controller.rsp.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SceneConfigRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.LoadTemplate;
import com.cyberpeace.cr.crbase.service.context.Share;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

@Api(tags = "基础模板")
@RestController
@Slf4j
@RequestMapping("/admin/ceBase/crEvents")
public class BusinessTemplateController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 获取所有靶场的业务模板
	 * @return
	 */
	@ApiOperation("查询模板列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<BusinessTemplateData>> getTemplate(TemplateReq listReq) {
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
			businessTemplateRsp.setSceneConfigInfo(sceneConfigInfo);

			return businessTemplateRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	/**
	 * 获取靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("查询操作权限")
	@GetMapping("/getShares")
	public BaseRsp<Share> getShares(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {
		Share data = businessTemplateService.getShares(resource_id);

		return new BaseRsp(data);
	}

	/**
	 * 设置靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("批量修改操作权限")
	@PostMapping("/batchSetShares")
	public BaseRsp<String> batchSetShares(@RequestBody @Validated ShareReq shareReq) {
		ShareContext shareContext = BeanUtils.copy(shareReq, ShareContext.class);

		businessTemplateService.batchSetShares(shareContext);

		return new BaseRsp();
	}

	/**
	 * 导入业务模板
	 * @return
	 */
	@ApiOperation("导入业务模板")
	@PostMapping("/batch_loads")
	public BaseRsp batchLoads(@RequestParam MultipartFile file) {
		try {
			File localFile = FileUtil.saveFile(file, ZipUtil.generatePath());
			String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
			File unzipFileDirectory = new File(unZipPath);

			if (unzipFileDirectory.isDirectory()) {
				File[] unzipFiles = unzipFileDirectory.listFiles();
				for (File unzipFile : unzipFiles) {

					// 反序列化对象
					ParserConfig parserConfig = new ParserConfig();
					parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
					LoadTemplate loadTemplate = JSON.parseObject(FileUtil.textFromFile(unzipFile),
							LoadTemplate.class, parserConfig);

					businessTemplateService.batchLoads(loadTemplate);

					SerializeConfig config = new SerializeConfig();
					config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
					String json123 = JSON.toJSONString(loadTemplate, config);

				}
			}
		} catch (ZipException e) {
			log.error("unable unzip file！filename=[{}]", file.getOriginalFilename());
			throw new BusinessException(CrBusinessErrorCode.FILE_FORMAT_ERROR);
		} catch (IOException e) {
			log.error("unable save file！filename=[{}],path=[{}]", file.getOriginalFilename(),
					ZipUtil.generatePath());
			throw new BusinessException(CrBusinessErrorCode.NOT_PATH);
		}

		return new BaseRsp();
	}

	/**
	 * 导出业务模板
	 * @return
	 */
	@ApiOperation("导出业务模板")
	@PostMapping("/batch_dumps")
	public BaseRsp<String> batchDumps(@ApiParam(name = "multiSelectReq",
			value = "模板id") @RequestBody MultiSelectReq multiSelectReq) {

		List<BusinessTemplate> businessTemplateList = businessTemplateService
				.listByIds(multiSelectReq.getIds());

		return null;
	}

	@DeleteMapping()
	@ApiOperation("批量删除")
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		// todo 关联`cr_xxx_template`表数据待删除
		return new BaseRsp<>(businessTemplateService.batchDelete(deleteReq.getIds()) != 0);
	}

	@ApiOperation("校验模板名字")
	@PatchMapping("/validate_name_patch/")
	public BaseRsp<String> validateNamePatch(@RequestBody @Validated NameValidReq nameValidReq) {
		businessTemplateService.validateNamePatch(nameValidReq.getName(), nameValidReq.getType(),
				nameValidReq.getId());
		return new BaseRsp();
	}

	@ApiOperation("启动后台测试场景")
	@PostMapping("/testScene")
	public BaseRsp testScene(@RequestBody TestSceneReq testSceneReq) {

		String templateId = testSceneReq.getBusinessTemplateId();
		// 1、检查权限
		checkBeforeTestingScene(templateId);
		// 2、获取模板
		BusinessTemplate businessTemplate = ensureTemplateExist(templateId);

		// 3、创建场景管理器
		LocalScene localScene = new LocalScene();
		localScene.setBusinessCode(LocalScene.BusinessCode.TEST_SCENE.getCode());
		localScene.setTopoId(businessTemplate.getTopoId());
		// 以模板id作为后台测试场景的 businessId，
		// 发给mp的context中会以localScene的businessInstanceId作为透明上下文（passThrough）的businessId，用于ws消息推送
		localScene.setBusinessInstanceId(templateId);

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
					// todo log
				}
			});
		} catch (Exception e) {
			// 保存失败，删除远程场景
			log.error("db error when testing scene: ", e);
			localSceneService.deleteRemoteScene(localScene);
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}
		return new BaseRsp();
	}

	@ApiOperation("关闭后台场景测试")
	@DeleteMapping("/testScene")
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

	private void checkBeforeTestingScene(String templateId) {
		// todo 模板授权
		if (!UserInfoContext.getUserContext().isAdmin()) {
			log.error("current user is not permitted to test template [{}]", templateId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	private BusinessTemplate ensureTemplateExist(String templateId) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(templateId);
		if (businessTemplate == null) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NOT_EXIST);
		}

		return businessTemplate;
	}
}
