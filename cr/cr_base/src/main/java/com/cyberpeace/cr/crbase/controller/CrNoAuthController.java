package com.cyberpeace.cr.crbase.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.resource.BatchLoadMdReq;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.RtksRelatedService;
import com.cyberpeace.cr.crbase.service.context.resource.MDParser;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Sets;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 无需用户认证的接口
 */
@Api(tags = "靶场无需用户认证的接口")
@RestController
@Slf4j
@RequestMapping("/cr/noAuth")
public class CrNoAuthController {

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private BusinessTemplateHandler businessTemplateHandler;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RtksRelatedService rtksRelatedService;

	@ApiOperation("批量导入资管模板")
	@PostMapping("/batchLoadMD")
	public BaseRsp<Boolean> batchLoadMd(@RequestBody @Valid BatchLoadMdReq req) {
		// validate
		req.validate();
		Map<String, String> envReqs = req.getEnvReqs();
		String basePath = req.getPath();
		// 依次导入，
		for (Map.Entry<String, String> entry : envReqs.entrySet()) {
			String templateDirName = entry.getKey();
			String topoId = entry.getValue();
			String dir = basePath + File.separator + templateDirName;
			// 读取
			MDParser mdParser = new MDParser(dir, topoId);
			// 资管导入的模板不同类型没有重名，只需要用name验证是否已导入
			String templateName = mdParser.getName();
			boolean overWritten;
			String businessTemplateId = businessTemplateService.getIdByName(templateName);
			// 已存在则覆盖更新训练模板
			if (businessTemplateId == null) {
				businessTemplateId = IDGeneratorUtil.getID();
				overWritten = false;
			} else {
				log.info(String.format("Template [%s] already exists. Gonna overwrite it.",
						templateName));
				overWritten = true;
			}
			mdParser.loadDealError(businessTemplateId);
			BusinessTemplate template = mdParser.getBusinessTemplate();
			List<MissionTemplate> missionTemplateList = mdParser.getMissionTemplates();
			Map<String, String> attachments = mdParser.getImages();
			// edit topo
			localSceneService.setTopoType(topoId, businessTemplateService
					.convertTypeToCode(template.getBusinessType()).getCode());

			// get handler
			BusinessTemplateHandler handler = businessTemplateHandler
					.getHandler(template.getBusinessType());
			// auto load attack,engage relation
			List<String> preSkills = JsonUtil
					.toJsonObjectArray(mdParser.getTemplateContext().getPreSkills(), String.class);
			List<String> trainSkills = JsonUtil.toJsonObjectArray(
					mdParser.getTemplateContext().getTrainSkills(), String.class);
			Set<String> relatedTechs = Sets.newHashSet(preSkills);
			relatedTechs.addAll(trainSkills);
			final String btId = businessTemplateId;

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

					if (overWritten) {
						// 覆盖，删除任务和旧训练模板，更新businessTemplate
						handler.deleteTemplateRelated(template);
						rtksRelatedService.clearAttackEngageRel(btId);
						businessTemplateService.updateById(template);
					} else {
						// 新增，保存businessTemplate
						businessTemplateService.save(template);
					}
					missionTemplateService.batchInsert(missionTemplateList);
					handler.loadTrainTemplate(mdParser.getTemplateContext());
					attachmentService.loadZiguanImages(attachments);
					rtksRelatedService.createAttackEngageRel(btId, relatedTechs);
				}
			});
		}

		return new BaseRsp<>(true);
	}

	/**
	 * 检查是否已存在同名模板
	 * @param template_names 
	 * @return true: 存在 
	 */
	@ApiOperation("导入模板重名校验")
	@PostMapping("/drm/check_template_name")
	public BaseRsp<List<String>> batchCheckName(@RequestBody @Valid List<String> template_names) {
		List<String> res = businessTemplateService.getExistedNames(template_names);

		return new BaseRsp<>(res);
	}

	@ApiOperation("辅助鉴权")
	@GetMapping("/auth")
	public BaseRsp auth(@ApiParam(name = "token", value = "jwt token") String token) {
		try {
			remoteUserService.doAuthentication(token);
		} catch (Exception e) {
			log.error("authentication failed, token={}", token, e);
			throw new AuthenticationException();
		}
		return new BaseRsp();
	}
}
