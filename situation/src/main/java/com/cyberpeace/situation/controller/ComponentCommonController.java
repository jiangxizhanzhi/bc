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
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 组件管理-通用组件
 *
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

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 通用组件-分页查询
	 *
	 * @param situationCommonComponentsPageReq 通用组件列表查询请求
	 * @return {@code BaseRsp<ListRsp<SituationCommonComponentsPageRes>>}
	 */
	@ApiOperation("通用组件-列表查询")
	@GetMapping
	public BaseRsp<ListRsp<SituationCommonComponentsPageRes>>
			getComponentsPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
		return new BaseRsp<>(situationCommonComponentsService
				.getComponentsPage(situationCommonComponentsPageReq));
	}

	/**
	 * 通用组件-全量查询
	 *
	 * @param situationCommonComponentsPageReq 通用组件查询请求
	 * @return {@code BaseRsp<List<SituationCommonComponentsPageRes>>}
	 */
	@ApiOperation("通用组件-全量查询")
	@GetMapping("/list")
	public BaseRsp<List<SituationCommonComponentsPageRes>>
			getComponentsPages(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
		return new BaseRsp<>(situationCommonComponentsService
				.getComponentsPages(situationCommonComponentsPageReq));
	}

	/**
	 * 通用组件-单个查询
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SituationCommonComponents>}
	 */
	@ApiOperation("通用组件-单个查询")
	@GetMapping("/info")
	public BaseRsp<SituationCommonComponents>
			getComponentsInfo(@RequestParam("resource_id") @ApiParam(required = true,
					name = "resource_id", value = "api资源id") String resourceId) {
		return new BaseRsp<>(situationCommonComponentsService.getComponentsInfo(resourceId));
	}

	/**
	 * 通用组件-新增
	 *
	 * @param commonComponentsReq 通用组件新增请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("通用组件-新增")
	@PostMapping("")
	public BaseRsp<String> teComponents(
			@RequestBody @Validated(Create.class) CommonComponentsReq commonComponentsReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(commonComponentsReq.getName(), null);

		return new BaseRsp<>(
				situationCommonComponentsService.createComponents(commonComponentsReq));
	}

	/**
	 * 通用组件-修改
	 *
	 * @param commonComponentsReq 通用组件修改请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("通用组件-修改")
	@PatchMapping("")
	public BaseRsp<String> updateComponents(
			@RequestBody @Validated(Update.class) CommonComponentsReq commonComponentsReq) {

		// 校验字段长度是否合法
		situationHandlerService.checkFieldLen(commonComponentsReq.getName(), null);

		return new BaseRsp<>(
				situationCommonComponentsService.updateComponents(commonComponentsReq));
	}

	/**
	 * 通用组件-删除
	 *
	 * @param idList id列表
	 * @return {@code BaseRsp<Integer>}
	 */
	@ApiOperation("通用组件-批量删除")
	@DeleteMapping("")
	public BaseRsp<Integer> deleteComponents(@RequestBody @Validated ColorThemeDelReq idList) {
		return new BaseRsp<>(situationCommonComponentsService.deleteComponents(idList.getIdList()));
	}

	/**
	 * 通用组件类型-列表查询
	 *
	 * @return {@code BaseRsp<Map<Integer, String>>}
	 */
	@ApiOperation("通用组件类型-列表查询")
	@GetMapping("/type/list")
	public BaseRsp<Map<Integer, String>> getComponentsType() {
		return new BaseRsp<>(getCommonComponentsType());
	}

	/**
	 * 海康摄像头-获取RTSP直播流
	 *
	 * @param hikVisionReq 海康摄像头请求
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("海康摄像头-获取RTSP直播流")
	@GetMapping("/hik/rtsp/channels")
	public BaseRsp<JSONObject> getHikVisionRtsp(HikVisionReq hikVisionReq) {
		return new BaseRsp<>(situationCommonComponentsService.getHikVisionRtsp(hikVisionReq));
	}

	/**
	 * 海康摄像头-获取FLV直播流
	 *
	 * @param rtspKey rtsp识别码
	 * @param setpts  倍率
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("海康摄像头-获取FLV直播流")
	@GetMapping("/hik/flv/channels")
	public BaseRsp<JSONObject> getHikVisionFlv(@RequestParam("rtspKey") String rtspKey,
			@RequestParam("setpts") Double setpts) {
		return new BaseRsp<>(situationCommonComponentsService.getHikVisionFLv(rtspKey, setpts));
	}

	/**
	 * 海康摄像头-退出直播流
	 *
	 * @param rtspKey rtsp识别码
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("海康摄像头-退出直播流")
	@PostMapping("/hik/flv/exit")
	public BaseRsp<JSONObject> exitHikVisionFlv(@RequestParam("rtspKey") String rtspKey) {
		return new BaseRsp<>(situationCommonComponentsService.exitHikVisionFLv(rtspKey));
	}

	/**
	 * 文本组件-解析TXT内容
	 *
	 * @param fileCommonUploadReq TXT文件请求
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("文本组件-解析TXT内容")
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
