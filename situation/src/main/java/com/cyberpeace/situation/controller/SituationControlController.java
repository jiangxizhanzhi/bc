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

/**
 * 态势控制Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "态势编排")
@RestController
@Slf4j
@RequestMapping("/situation/control")
public class SituationControlController {

	/**
	 * 动态方案Service
	 *
	 * @see DynamicSchemeService
	 */
	@Autowired
	private DynamicSchemeService dynamicSchemeService;

	/**
	 * 附件Service
	 *
	 * @see AttachmentService
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 场景配置Service
	 *
	 * @see SceneConfigService
	 */
	@Autowired
	private SceneConfigService sceneConfigService;

	/**
	 * 新增动态动效方案
	 *
	 * @param addDynamicSchemeReq 新增动态方案请求体
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see AddDynamicSchemeReq
	 * @see BaseRsp<String>
	 */
	@ApiOperation("新增动效方案")
	@PostMapping("/scheme")
	public BaseRsp<String> addDynamicEffectScheme(
			@RequestBody @Validated AddDynamicSchemeReq addDynamicSchemeReq) {
		dynamicSchemeService.validDynamicSchemeName(addDynamicSchemeReq);
		String resourceId = dynamicSchemeService.addDynamicScheme(addDynamicSchemeReq);
		return new BaseRsp<>(resourceId);
	}

	/**
	 * 获取动态动效方案
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<DynamicSchemeRes> }
	 * @throws
	 * @see String
	 * @see BaseRsp<DynamicSchemeRes>
	 */
	@ApiOperation("查看动效方案")
	@GetMapping("/scheme")
	public BaseRsp<DynamicSchemeRes>
			getDynamicEffectScheme(@ApiParam(name = "resource_id", value = "动效方案编号",
					required = true) @RequestParam(value = "resource_id") String resourceId) {
		DynamicSchemeRes dynamicSchemeRes = dynamicSchemeService.getDynamicSchemeById(resourceId);
		return new BaseRsp<>(dynamicSchemeRes);
	}

	/**
	 * 更新动态动效方案
	 *
	 * @param dynamicSchemeReq 动态方案请求体
	 * @return {@code BaseRsp<DynamicSchemeReq> }
	 * @throws
	 * @see DynamicSchemeReq
	 * @see BaseRsp<DynamicSchemeReq>
	 */
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

	/**
	 * 删除动态动效方案
	 *
	 * @param schemeIdListReq 方案id列表请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SchemeIdListReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("删除动效方案")
	@DeleteMapping("/scheme")
	public BaseRsp<Boolean>
			deleteDynamicEffectScheme(@RequestBody @Validated SchemeIdListReq schemeIdListReq) {
		for (String dynamicSchemeId : schemeIdListReq.getSchemeIds()) {
			dynamicSchemeService.deleteDynamicScheme(dynamicSchemeId);
		}
		return new BaseRsp<>(true);
	}

	/**
	 * 获取全部动态名称
	 *
	 * @param sceneId 场景id
	 * @return {@code BaseRsp<List<DynamicSchemeNameRes>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<DynamicSchemeNameRes>>
	 */
	@ApiOperation("所有动效方案名称")
	@GetMapping("/scheme_names")
	public BaseRsp<List<DynamicSchemeNameRes>> getAllDynamicName(@ApiParam(name = "scene_id",
			value = "场景编号", required = true) @RequestParam(value = "scene_id") String sceneId) {
		List<DynamicSchemeNameRes> names = dynamicSchemeService.getAllDynamicName(sceneId);
		return new BaseRsp<>(names);
	}

	/**
	 * 获取全部选项
	 *
	 * @param sceneId    场景id
	 * @param isInstance 是否实例
	 * @return {@code BaseRsp<OptionsRes> }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see BaseRsp<OptionsRes>
	 */
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

	/**
	 * Run 攻击消息
	 *
	 * @param runAttackMessageReq 跑攻击消息请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see RunAttackMessageReq
	 * @see BaseRsp<Boolean>
	 */
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
			dynamicSchemeService.pushAttackToRoom(attackMessageBo, elementIds);
		}
		return new BaseRsp<>();
	}

	/**
	 * 运行 Dynamic Scheme
	 *
	 * @param dynamicSchemeReq 动态方案请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see DynamicSchemeReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("运行动效方案")
	@PostMapping("/run_dynamic_scheme")
	public BaseRsp<Boolean> runDynamicScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq) {
		dynamicSchemeService.validStage(dynamicSchemeReq);
		dynamicSchemeService.validMedia(dynamicSchemeReq);
		dynamicSchemeService.clearMedia(dynamicSchemeReq.getSceneId());
		dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeReq);
		return new BaseRsp<>();
	}

	/**
	 * 上传媒体
	 *
	 * @param multipartFile Multipart 文件
	 * @return {@code BaseRsp<AttachmentBo> }
	 * @throws
	 * @see MultipartFile
	 * @see BaseRsp<AttachmentBo>
	 */
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

	/**
	 * 获取附表
	 *
	 * @param sceneId 场景id
	 * @return {@code BaseRsp<List<WsAttackSchedule>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<WsAttackSchedule>>
	 */
	@ApiOperation("获取当前进程")
	@GetMapping("/schedule")
	public BaseRsp<List<WsAttackSchedule>> getSchedule(@ApiParam(name = "scene_id", value = "场景编号",
			required = true) @RequestParam(value = "scene_id") String sceneId) {
		List<WsAttackSchedule> schedule = dynamicSchemeService.getSchedule(sceneId);
		return new BaseRsp<>(schedule);
	}

	/**
	 * 导出方案
	 *
	 * @param response   响应
	 * @param resourceId 资源id
	 * @throws
	 * @see HttpServletResponse
	 * @see String
	 */
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

	/**
	 * 导入方案
	 *
	 * @param importDynamicSchemeReq 导入动态方案请求体
	 * @return {@code BaseRsp<String> }
	 * @throws @throws      IOException io异常
	 * @throws ZipException zip 异常
	 * @see ImportDynamicSchemeReq
	 * @see BaseRsp<String>
	 */
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
