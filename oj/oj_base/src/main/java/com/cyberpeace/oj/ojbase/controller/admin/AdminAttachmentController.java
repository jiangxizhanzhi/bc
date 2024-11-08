package com.cyberpeace.oj.ojbase.controller.admin;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.ojbase.controller.res.LogoUploadRes;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 管理附件Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Api(tags = "oj-后台附件相关api")
@RestController
@RequestMapping("/oj/admin/attachment")
@RequiresAuthentication
public class AdminAttachmentController {

	/**
	 * 附件Service
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	@ApiOperation("logo文件上传")
	@PostMapping("/image_upload")
	public BaseRsp<LogoUploadRes> uploadAttachment(@RequestParam("file") MultipartFile file) {
		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.LOGO.getCode(),
				CommonConstant.FALSE, file, true);

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		return new BaseRsp<>(new LogoUploadRes(viewPath));
	}

	@ApiOperation("附件公开与隐藏")
	@PatchMapping("/batch-public")
	public BaseRsp<Boolean> batchPublic(@RequestBody @Validated ResourcePublicReq publicReq) {

		List<Attachment> attachments = StreamApiUtils.map(publicReq.getResourceIds(),
				resourceId -> Attachment.builder().isPublic(publicReq.getIsPublic())
						.resourceId(resourceId).build());

		boolean result = attachmentService.updateBatchById(attachments);
		return new BaseRsp<>(result);
	}

}
