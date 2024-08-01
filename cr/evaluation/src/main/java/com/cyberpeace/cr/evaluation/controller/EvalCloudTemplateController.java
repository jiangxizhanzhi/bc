package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.resource.BatchDumpReq;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags = "后台-云场景模板")
@RestController
@RequestMapping("/cr/eval/cloud/template")
@RequiresAuthentication
public class EvalCloudTemplateController {

	@Autowired
	private EvalCloudTemplateService evalCloudTemplateService;


	@ApiOperation("新增云场景模板")
	@PostMapping("")
	public BaseRsp<Boolean> addCloudTemplate(@RequestBody EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		evalCloudTemplateService.addCloudTemplate(evalCloudTemplateAddReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改云场景模板")
	@PutMapping("")
	public BaseRsp<Boolean> modifyCloudTemplate(@RequestBody EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		evalCloudTemplateService.modifyCloudTemplate(evalCloudTemplateAddReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("删除云场景模板")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteCloudTemplate(@RequestBody DeleteReq deleteReq) {
		evalCloudTemplateService.deleteCloudTemplate(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("分页查询云场景模板")
	@GetMapping("/page")
	public BaseRsp<ListRsp<EvalCloudTemplateListRsp>> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq) {
		List<EvalCloudTemplateListRsp> evalCloudTemplateListRspList = evalCloudTemplateService.getCloudTemplateList(evalCloudTemplateListReq);
		return new BaseRsp<>(PageUtil.getListResult(evalCloudTemplateListRspList, evalCloudTemplateListReq.toPageParam(), evalCloudTemplateListRspList.size()));
	}

	@ApiOperation("查询云场景模板详情")
	@GetMapping("")
	public BaseRsp<EvalCloudTemplateAddReq> getCloudTemplateDetail(@ApiParam(name = "cloud_template_id", value = "云模板id") @RequestParam("cloud_template_id") String cloudTemplateId) {
		return new BaseRsp<>(evalCloudTemplateService.getCloudTemplate(cloudTemplateId));
	}

	@ApiOperation("获取云模板基础信息相关枚举")
	@GetMapping("/enum")
	public BaseRsp<EvalCloudTemplateEnumRsp> getCloudTemplateEnum() {
		return new BaseRsp<>(evalCloudTemplateService.getCloudTemplateEnum());
	}

	@ApiOperation("批量导出场景模板")
	@PostMapping("/batchDump")
	public void batchDump(HttpServletResponse response, @RequestBody BatchDumpReq dumpReq) {

		List<String> templateIds = dumpReq.getIds();
		// 1. generate dump file
		String tempDir = ZipUtil.generatePath();
		try {
			String zipPath = evalCloudTemplateService.makeDumpFile(templateIds, tempDir);
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

	@ApiOperation("批量导入场景模板")
	@PutMapping("/batchLoad")
	public BaseRsp<Boolean> batchLoad(@RequestParam("file") MultipartFile file)
			throws IOException, ZipException {

		FileUtil.validateSuffix(file, FileSunffixConstant.ZIP.getDesc());
		// 1. save file to temp dir
		File localFile = FileUtil.saveFile(file, ZipUtil.generatePath());
		// 2. unzip file
		String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
		// 3. load resources
		evalCloudTemplateService.batchLoad(unZipPath);
		// 4. delete temp dir
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}
		return new BaseRsp<>();
	}
}
