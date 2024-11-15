package com.cyberpeace.situation.controller;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.common.util.ZipUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.AttackMessageHistoryRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.ToolVuln;
import com.cyberpeace.situation.dao.entity.Vuln;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;
import com.cyberpeace.situation.service.AttackMessageHistoryService;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SceneConfigService;
import com.cyberpeace.situation.service.impl.DynamicSchemeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

	@Autowired
	private AttackMessageHistoryService attackMessageHistoryService;

	@ApiOperation("新增动效方案")
	@PostMapping("/scheme")
	public BaseRsp<String> addDynamicEffectScheme(
			@RequestBody @Validated AddDynamicSchemeReq addDynamicSchemeReq) {
		//校验动效方案名称
		dynamicSchemeService.validDynamicSchemeName(addDynamicSchemeReq);
		//添加动效方案
		String resourceId = dynamicSchemeService.addDynamicScheme(addDynamicSchemeReq);
		//返回动效方案编号
		return new BaseRsp<>(resourceId);
	}

	@ApiOperation("查看动效方案")
	@GetMapping("/scheme")
	public BaseRsp<DynamicSchemeRes>
			getDynamicEffectScheme(@ApiParam(name = "resource_id", value = "动效方案编号",
					required = true) @RequestParam(value = "resource_id") String resourceId) {
		//根据动效方案编号获取动效方案数据
		DynamicSchemeRes dynamicSchemeRes = dynamicSchemeService.getDynamicSchemeById(resourceId);
		//返回动效方案列表
		return new BaseRsp<>(dynamicSchemeRes);
	}

	@ApiOperation("更新动效方案")
	@PatchMapping("/scheme")
	public BaseRsp<DynamicSchemeReq>
			updateDynamicEffectScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq) {
		//校验动效方案阶段
		dynamicSchemeService.validStage(dynamicSchemeReq);
		//校验动效方案多媒体动效
		dynamicSchemeService.validMedia(dynamicSchemeReq);
		//更新并获取更新后的动效方案数据
		DynamicSchemeReq dynamicSchemeReq1 = dynamicSchemeService
				.updateDynamicScheme(dynamicSchemeReq);
		//返回更新后的动效方案数据
		return new BaseRsp<>(dynamicSchemeReq1);
	}

	@ApiOperation("删除动效方案")
	@DeleteMapping("/scheme")
	public BaseRsp<Boolean>
			deleteDynamicEffectScheme(@RequestBody @Validated SchemeIdListReq schemeIdListReq) {
		//循环删除
		for (String dynamicSchemeId : schemeIdListReq.getSchemeIds()) {
			//根据动效方案编号删除动效方案
			dynamicSchemeService.deleteDynamicScheme(dynamicSchemeId);
		}
		return new BaseRsp<>(true);
	}

	@ApiOperation("所有动效方案名称")
	@GetMapping("/scheme_names")
	public BaseRsp<List<DynamicSchemeNameRes>> getAllDynamicName(@ApiParam(name = "scene_id",
			value = "场景编号", required = true) @RequestParam(value = "scene_id") String sceneId) {
		//获取当前场景下的动效方案名称列表
		List<DynamicSchemeNameRes> names = dynamicSchemeService.getAllDynamicName(sceneId);
		//返回名称列表
		return new BaseRsp<>(names);
	}

	@ApiOperation("返回所有选择项")
	@GetMapping("/option")
	public BaseRsp<OptionsRes> getAllOption(
			@ApiParam(name = "scene_id", value = "场景编号",
					required = true) @RequestParam(value = "scene_id") String sceneId,
			@ApiParam(name = "is_instance", value = "是否为实例场景") @RequestParam(value = "is_instance",
					required = false) Boolean isInstance) {
		//创建空的返回结果
		OptionsRes optionsRes = new OptionsRes();
		//获取本地的动效选择项
		dynamicSchemeService.getLocalOption(optionsRes);
		//获取区域列表和节点列表
		dynamicSchemeService.getRemoteOption(optionsRes, sceneId, isInstance);
		//返回所有选项
		return new BaseRsp<>(optionsRes);
	}

	@ApiOperation("运行单个动效")
	@PostMapping("/run_attack_message")
	public BaseRsp<?>
			runAttackMessage(@RequestBody @Validated RunAttackMessageReq runAttackMessageReq) {
		//类型转换
		AttackMessageReq attackMessageBo = BeanUtils.copy(runAttackMessageReq,
				AttackMessageReq.class);
		//获取场景编号
		String sceneId = runAttackMessageReq.getSceneId();
		//清除多媒体动效
		dynamicSchemeService.clearMedia(sceneId);
		//清除当前场景的进程数据
		dynamicSchemeService.clearRoomSchedule(sceneId);
		//更新场景的code
		String code = sceneConfigService.updateSceneCode(sceneId);
		//获取节点列表
		List<String> elementIds = dynamicSchemeService
				.getElementIds(sceneId, runAttackMessageReq.getIsInstance());
		//判断是否为多媒体动效
//		if (runAttackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
			//推送多媒体动效
//			dynamicSchemeService.pushMediaToRoom(attackMessageBo, code, false);
//		} else {
			//推送普通动效
			dynamicSchemeService.pushDynamicToRoom(attackMessageBo, elementIds, false, false);
//		}
		return new BaseRsp<>();
	}

	@ApiOperation("运行动效方案")
	@PostMapping("/run_dynamic_scheme")
	public BaseRsp<?> runDynamicScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq) {
		//校验阶段
		dynamicSchemeService.validStage(dynamicSchemeReq);
		//校验多媒体动效
		dynamicSchemeService.validMedia(dynamicSchemeReq);
		//清除当前场景多媒体动效
		dynamicSchemeService.clearMedia(dynamicSchemeReq.getSceneId());
		//推送多媒体动效至场景
		dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeReq, null ,null);
		return new BaseRsp<>();
	}

	@ApiOperation("上传媒体文件")
	@PostMapping("/upload_media")
	public BaseRsp<AttachmentBo> uploadMedia(@ApiParam(value = "上传文件",
			required = true) @RequestPart("file") MultipartFile multipartFile) {
		//获取名称
		String fileName = multipartFile.getOriginalFilename();
		//校验文件后缀
		FileUtil.validateFileSuffix(fileName,
				DynamicSchemeServiceImpl.PICTURE_TYPE + "," + DynamicSchemeServiceImpl.VIDEO_TYPE);
		//上传媒体文件
		AttachmentBo attachmentBo = attachmentService
				.uploadFile(AttachmentPath.UPLOAD_SITUATION.getCode(), true, multipartFile, true);
		//设置文件路径
		attachmentBo.setFilePath(attachmentBo.getFilePath());
		return new BaseRsp<>(attachmentBo);
	}

	@ApiOperation("获取当前进程")
	@GetMapping("/schedule")
	public BaseRsp<List<WsAttackSchedule>> getSchedule(@ApiParam(name = "scene_id", value = "场景编号",
			required = true) @RequestParam(value = "scene_id") String sceneId) {
		//根据场景编号获取进程信息
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
			//动效方案导出
			String zipPath = dynamicSchemeService.makeExportFile(resourceId, tempDir);
			// 2. generate file response
			File zip = new File(zipPath);
			//生成文件相应
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			//打印错误日志
			log.error("failed to dump combat techs", e);
			//抛出异常导出动效方案失败
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
		return new BaseRsp(resourceId);
	}

	@ApiOperation("恢复")
	@PostMapping("/recovery")
	public void recovery(
			@ApiParam(name = "scene_id", value = "场景编号", required = true) @RequestParam(value = "scene_id") String sceneId){
		//恢复场景下的节点状态
		attackMessageHistoryService.recovery(sceneId);
	}

	@ApiOperation("情况回放")
	@PostMapping("/playback")
	public BaseRsp<Boolean> playback(@RequestBody @Validated PlaybackReq playbackReq) {
		//回放当前场景动效
		dynamicSchemeService.playbackPushDynamicSchemeToRoom(playbackReq.getSceneId(), TimeUtil.toDate1(playbackReq.getStartTime()), TimeUtil.toDate1(playbackReq.getEndTime()));
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取实景最大步骤")
	@GetMapping("/get_max_step")
	public BaseRsp<Integer> getMaxStep(Integer type) {
		if (type == null) {
			throw new BusinessException(SituationErrorCode.NO_TYPE);
		} else if (type.equals(0)) {
			return new BaseRsp<>(12);
		} else if (type.equals(1)) {
			return new BaseRsp<>(13);
		} else if (type.equals(2)){
			return new BaseRsp<>(11);
		} else {
			throw new BusinessException(SituationErrorCode.WRONG_TYPE);
		}
	}

	@ApiOperation("实景demo")
	@GetMapping("/demo")
	public void demo(@RequestParam("topo_id") String topoId,@RequestParam("step") Integer step) {
		dynamicSchemeService.pushDataToRealisticScene(topoId, step);
	}

	@ApiOperation("查看回放列表")
	@GetMapping("/playback/list")
	public BaseRsp<List<AttackMessageHistoryRes>> getAttackMessageHistoryList(
			@ApiParam(value = "场景编号") @RequestParam("scene_id") String sceneId,
			@ApiParam(value = "开始时间") @RequestParam(value = "start_time", required = false) String startTime,
			@ApiParam(value = "截止时间") @RequestParam(value = "end_time", required = false) String endTime) {
		return new BaseRsp(attackMessageHistoryService.getAttackMessageHistoryRes(sceneId, TimeUtil.toDate1(startTime),TimeUtil.toDate1(endTime)));
	}

	@ApiOperation("漏洞信息查询")
	@GetMapping("/queryVuln")
	public BaseRsp<List<Vuln>> queryLeak(@RequestParam(value = "application", required = false) String application,
										 @RequestParam(value = "node_id", required = false) String nodeId) {
		// todo 测试用
		if (application == null) {
			application = "Windows";
		}
		return new BaseRsp(dynamicSchemeService.queryLeak(application));
	}

	@ApiOperation("工具信息查询")
	@GetMapping("/queryTool")
	public BaseRsp<List<ToolVuln>> queryTool(@RequestParam(value = "vuln_id") String vulnId,@RequestParam(value = "tool_type") Integer toolType) {
		return new BaseRsp(dynamicSchemeService.queryTool(vulnId, toolType));
	}
}
