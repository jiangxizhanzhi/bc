package com.cyberpeace.oj.ojbase.controller.admin;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;
import io.swagger.annotations.ApiModel;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.controller.res.LogoUploadRes;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Api(tags = "oj-后台附件相关api")
@RestController
@RequestMapping("/oj/admin/attachment")
@RequiresAuthentication
public class AdminAttachmentController {

	@Autowired
	private AttachmentService attachmentService;

	@ApiOperation("logo文件上传")
	@PostMapping("/image_upload")
	public BaseRsp<LogoUploadRes> uploadAttachment(@RequestParam("file") MultipartFile file) {
		AttachmentBo attachmentBo = attachmentService
				.uploadFile(Attachment.AttachmentType.OTHER.getCode(), CommonConstant.TRUE, file);
		return new BaseRsp<>(new LogoUploadRes(attachmentBo.getFilePath()));
	}

	@ApiOperation("附件公开与隐藏")
	@PatchMapping("/batch-public")
	public BaseRsp<Boolean> batchPublic(@RequestBody @Validated ResourcePublicReq publicReq) {

		List<Attachment> attachments = StreamApiUtils.map(publicReq.getResourceIds(),
				resourceId -> {
					Attachment build = Attachment.builder().isPublic(publicReq.getIsPublic())
							.resourceId(resourceId).build();
					return build;
				});

		boolean result = attachmentService.updateBatchById(attachments);
		return new BaseRsp<>(result);
	}

}
