package com.cyberpeace.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.gateway.controller.req.*;
import com.cyberpeace.gateway.controller.res.ControlRes;
import com.cyberpeace.gateway.controller.res.ScreenPathRes;
import com.cyberpeace.integratation.service.bo.situation.BooleanRes;
import com.cyberpeace.situation.constant.PublicStatus;
import com.cyberpeace.situation.constant.SituationInstanceStatus;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.dao.mapper.SituationPublishInstanceMapper;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-态势相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/situation")
public class ExternalSituationController {

	@Autowired
	private SituationPublishInstanceMapper situationPublishInstanceMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private DashboardTemplateService dashboardTemplateService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@CrossOrigin("*")
	@ApiOperation("接收任务指挥命令")
	@PostMapping("control")
	public BaseRsp<ControlRes>
			controlCmd(@RequestBody @Validated ControlCommandReq controlCommandReq) {

		log.info(String.format("成功接收指令：%s", JsonUtil.toJsonString(controlCommandReq)));

		return new BaseRsp<>(new ControlRes("1", "0"));
	}

	@CrossOrigin("*")
	@ApiOperation("接收可视化方案的新建、编辑")
	@PostMapping("/scheme")
	public BaseRsp<ScreenPathRes>
			receiveScheme(@RequestBody @Validated SituationSchemeBo schemeBo) {

		SituationPublishInstanceAddReq situationPublishInstanceAddReq = BeanUtils.copy(schemeBo,
				SituationPublishInstanceAddReq.class);

		SituationPublishInstance situationPublishInstance = situationPublishInstanceService
				.insertOne(situationPublishInstanceAddReq);

		log.info("成功接收可视化方案：{}", JsonUtil.toJsonString(schemeBo));

		ScreenPathRes res = new ScreenPathRes();

		res.setPath("/cr/admin/situation-release/edit/"
				+ situationPublishInstance.getResourceId());
		return new BaseRsp<>(res);
	}

	@CrossOrigin("*")
	@ApiOperation("接收想定下发大屏指令")
	@PostMapping("/emission")
	public BaseRsp<BooleanRes> receiveEmission(@RequestBody @Validated EmissionReq req) {
		log.info("成功接收指令：{}", JsonUtil.toJsonString(req));
		BooleanRes res = new BooleanRes();
		res.setResult(true);
		return new BaseRsp<>(res);
	}

	@CrossOrigin("*")
	@ApiOperation("接收节点迁移信息")
	@PostMapping("/migration/")
	public BaseRsp<BooleanRes> receiveMigration(@RequestBody @Validated NodeMigrationReq req) {

		BooleanRes res = new BooleanRes();
		res.setResult(true);

		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
				.selectPublicedBySubjectId(req.getTaskId(), req.getSubjectId());

		if (situationPublishInstance == null) {
			return new BaseRsp<>(res);
		}

		WsMessage<NodeMigrationReq> wsMessage = new WsMessage<>(MessageType.NodeMigration.getDesc(),
				req);
		situationHandlerService.pushDataToDashboard(wsMessage,
				situationPublishInstance.getResourceId());

		return new BaseRsp<>(res);
	}

	@CrossOrigin("*")
	@ApiOperation("接收第三方大屏绑定")
	@PostMapping("/third_dashboard/")
	public BaseRsp<BooleanRes> bindThirdDashboard(ThirdDashboardReq thirdDashboardReq) {

		// 上传图片文件
		FileUtil.validateFileSuffix(thirdDashboardReq.getThumbnail().getOriginalFilename(),
				FileTypeContant.PICTURE_TYPE);
		AttachmentBo attachmentBo = attachmentService.uploadFile(
				AttachmentPath.UPLOAD_IMAGE.getCode(), true, thirdDashboardReq.getThumbnail(),
				true);
		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());

		// 创建大屏实例
		SituationPublishInstance situationPublishInstance = BeanUtils.copy(thirdDashboardReq,
				SituationPublishInstance.class);
		situationPublishInstance.setThumbnail(viewPath);

		// 构造基本信息
		situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());
		situationPublishInstance.setPublicStatus(PublicStatus.OFF.getCode());
		situationPublishInstance.setEdit(false);
		situationPublishInstance.setCategory("26c4cf546d1f4557ac29f9fa8e8a6c3a");
		situationPublishInstanceMapper.insert(situationPublishInstance);
		BooleanRes res = new BooleanRes();
		res.setResult(true);
		return new BaseRsp<>(res);
	}
}
