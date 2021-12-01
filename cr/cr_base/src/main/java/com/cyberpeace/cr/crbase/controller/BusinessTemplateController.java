package com.cyberpeace.cr.crbase.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.MultiSelectReq;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.NameValidReq;
import com.cyberpeace.cr.crbase.controller.req.ShareReq;
import com.cyberpeace.cr.crbase.controller.req.TemplateReq;
import com.cyberpeace.cr.crbase.controller.req.scene.TestSceneReq;
import com.cyberpeace.cr.crbase.controller.rsp.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SceneConfigRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.SceneService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.Share;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cr.crbase.service.impl.TestSceneHandler;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

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
	private TestSceneHandler testSceneHandler;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private SceneService sceneService;

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
	public BaseRsp<String> batchLoads(@RequestBody MultipartFile file) {
		// todo

		return null;
	}

	/**
	 * 导出业务模板
	 * @return
	 */
	@ApiOperation("导出业务模板")
	@PostMapping("/batch_dumps")
	public BaseRsp<String> batchDumps(@ApiParam(name = "multiSelectReq",
			value = "模板id") @RequestBody MultiSelectReq multiSelectReq) {
		// todo

		return null;
	}

	@DeleteMapping()
	@ApiOperation("批量删除")
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		// todo
		return new BaseRsp<>();
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

		testSceneHandler.checkBeforeTestingScene(templateId);

		BusinessTemplate businessTemplate = businessTemplateService.getById(templateId);
		LocalScene localScene = sceneService.createLocalScene(
				LocalScene.BusinessCode.TEST_SCENE.getCode(), "", businessTemplate.getTopoId());

		String sceneId = localSceneService.startScene(localScene);
		localScene.setMpSceneId(sceneId);

		businessTemplate.setLocalSceneId(localScene.getResourceId());

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
			log.error("db error when testing scene: ", e);
			localSceneService.deleteRemoteScene(localScene);
			throw new RuntimeException();
		}
		return new BaseRsp();
	}
}
