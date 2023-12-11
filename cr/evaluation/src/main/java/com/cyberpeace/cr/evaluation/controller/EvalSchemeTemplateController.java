package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.resource.BatchDumpReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSchemeTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalSchemeTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionSchemeTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSchemeTemplate;
import com.cyberpeace.cr.evaluation.service.EvalSchemeTemplateService;
import com.cyberpeace.cr.evaluation.service.impl.bo.StageTemplateData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Api(tags = "靶场测评-技战法")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/mission/template/")
@Slf4j
public class EvalSchemeTemplateController {

	@Autowired
	private EvalSchemeTemplateService evalSchemeTemplateService;

	@ApiOperation("新增技战法")
	@PostMapping("")
	public BaseRsp<String> addEvalSchemeTemplate(@RequestBody @Validated EvalSchemeTemplateReq evalSchemeTemplateReq) {
		EvalSchemeTemplate evalSchemeTemplate = BeanUtils.copy(evalSchemeTemplateReq, EvalSchemeTemplate.class);
		evalSchemeTemplate.setContent(JsonUtil.toJsonString(evalSchemeTemplateReq.getContent()));
		evalSchemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalSchemeTemplate.setTemplateData(JsonUtil.toJsonString(evalSchemeTemplateReq.getTemplateData()));
		evalSchemeTemplateService.addEvalSchemeTemplate(evalSchemeTemplate);
		return new BaseRsp<>(evalSchemeTemplate.getResourceId());
	}

	@ApiOperation("查看技战法详情")
	@GetMapping("")
	public BaseRsp<EvalSchemeTemplateReq> getEvalSchemeTemplate(@ApiParam(name = "resource_id", value = "资源编号", required = true) @RequestParam("resource_id") String resourceId) {
		EvalSchemeTemplate evalSchemeTemplate = evalSchemeTemplateService.getEvalSchemeTemplate(resourceId);
		EvalSchemeTemplateReq evalSchemeTemplateReq = BeanUtils.copy(evalSchemeTemplate, EvalSchemeTemplateReq.class);
		evalSchemeTemplateReq.setTemplateData(JsonUtil.toJsonObjectArray(evalSchemeTemplate.getTemplateData(), StageTemplateData.class));
		evalSchemeTemplateReq.setContent(JsonUtil.toJsonObject(evalSchemeTemplate.getContent(), String[][].class));
		return new BaseRsp<>(evalSchemeTemplateReq);
	}

	@ApiOperation("删除技战法")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalSchemeTemplate(@RequestBody DeleteReq deleteReq) {
		evalSchemeTemplateService.deleteEvalSchemeTemplate(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改技战法")
	@PatchMapping("")
	public BaseRsp<Boolean> updateEvalSchemeTemplate(@RequestBody @Validated UpdateEvalSchemeTemplateReq updateEvalMissionSchemeTemplateReq) {
		EvalSchemeTemplate evalSchemeTemplate = BeanUtils.copy(updateEvalMissionSchemeTemplateReq, EvalSchemeTemplate.class);
		evalSchemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalSchemeTemplate.setTemplateData(JsonUtil.toJsonString(updateEvalMissionSchemeTemplateReq.getTemplateData()));
		evalSchemeTemplate.setContent(JsonUtil.toJsonString(updateEvalMissionSchemeTemplateReq.getContent()));
		evalSchemeTemplateService.updateEvalSchemeTemplate(evalSchemeTemplate);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查看技战法列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalMissionSchemeTemplateListRsp>> getSchemeTemplate(@ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
		Page evalMissionSchemeTemplatePage = pageReqBase.toPageParam();
		List<EvalMissionSchemeTemplateListRsp> evalMissionSchemeTemplateListRspList = evalSchemeTemplateService.getEvalSchemeTemplateList(pageReqBase);
		return new BaseRsp<>(PageUtil.getListResult(evalMissionSchemeTemplateListRspList, evalMissionSchemeTemplatePage, evalMissionSchemeTemplateListRspList.size()));
	}

	@ApiOperation("导出技战法")
	@PostMapping("/export")
	public void exportScheme(HttpServletResponse response,
							 @RequestBody BatchDumpReq dumpReq) {
		String tempDir = ZipUtil.generatePath();
		try {
			String zipPath = evalSchemeTemplateService.exportSchemeTemplate(dumpReq.getIds(), tempDir);
			// 2. generate file response
			File zip = new File(zipPath);
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			log.error("failed to dump combat techs", e);
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_EXPORT_EVAL_SCHEME_TEMPLATE);
		} finally {
			// 3. delete temp dir
			if (!FileUtil.deleteDir(new File(tempDir))) {
				log.error("failed to remove temp dir :{}", tempDir);
			}
		}
	}

	@ApiOperation("导入技战法")
	@PostMapping("/import")
	public BaseRsp<String> importScheme(@RequestParam("file") MultipartFile file) throws IOException, ZipException {
		// 1. save file to temp dir
		File localFile = FileUtil.saveFile(file, ZipUtil.generatePath());
		// 2. unzip file
		String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
		// 3. load resources
		evalSchemeTemplateService.importSchemeTemplate(unZipPath);
		// 4. delete temp dir
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}
		return new BaseRsp<>();
	}

}
