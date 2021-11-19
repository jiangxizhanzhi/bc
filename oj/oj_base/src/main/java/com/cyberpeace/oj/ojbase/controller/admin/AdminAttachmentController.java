package com.cyberpeace.oj.ojbase.controller.admin;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.controller.req.UploadAttachmentReq;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.dao.mapper.AttachmentMapper;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Api(tags = "oj-后台附件相关api")
@RestController
@RequestMapping("/oj/admin/attachment")
@RequiresAuthentication
@Slf4j
public class AdminAttachmentController extends PublicController<AttachmentService, Attachment> {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private AttachmentMapper attachmentMapper;

	@ApiOperation("文件资源上传")
	@PostMapping(value = "")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "文件类型 1=讲义 2=视频 3=附件"),
			@ApiImplicitParam(name = "insert", value = "是否将数据存入数据库") })
	public BaseRsp<AttachmentBo> uploadAttachment(@RequestParam("type") Integer type,
			@RequestParam("insert") Boolean insert, @RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			throw new InvalidParamException("请选择文件上传！");
		}

		String filePath = EnumUtils.getEnumDescByCode(type, Attachment.AttachmentType.class);
		if (StringUtils.isBlank(filePath)) {
			throw new InvalidParamException("不支持此类型文件上传！");
		}

		File uploadFile = new File(filePath + file.getOriginalFilename());
		if (uploadFile.exists()) {
			log.error("Upload file exist! upload_file_path=[{}]", uploadFile.getAbsolutePath());
			Attachment attachment = attachmentMapper.selectByPath(uploadFile.getAbsolutePath());
			AttachmentBo attachmentBo = BeanUtils.copy(attachment, AttachmentBo.class);
			return new BaseRsp<>(attachmentBo);
		}

		if (!uploadFile.getParentFile().exists()) {
			uploadFile.getParentFile().mkdirs();
		}

		try {
			file.transferTo(uploadFile);
		} catch (IOException e) {
			log.error("Upload file error, upload_file_path=[{}]. catch error:",
					uploadFile.getAbsolutePath(), e);
			throw new BusinessException(OjBusinessErrorCode.FILE_UPLOAD_ERROR);
		}

		if (null != insert && insert) {
			AttachmentBo attachmentBo = attachmentService.create(file.getOriginalFilename(),
					uploadFile.getAbsolutePath());
			return new BaseRsp<>(attachmentBo);
		}

		return new BaseRsp<>();

	}

}
