package com.cyberpeace.oj.ojbase.controller.admin;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.oj.ojbase.controller.res.LogoUploadRes;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "oj-后台附件相关api")
@RestController
@RequestMapping("/oj/admin/attachment")
@RequiresAuthentication
public class AdminAttachmentController extends PublicController<AttachmentService, Attachment> {

	@Autowired
	private AttachmentService attachmentService;

	@ApiOperation("logo文件上传")
	@PostMapping("/image_upload")
	public BaseRsp<LogoUploadRes> uploadAttachment(@RequestParam("file") MultipartFile file) {
		AttachmentBo attachmentBo = attachmentService
				.uploadFile(Attachment.AttachmentType.OTHER.getCode(), CommonConstant.TRUE, file);
		return new BaseRsp<>(new LogoUploadRes(attachmentBo.getFilePath()));
	}

}
