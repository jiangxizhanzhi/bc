package com.cyberpeace.cs.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ExtractBase;
import com.cyberpeace.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.constant.UploadAttachmentPermission;
import com.cyberpeace.cs.constant.UploadImagePermission;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

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

		// 校验权限和文件
		filePermission.validatePermission(false);
		String fileName = fileCommonUploadReq.getFile().getOriginalFilename();
		filePermission.validateFileSuffix(fileName);

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadDest(
				AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), true, fileCommonUploadReq.getFile());

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp(attachmentBo);
	}

	@ApiOperation("解析md压缩包")
	@PostMapping("/parse/md")
	public BaseRsp<ExtractBase> parseMDFile(@RequestParam("md") MultipartFile md){
		String realContent = null;
		String suffix = FileUtil.getFileSuffix(md.getOriginalFilename());
		suffix = suffix.toLowerCase();
		if (!suffix.equals(FileSunffixConstant.ZIP.getDesc()) ){
			throw new BusinessException(ErrorCode.NOT_SUPPORT_FILE_TYPE, FileSunffixConstant.ZIP.getDesc());
		}
		// 文件上传至服务器 原始文件地址存储
		String markdownPath = AttachmentPath.UPLOAD_ATTACHMENT.getDesc();
		AttachmentBo attachmentBo = attachmentService.uploadDest(markdownPath, false, md);
		ExtractBase extractBase = BeanUtils.copy(attachmentBo, ExtractBase.class);
		// 获取md文件存储地址
		String mdPath = AttachmentPath.UPLOAD_MARKDOWN.getDesc();
		String realPath = utilService.getRealPath(attachmentBo.getFilePath());
		// 计算要解压的文件地址
		String unzipPath = mdPath + FileUtil.getFileName(realPath, false);
		String mdFilePath = null;
		ZipUtil.unzipToDest(realPath, unzipPath);
		// 获取解压的文件
		mdFilePath = FileUtil.getOneFile(unzipPath, FileSunffixConstant.MD.getDesc());
		if (StringUtils.isBlank(mdFilePath)) {
			throw new BusinessException(ErrorCode.CANT_FIND_MD_FILE);
		}
		// md文件内容
		File mdFile = new File(mdFilePath);
		if (mdFile.length() > Long.parseLong(ConfigUtil.getContextProperty("md.max.size"))) {
			throw new BusinessException(ErrorCode.MD_SIZE_TOO_LARGE, Long.parseLong(ConfigUtil.getContextProperty("md.max.size"))/1048576);
		}
		String content = MdUtil.parseMarkdown(mdFilePath);
		realContent = utilService.replaceMarkdownFile(content);
		extractBase.setContent(realContent);
		extractBase.setIsExtract(Boolean.TRUE);
		return new BaseRsp<>(extractBase);
	}

}
