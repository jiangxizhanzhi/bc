package com.cyberpeace.situation.controller;

import static com.cyberpeace.situation.dao.entity.SituationCommonComponents.getCommonComponentsType;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.UploadAttachmentPermission;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.situation.controller.req.ColorThemeDelReq;
import com.cyberpeace.situation.controller.req.CommonComponentsReq;
import com.cyberpeace.situation.controller.req.HikVisionReq;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;
import com.cyberpeace.situation.service.SituationCommonComponentsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:08
 */
@Api(tags = "组件管理-通用组件")
@RestController
@RequiresAuthentication
@RequestMapping("/situation/components")
public class ComponentCommonController {

	@Autowired
	private SituationCommonComponentsService situationCommonComponentsService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@ApiOperation("获取控件组件资源列表")
	@GetMapping
	public BaseRsp<ListRsp<SituationCommonComponentsPageRes>>
			getComponentsPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
		return new BaseRsp<>(situationCommonComponentsService
				.getComponentsPage(situationCommonComponentsPageReq));
	}

	@ApiOperation("获取控件组件资源全量列表")
	@GetMapping("/list")
	public BaseRsp<List<SituationCommonComponentsPageRes>>
			getComponentsPages(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
		return new BaseRsp<>(situationCommonComponentsService
				.getComponentsPages(situationCommonComponentsPageReq));
	}

	@ApiOperation("获取组件详情")
	@GetMapping("/info")
	public BaseRsp<SituationCommonComponents>
			getComponentsInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationCommonComponentsService.getComponentsInfo(resourceId));
	}

	@ApiOperation("新增组件信息")
	@PostMapping("")
	public BaseRsp<String> createComponents(
			@RequestBody @Validated(Create.class) CommonComponentsReq commonComponentsReq) {
		return new BaseRsp<>(
				situationCommonComponentsService.createComponents(commonComponentsReq));
	}

	@ApiOperation("修改组件信息")
	@PatchMapping("")
	public BaseRsp<String> updateComponents(
			@RequestBody @Validated(Update.class) CommonComponentsReq commonComponentsReq) {
		return new BaseRsp<>(
				situationCommonComponentsService.updateComponents(commonComponentsReq));
	}

	@ApiOperation("批量删除组件")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteComponents(@RequestBody @Validated ColorThemeDelReq idList) {
		return new BaseRsp<>(situationCommonComponentsService.deleteComponents(idList.getIdList()));
	}

	@ApiOperation("获取组件类型列表")
	@GetMapping("/type/list")
	public BaseRsp<Map<Integer, String>> getComponentsType() {
		return new BaseRsp<>(getCommonComponentsType());
	}

	@ApiOperation("获取海康rtsp直播流")
	@GetMapping("/hik/rtsp/channels")
	public BaseRsp<JSONObject> getHikVisionRtsp(HikVisionReq hikVisionReq) {
		return new BaseRsp<>(situationCommonComponentsService.getHikVisionRtsp(hikVisionReq));
	}

	@ApiOperation("获取海康flv直播流")
	@GetMapping("/hik/flv/channels")
	public BaseRsp<JSONObject> getHikVisionFlv(@RequestParam("rtspKey") String rtspKey,
			@RequestParam("setpts") Double setpts) {
		return new BaseRsp<>(situationCommonComponentsService.getHikVisionFLv(rtspKey, setpts));
	}

	@ApiOperation("退出直播流")
	@PostMapping("/hik/flv/exit")
	public BaseRsp<JSONObject> exitHikVisionFlv(@RequestParam("rtspKey") String rtspKey) {
		return new BaseRsp<>(situationCommonComponentsService.exitHikVisionFLv(rtspKey));
	}

	@ApiOperation("附件txt上传解析内容")
	@PostMapping("/text_upload")
	public BaseRsp<String> attachmentUpload(FileCommonUploadReq fileCommonUploadReq) {

		// 获取需要校验的数据
		UploadAttachmentPermission filePermission = EnumUtils
				.getEnumByCode(fileCommonUploadReq.getType(), UploadAttachmentPermission.class);
		if (null == filePermission) {
			throw new UnauthorizedException();
		}

		// 校验权限和文件
		// filePermission.validatePermission(false);
		String fileName = fileCommonUploadReq.getFile().getOriginalFilename();
		filePermission.validateFileSuffix(fileName);

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadDest(
				AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), true, fileCommonUploadReq.getFile());

		String data = FileUtil
				.getContent(new File(utilService.getRealPath(attachmentBo.getFilePath())));

		return new BaseRsp(data);
	}
}
