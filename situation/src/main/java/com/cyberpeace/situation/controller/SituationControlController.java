package com.cyberpeace.situation.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeReq;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.req.RunAttackMessageReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.impl.DynamicSchemeServiceImpl;
import net.lingala.zip4j.exception.ZipException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "态势编排")
@RestController
@Slf4j
@RequestMapping("/situation/control")
public class SituationControlController {

	@Autowired
	private DynamicSchemeService dynamicSchemeService;

	@Autowired
	private AttachmentService attachmentService;


	@ApiOperation("新增动效方案")
	@PostMapping("/scheme")
	public BaseRsp<String> addDynamicEffectScheme(
			@RequestBody @Validated AddDynamicSchemeReq addDynamicSchemeReq) {
		dynamicSchemeService.validDynamicSchemeName(addDynamicSchemeReq);
		String resourceId = dynamicSchemeService.addDynamicScheme(addDynamicSchemeReq);
		return new BaseRsp<>(resourceId);
	}

	@ApiOperation("查看动效方案")
	@GetMapping("/scheme")
	public BaseRsp<DynamicSchemeRes> getDynamicEffectScheme(@ApiParam("动效方案id") String resourceId) {
		DynamicSchemeRes dynamicSchemeRes = dynamicSchemeService.getDynamicSchemeById(resourceId);
		return new BaseRsp<>(dynamicSchemeRes);
	}

	@ApiOperation("更新动效方案")
	@PatchMapping("/scheme")
	public BaseRsp<DynamicSchemeReq>
			updateDynamicEffectScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq) {
		dynamicSchemeService.validStage(dynamicSchemeReq);
		DynamicSchemeReq dynamicSchemeReq1 = dynamicSchemeService
				.updateDynamicScheme(dynamicSchemeReq);
		return new BaseRsp<>(dynamicSchemeReq1);
	}

	@ApiOperation("删除动效方案")
	@DeleteMapping("/scheme/{resource_id}")
	public BaseRsp<?>
			deleteDynamicEffectScheme(@PathVariable("resource_id") String dynamicSchemeId) {
		dynamicSchemeService.deleteDynamicScheme(dynamicSchemeId);
		return new BaseRsp<>();
	}

	@ApiOperation("所有动效方案名称")
	@GetMapping("/scheme_names")
	public BaseRsp<List<DynamicSchemeNameRes>>
			getAllDynamicName(@ApiParam("场景id") @Validated @NotBlank String sceneId) {
		List<DynamicSchemeNameRes> names = dynamicSchemeService.getAllDynamicName(sceneId);
		return new BaseRsp<>(names);
	}

	@ApiOperation("返回所有选择项")
	@GetMapping("/option")
	public BaseRsp<OptionsRes> getAllOption(String sceneId) {
		OptionsRes optionsRes = new OptionsRes();
		dynamicSchemeService.getLocalOption(optionsRes);
		dynamicSchemeService.getRemoteOption(optionsRes, sceneId);
		return new BaseRsp<>(optionsRes);
	}

	@ApiOperation("运行单个动效")
	@PostMapping("/run_attack_message")
	public BaseRsp<?>
			runAttackMessage(@RequestBody @Validated RunAttackMessageReq runAttackMessageReq) {
		AttackMessageReq attackMessageBo = BeanUtils.copy(runAttackMessageReq,
				AttackMessageReq.class);
		dynamicSchemeService.clearMedia(runAttackMessageReq.getSceneId());
		if (runAttackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
			dynamicSchemeService.pushMediaToRoom(attackMessageBo);
		} else {
			dynamicSchemeService.pushAttackToRoom(attackMessageBo);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("运行动效方案")
	@PostMapping("/run_dynamic_scheme")
	public BaseRsp<?> runDynamicScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq) {
		dynamicSchemeService.validStage(dynamicSchemeReq);
		dynamicSchemeService.clearMedia(dynamicSchemeReq.getSceneId());
		dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeReq);
		return new BaseRsp<>();
	}

	@ApiOperation("上传媒体文件")
	@PostMapping("/upload_media")
	public BaseRsp<AttachmentBo> uploadMedia(@ApiParam(value = "上传文件",
			required = true) @RequestPart("file") MultipartFile multipartFile) {
		String fileName = multipartFile.getName();
		FileUtil.validateFileSuffix(fileName,
				DynamicSchemeServiceImpl.PICTURE_TYPE + "," + DynamicSchemeServiceImpl.VIDEO_TYPE);
		AttachmentBo attachmentBo = attachmentService
				.uploadFile(AttachmentPath.UPLOAD_SITUATION.getCode(), true, multipartFile, true);
		attachmentBo.setFilePath(attachmentBo.getFilePath());
		return new BaseRsp<>(attachmentBo);
	}

	@ApiOperation("获取当前进程")
	@GetMapping("/schedule")
	public BaseRsp<List<WsAttackSchedule>> getSchedule(@ApiParam("场景id") String sceneId) {
		List<WsAttackSchedule> schedule = dynamicSchemeService.getSchedule(sceneId);
		return new BaseRsp<>(schedule);
	}

	@ApiOperation("导出动效方案")
	@PostMapping("/scheme/export")
	public void exportScheme(HttpServletResponse response, @ApiParam("资源id") @NotBlank @Validated String resourceId){
		// 1. generate dump file
		String tempDir = ZipUtil.generatePath();
		try {
			String zipPath = dynamicSchemeService.makeExportFile(resourceId, tempDir);
			// 2. generate file response
			File zip = new File(zipPath);
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			log.error("failed to dump combat techs", e);
			throw new BusinessException(ErrorCode.FAILED_TO_EXPORT_DYNAMIC_SCHEME);
		} finally {
			// 3. delete temp dir
			if (!FileUtil.deleteDir(new File(tempDir))) {
				log.error("failed to remove temp dir :{}", tempDir);
			}
		}
	}

	@ApiOperation("导入动效方案")
	@PostMapping("/scheme/import")
	public BaseRsp importScheme(@RequestParam("file") MultipartFile file) throws IOException, ZipException {
		// 1. save file to temp dir
		File localFile = FileUtil.saveFile(file, ZipUtil.generatePath());
		// 2. unzip file
		String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
		// 3. load resources
		dynamicSchemeService.importDynamicScheme(unZipPath);
		// 4. delete temp dir
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}
		return new BaseRsp();
	}

}
