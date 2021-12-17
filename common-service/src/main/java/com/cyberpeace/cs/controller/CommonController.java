package com.cyberpeace.cs.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "公共服务接口")
@RestController
@RequestMapping("/common")
@RequiresAuthentication
@Slf4j
public class CommonController {

	@Autowired
	private AttachmentService attachmentService;

	@ApiOperation("图片上传")
	@PostMapping("/image_upload")
	public BaseRsp<AttachmentBo> imageUpload(MultipartFile file) {
		AttachmentBo attachmentBo = attachmentService
				.uploadFile(AttachmentPath.UPLOAD_IMAGE.getCode(), true, file, true);

		return new BaseRsp(attachmentBo);
	}

}
