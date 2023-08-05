package com.cyberpeace.cs.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.constant.UploadAttachmentPermission;
import com.cyberpeace.cs.constant.UploadImagePermission;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.service.impl.RemoteServerEnvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "公共服务接口")
@RestController
@RequestMapping("/common")
@RequiresAuthentication
@Slf4j
public class CommonController {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private RemoteServerEnvService remoteServerEnvService;

	@ApiOperation("图片上传")
	@PostMapping("/image_upload")
	public BaseRsp<AttachmentBo> imageUpload(FileCommonUploadReq fileCommonUploadReq) {

		if (null != fileCommonUploadReq.getType()) {
			// 获取需要校验的数据
			UploadImagePermission filePermission = EnumUtils
					.getEnumByCode(fileCommonUploadReq.getType(), UploadImagePermission.class);
			if (null == filePermission) {
				log.error("Type not found! type=[{}]", fileCommonUploadReq.getType());
				throw new UnauthorizedException();
			}

			// 校验权限和文件
			filePermission.validatePermission(false);
			String fileName = fileCommonUploadReq.getFile().getOriginalFilename();
			filePermission.validateFileSuffix(fileName);
		} else {
			FileUtil.validateFileSuffix(fileCommonUploadReq.getFile().getOriginalFilename(),
					FileTypeContant.PICTURE_TYPE);
		}

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadFile(
				AttachmentPath.UPLOAD_IMAGE.getCode(), true, fileCommonUploadReq.getFile(), true);

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp(attachmentBo);
	}

	@ApiOperation("附件上传")
	@PostMapping("/attachment_upload")
	public BaseRsp<AttachmentBo> attachmentUpload(FileCommonUploadReq fileCommonUploadReq) {

		// 获取需要校验的数据
		UploadAttachmentPermission filePermission = EnumUtils
				.getEnumByCode(fileCommonUploadReq.getType(), UploadAttachmentPermission.class);
		if (null == filePermission) {
			log.error("Type not found! type=[{}]", fileCommonUploadReq.getType());
			throw new UnauthorizedException();
		}

		// 判断Office服务是否正常
		Boolean checkOffice = remoteServerEnvService.checkOffice();

		// 校验权限和文件
		filePermission.validatePermission(false);
		String fileName = fileCommonUploadReq.getFile().getOriginalFilename();
		filePermission.validateFileSuffix(fileName, checkOffice);

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadDest(
				AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), true, fileCommonUploadReq.getFile());

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp(attachmentBo);
	}

}
