package com.cyberpeace.cr.crbase.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.controller.req.resource.BatchLoadMdReq;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.resource.MDParser;
import com.cyberpeace.cs.service.AttachmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	private MissionService missionService;

	@Autowired
	private BusinessTemplateHandler businessTemplateHandler;

	@Autowired
	private AttachmentService attachmentService;

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
			// 跳过已存在
			String templateName = mdParser.getName();
			if (businessTemplateService.existByName(templateName)) {
				log.info(String.format("template [%s] already exists.", templateName));
				continue;
			}
			mdParser.load();
			BusinessTemplate template = mdParser.getBusinessTemplate();
			List<MissionTemplate> missionTemplateList = mdParser.getMissionTemplates();
			Map<String, String> attachments = mdParser.getImages();
			// get handler
			BusinessTemplateHandler handler = businessTemplateHandler
					.getHandler(template.getBusinessType());

			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

					businessTemplateService.save(template);
					missionService.batchInsertMissionTemplates(missionTemplateList);
					handler.loadTrainTemplate(mdParser.getTemplateContext());
					attachmentService.loadZiguanImages(attachments);
				}
			});
		}

		return new BaseRsp<>(true);
	}

}
