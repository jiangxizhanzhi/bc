package com.cyberpeace.situation.controller;

import java.io.*;
import java.util.Date;
import java.util.Map;


import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cs.controller.CommonController;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.situation.annotation.AuditLogger;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.dao.entity.DashboardComponents;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 态势大屏-原始模板
 *
 * @Author xuxz
 * @Date 10/11/2022 上午11:58
 */

@Api(tags = "态势大屏-原始模板")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("dashboard/template/")
public class DashboardTemplateController {

	@Autowired
	private DashboardTemplateService dashboardTemplateService;

	@Autowired
	private CommonController commonController;

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 新建态势模板
	 *
	 * @param dashBoardTemplateReq 态势模板请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.XZ)
	@ApiOperation("原始模板-新增")
	@PostMapping()
	public BaseRsp<String> createDashboardTemplate(
			@RequestBody @Validated(Create.class) DashBoardTemplateReq dashBoardTemplateReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dashBoardTemplateReq.getName(), null);

		return new BaseRsp<>(
				dashboardTemplateService.createDashboardTemplate(dashBoardTemplateReq));
	}

	/**
	 * 复制态势模板
	 *
	 * @param dashBoardTemplateCopyReq 复制态势模板请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.XZ)
	@ApiOperation("原始模板-复制")
	@PostMapping("/copy/")
	public BaseRsp<String> copyDashboardTemplate(
			@RequestBody @Validated DashBoardTemplateCopyReq dashBoardTemplateCopyReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dashBoardTemplateCopyReq.getName(), null);

		return new BaseRsp<>(dashboardTemplateService.copyDashboardTemplate(
				dashBoardTemplateCopyReq.getTemplateId(), dashBoardTemplateCopyReq.getName()));
	}

	/**
	 * 导出态势模板
	 *
	 * @param dashBoardTemplateId 导出态势模板请求
	 *
	 */
	@ApiOperation("态势模板-导出")
	@GetMapping("/export/")
	public void exportDashboardTemplate(HttpServletResponse response, @ApiParam(name = "template_id", value = "模板id",
			required = true) @RequestParam("template_id") String dashBoardTemplateId) {
		// 1. generate dump file
		DashboardTemplate dashboardTemplate = dashboardTemplateService.selectDashboardTemplateById(dashBoardTemplateId);

		String fileName = dashboardTemplate.getName() + "_模板数据_" + TimeUtil.format5(new Date());
		String tempDir = ZipUtil.generatePath(fileName);

		try {
			String zipPath = dashboardTemplateService.exportDashboardTemplate(tempDir,
					dashBoardTemplateId);
			// 2. generate file response
			File zip = new File(zipPath);
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			log.error("failed to dump dashboard template", e);
			throw new BusinessException(SituationErrorCode.FAILED_TO_EXPORT_DYNAMIC_SCHEME);
		} finally {
			// 3. delete temp dir
			if (!FileUtil.deleteDir(new File(tempDir))) {
				log.error("failed to remove temp dir :{}", tempDir);
			}
		}

	}

	/**
	 * 导入态势模板
	 *
	 * @param req {@code DashBoardTemplateImportReq}
	 * @return {@code BaseRsp<Boolean>}
	 */
	@ApiOperation("态势模板-导入")
	@PostMapping("/import_template")
	public BaseRsp<Boolean> importTemplate(@Validated DashBoardTemplateImportReq req)
			throws IOException, ZipException {

		// 1. 临时存储导入上传的文件
		File markFile = FileUtil.saveFile(req.getFile(), ZipUtil.generatePath());

		// 2. 解压文件
		String unZipPath = ZipUtil.unZipToTmpPath(markFile.getAbsolutePath());

		// 3. 导入模板
		// 3.1 读取导入文件
		String dataJson = FileUtil
				.getContent(new File(unZipPath + File.separator + CrCommonConstants.RESOURCE_FILE_NAME));
		// 3.2 提取模板数据
		dashboardTemplateService.importDashboardTemplate(dataJson, unZipPath);

		// 4. 删除临时文件
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}

		String message = "模板导入成功";
		return new BaseRsp(true, message);
	}


	/**
	 * 修改态势模板
	 *
	 * @param dashBoardTemplateReq 态势模板请求
	 * @return {@code BaseRsp<String>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.XG)
	@ApiOperation("原始模板-修改")
	@PatchMapping("")
	public BaseRsp<String> updateDashboardTemplate(
			@RequestBody @Validated(Update.class) DashBoardTemplateReq dashBoardTemplateReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(dashBoardTemplateReq.getName(), null);

		return new BaseRsp<>(
				dashboardTemplateService.updateDashboardTemplate(dashBoardTemplateReq));
	}

	/**
	 * 查看态势模板详情
	 *
	 * @param resourceId 资源id
	 * @param type       视角类型
	 * @return {@code BaseRsp<DashboardTemplateRes>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.CX)
	@ApiOperation("原始模板-单个查询")
	@GetMapping("")
	public BaseRsp<DashboardTemplateRes> getDashboardTemplateInfo(
			@RequestParam("resource_id") @ApiParam(required = true, name = "resource_id",
					value = "资源id") String resourceId,
			@RequestParam(value = "type", required = false) @ApiParam(name = "type",
					value = "视角类型") String type) {
		return new BaseRsp<>(
				dashboardTemplateService.getDashboardTemplateInfo(resourceId, type, Boolean.FALSE));
	}

	/**
	 * 删除态势模板
	 *
	 * @param dashboardTemplateDelReq 态势模板删除请求
	 * @return {@code BaseRsp<Integer>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.SC)
	@ApiOperation("原始模板-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteDashboardTemplate(
			@RequestBody @Validated DashboardTemplateDelReq dashboardTemplateDelReq) {
		return new BaseRsp<>(dashboardTemplateService
				.deleteDashboardTemplate(dashboardTemplateDelReq.getDashboardTemplateIdList()));
	}

	/**
	 * 查询态势模板列表
	 *
	 * @param dashboardTemplatePageReq 态势模板分页请求
	 * @return {@code BaseRsp<ListRsp<DashboardTemplatePageRes>>}
	 */
	@AuditLogger(subModule = ServiceLogContext.SubModule.DPMB, type = ServiceLogContext.Type.CX)
	@ApiOperation("原始模板-列表查询")
	@GetMapping("list")
	public BaseRsp<ListRsp<DashboardTemplatePageRes>>
			getDashboardTemplatePage(DashboardTemplatePageReq dashboardTemplatePageReq) {
		return new BaseRsp<>(
				dashboardTemplateService.getDashboardTemplatePage(dashboardTemplatePageReq));
	}

	/**
	 * 获取组件类型
	 *
	 * @return {@code BaseRsp<Map<Integer, String>>}
	 */
	@ApiOperation("模板组件-获取组件类型")
	@GetMapping("type/list")
	public BaseRsp<Map<Integer, String>> getComponentType() {
		return new BaseRsp<>(DashboardComponents.getComponentType());
	}

	/**
	 * 获取模板等级列表
	 *
	 * @return {@code BaseRsp<Map<Integer, String>>}
	 */
	@ApiOperation("模板属性-获取模板等级列表")
	@GetMapping("level/list")
	public BaseRsp<Map<Integer, String>> getDashboardTemplateLevel() {
		return new BaseRsp<>(DashboardTemplate.getDashboardTemplateLevel());
	}

	/**
	 * 获取echarts数据
	 *
	 * @param componentsDataReq 组件数据请求
	 * @return {@code BaseRsp<JSONArray>}
	 */
	@ApiOperation("模板组件-获取echarts数据")
	@PostMapping("option/data")
	public BaseRsp<JSONArray> getEchartsOption(@RequestBody ComponentsDataReq componentsDataReq) {
		return new BaseRsp<>(dashboardTemplateService.getComponentsEchartsData(componentsDataReq));
	}

	/**
	 * Base64截图上传
	 *
	 * @param imageReq 图片请求
	 * @return {@code BaseRsp<AttachmentBo>}
	 */
	@ApiOperation("Base64截图上传")
	@PostMapping("upload/image")
	public BaseRsp<AttachmentBo> uploadBackgroundImage(@RequestBody @Validated ImageReq imageReq) {

		// 获取图片base64
		String base64 = imageReq.getBase64();
		if (base64.contains("data:image")) {
			base64 = base64.substring(base64.indexOf(",") + 1);
		}
		base64 = base64.replace("\r\n", "");

		// 创建文件目录
		String prefix = ".png";
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		InputStream input = null;
		OutputStream os = null;
		try {

			// 创建临时文件
			File file = File.createTempFile(IDGeneratorUtil.getID(), prefix);
			byte[] bytes = Base64.decodeBase64(base64);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bytes);
			FileItem item = new DiskFileItemFactory().createItem("file",
					MediaType.MULTIPART_FORM_DATA_VALUE, true, file.getName());

			input = new FileInputStream(file);
			os = item.getOutputStream();
			// 流转移
			IOUtils.copy(input, os);
			CommonsMultipartFile commonsMultipartFile = new CommonsMultipartFile(item);
			FileCommonUploadReq fileCommonUploadReq = new FileCommonUploadReq();
			fileCommonUploadReq.setFile(commonsMultipartFile);
			BaseRsp<AttachmentBo> attachmentBoBaseRsp = commonController
					.imageUpload(fileCommonUploadReq);
			// 删除图片
			file.delete();
			return attachmentBoBaseRsp;
		} catch (Exception e) {
			throw new BusinessException(SituationErrorCode.RELEASE_REPEAT_ERROR);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 静态数据Json转换
	 *
	 * @param file 文件
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("静态数据Json转换")
	@PutMapping("import")
	public BaseRsp<JSONObject>
			importTwoDimensionalComponents(@RequestParam("file") MultipartFile file) {
		JSONObject jsonObject;
		InputStream inputStream = null;
		try {
			FileUtil.validateSuffix(file, FileSunffixConstant.JSON.getDesc());
			inputStream = file.getInputStream();
			jsonObject = JsonUtil.toJsonObject(inputStream, JSONObject.class);
		} catch (IOException e) {
			throw new BusinessException(SituationErrorCode.COMPONENTS_IMPORT_FAILED);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new BaseRsp<>(jsonObject);
	}
}
