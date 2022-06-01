package com.cyberpeace.cs.controller;

import java.util.List;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cs.constant.*;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.controller.rsp.PageItemRsp;
import com.cyberpeace.cs.dao.entity.PageConfigItem;
import com.cyberpeace.cs.dao.mapper.PageConfigItemMapper;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
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

	@Autowired
	private UtilService utilService;

	@Autowired
	private PageConfigItemMapper pageConfigItemMapper;

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
		AttachmentBo attachmentBo = attachmentService.uploadFile(
				AttachmentPath.UPLOAD_ATTACHMENT.getCode(), true, fileCommonUploadReq.getFile(),
				true);

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp(attachmentBo);
	}

	@ApiOperation("获取前端页面配置项")
	@GetMapping("/page/config/item")
	public BaseRsp<List<PageItemRsp>> getPageItem(@RequestParam("type") Integer type) {

		// 获取对应的枚举类型 进行权限校验
		PageItemType enumByCode = EnumUtils.getEnumByCode(type, PageItemType.class);
		if (null == enumByCode) {
			throw new UnauthorizedException();
		}

		// 权限校验
		boolean permission = enumByCode.validatePermission();
		if (!permission) {
			throw new UnauthorizedException();
		}

		// 数据查询
		List<PageConfigItem> items = pageConfigItemMapper.selectByType(type);
		List<PageItemRsp> rsps = BeanUtils.deepCopyList(items, PageItemRsp.class);
		return new BaseRsp<>(rsps);
	}

}
