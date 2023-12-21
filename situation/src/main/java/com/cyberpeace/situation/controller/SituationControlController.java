package com.cyberpeace.situation.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.situation.req.ImportDynamicSchemeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SceneConfigService;
import com.cyberpeace.situation.service.impl.DynamicSchemeServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

@Api(tags = "态势编排")
@RestController
@Slf4j
@RequestMapping("/situation/control")
public class SituationControlController {

	@Autowired
	private DynamicSchemeService dynamicSchemeService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private SceneConfigService sceneConfigService;

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
	public BaseRsp<DynamicSchemeRes>
			getDynamicEffectScheme(@ApiParam(name = "resource_id", value = "动效方案编号",
					required = true) @RequestParam(value = "resource_id") String resourceId) {
		DynamicSchemeRes dynamicSchemeRes = dynamicSchemeService.getDynamicSchemeById(resourceId);
		return new BaseRsp<>(dynamicSchemeRes);
	}

	@ApiOperation("更新动效方案")
	@PatchMapping("/scheme")
	public BaseRsp<DynamicSchemeReq>
			updateDynamicEffectScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq) {
		dynamicSchemeService.validStage(dynamicSchemeReq);
		dynamicSchemeService.validMedia(dynamicSchemeReq);
		DynamicSchemeReq dynamicSchemeReq1 = dynamicSchemeService
				.updateDynamicScheme(dynamicSchemeReq);
		return new BaseRsp<>(dynamicSchemeReq1);
	}

	@ApiOperation("删除动效方案")
	@DeleteMapping("/scheme")
	public BaseRsp<Boolean>
			deleteDynamicEffectScheme(@RequestBody @Validated SchemeIdListReq schemeIdListReq) {
		for (String dynamicSchemeId : schemeIdListReq.getSchemeIds()) {
			dynamicSchemeService.deleteDynamicScheme(dynamicSchemeId);
		}
		return new BaseRsp<>(true);
	}

	@ApiOperation("所有动效方案名称")
	@GetMapping("/scheme_names")
	public BaseRsp<List<DynamicSchemeNameRes>> getAllDynamicName(@ApiParam(name = "scene_id",
			value = "场景编号", required = true) @RequestParam(value = "scene_id") String sceneId) {
		List<DynamicSchemeNameRes> names = dynamicSchemeService.getAllDynamicName(sceneId);
		return new BaseRsp<>(names);
	}

	@ApiOperation("返回所有选择项")
	@GetMapping("/option")
	public BaseRsp<OptionsRes> getAllOption(
			@ApiParam(name = "scene_id", value = "场景编号",
					required = true) @RequestParam(value = "scene_id") String sceneId,
			@ApiParam(name = "is_instance", value = "是否为实例场景") @RequestParam(value = "is_instance",
					required = false) Boolean isInstance) {
		OptionsRes optionsRes = new OptionsRes();
		dynamicSchemeService.getLocalOption(optionsRes);
		dynamicSchemeService.getRemoteOption(optionsRes, sceneId, isInstance);
		return new BaseRsp<>(optionsRes);
	}

	@ApiOperation("运行单个动效")
	@PostMapping("/run_attack_message")
	public BaseRsp<Boolean> runAttackMessage(@RequestBody @Validated RunAttackMessageReq runAttackMessageReq) {
		AttackMessageReq attackMessageBo = BeanUtils.copy(runAttackMessageReq,
				AttackMessageReq.class);
		String sceneId = runAttackMessageReq.getSceneId();
		dynamicSchemeService.clearMedia(sceneId);
		dynamicSchemeService.clearRoomSchedule(sceneId);
		String code = sceneConfigService.updateSceneCode(sceneId);
		List<String> elementIds = dynamicSchemeService
				.getElementIds(sceneId, runAttackMessageReq.getIsInstance());
		if (runAttackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
			dynamicSchemeService.pushMediaToRoom(attackMessageBo, code);
		} else {
			dynamicSchemeService.pushAttackToRoom(attackMessageBo, elementIds, false);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("中止单个动效")
	@PostMapping("/stop_attack_message")
	public BaseRsp<Boolean> stopAttackMessage(@RequestBody @Validated StopAttackMessageReq stopAttackMessageReq) {
		AttackMessageReq attackMessageBo = BeanUtils.copy(stopAttackMessageReq,
				AttackMessageReq.class);
		String sceneId = stopAttackMessageReq.getSceneId();
		dynamicSchemeService.clearMedia(sceneId);
		dynamicSchemeService.clearRoomSchedule(sceneId);
		String code = sceneConfigService.updateSceneCode(sceneId);
		List<String> elementIds = dynamicSchemeService
				.getElementIds(sceneId, stopAttackMessageReq.getIsInstance());
		if (stopAttackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
			dynamicSchemeService.pushMediaToRoom(attackMessageBo, code);
		} else {
			dynamicSchemeService.pushAttackToRoom(attackMessageBo, elementIds, true);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("运行动效方案")
	@PostMapping("/run_dynamic_scheme")
	public BaseRsp<Boolean> runDynamicScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq) {
		dynamicSchemeService.validStage(dynamicSchemeReq);
		dynamicSchemeService.validMedia(dynamicSchemeReq);
		dynamicSchemeService.clearMedia(dynamicSchemeReq.getSceneId());
		dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeReq);
		return new BaseRsp<>();
	}

	@ApiOperation("上传媒体文件")
	@PostMapping("/upload_media")
	public BaseRsp<AttachmentBo> uploadMedia(@ApiParam(value = "上传文件",
			required = true) @RequestPart("file") MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		FileUtil.validateFileSuffix(fileName,
				DynamicSchemeServiceImpl.PICTURE_TYPE + "," + DynamicSchemeServiceImpl.VIDEO_TYPE);
		AttachmentBo attachmentBo = attachmentService
				.uploadFile(AttachmentPath.UPLOAD_SITUATION.getCode(), true, multipartFile, true);
		attachmentBo.setFilePath(attachmentBo.getFilePath());
		return new BaseRsp<>(attachmentBo);
	}

	@ApiOperation("获取当前进程")
	@GetMapping("/schedule")
	public BaseRsp<List<WsAttackSchedule>> getSchedule(@ApiParam(name = "scene_id", value = "场景编号",
			required = true) @RequestParam(value = "scene_id") String sceneId) {
		List<WsAttackSchedule> schedule = dynamicSchemeService.getSchedule(sceneId);
		return new BaseRsp<>(schedule);
	}

	@ApiOperation("导出动效方案")
	@GetMapping("/scheme/export")
	public void exportScheme(HttpServletResponse response,
			@ApiParam("动效方案id") @RequestParam("resource_id") @Validated String resourceId) {
		// 1. generate dump file
		String tempDir = ZipUtil.generatePath();
		try {
			String zipPath = dynamicSchemeService.makeExportFile(resourceId, tempDir);
			// 2. generate file response
			File zip = new File(zipPath);
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			log.error("failed to dump combat techs", e);
			throw new BusinessException(SituationErrorCode.FAILED_TO_EXPORT_DYNAMIC_SCHEME);
		} finally {
			// 3. delete temp dir
			if (!FileUtil.deleteDir(new File(tempDir))) {
				log.error("failed to remove temp dir :{}", tempDir);
			}
		}
	}

	@ApiOperation("导入动效方案")
	@PostMapping("/scheme/import")
	public BaseRsp<String> importScheme(@Validated ImportDynamicSchemeReq importDynamicSchemeReq)
			throws IOException, ZipException {

		BaseRsp<String> result = new BaseRsp<>();
		// 1. save file to temp dir
		File localFile = FileUtil.saveFile(importDynamicSchemeReq.getFile(),
				ZipUtil.generatePath());
		// 2. unzip file
		String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
		// 3. load resources
		String resourceId = dynamicSchemeService.importDynamicScheme(unZipPath,
				importDynamicSchemeReq.getSceneId(), importDynamicSchemeReq.getIsInstance());
		// 4. delete temp dir
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}
		result.setData(resourceId);
		return result;
	}

}
