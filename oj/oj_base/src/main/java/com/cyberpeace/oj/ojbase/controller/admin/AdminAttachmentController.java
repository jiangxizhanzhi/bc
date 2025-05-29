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
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminAttachmentController` 的 Spring MVC 控制器类，用于处理与管理附件相关的 API 请求。以下是代码的主要组成部分和功能的简要分析： 
 * ### 类和注解 
 * - **类名**: `AdminAttachmentController` 
 * - **包名**: `com.cyberpeace.oj.ojbase.controller.admin` 
 * - **注解**: 
 * - `@Slf4j`: 通过 Lombok 自动生成日志记录器。 
 * - `@RestController`: 标识这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/oj/admin/attachment")`: 设置该控制器处理的所有请求的基础路径。 
 * - `@RequiresAuthentication`: 需要认证才能访问这些 API。 
 * ### 成员变量 
 * - **成员变量**: 
 * - `private AttachmentService attachmentService;`: 用于处理附件业务逻辑的服务。 
 * - `private UtilService utilService;`: 提供一些实用工具方法的服务。 
 * ### 方法 
 * 1. **上传附件** 
 * - **方法名**: `uploadAttachment` 
 * - **HTTP 方法**: `POST` 
 * - **路径**: `/oj/admin/attachment/image_upload` 
 * - **功能**: 接收一个文件并将其上传到指定路径，返回一个包含视图路径的响应。 
 * - **参数**: `@RequestParam("file") MultipartFile file`：上传的文件。 
 * - **返回值**: `BaseRsp<LogoUploadRes>`: 包含视图路径的响应。 
 * 2. **批量公共** 
 * - **方法名**: `batchPublic` 
 * - **HTTP 方法**: `PATCH` 
 * - **路径**: `/oj/admin/attachment/batch-public` 
 * - **功能**: 批量设置附件的公开状态。 
 * - **参数**: `@RequestBody @Validated ResourcePublicReq publicReq`：包含资源 ID 和公开状态的请求体。 
 * - **返回值**: `BaseRsp<Boolean>`: 包含操作结果的响应。 
 * ### 主要功能 
 * - **上传附件**: 上传一个文件，并返回其在系统中的视图路径。 
 * - **批量设置公开状态**: 批量更新多个附件的公开状态。 
 * ### 技术细节 
 * - 使用了 Lombok 的 `@Slf4j` 注解来简化日志记录。 
 * - 使用了 Spring 的 `@Autowired` 注解来自动注入依赖服务。 
 * - 使用了 Swagger 的 `@Api` 和 `@ApiOperation` 注解来描述 API 文档。 
 * - 使用了 Java 8 的 Stream API 来处理集合操作。 
 * ### 总结 
 * 这个控制器主要用于管理附件的上传和状态更新，提供了两个主要的功能：上传单个附件和批量更新附件的公开状态。整个代码结构清晰，使用了现代 Java 技术和框架特性来实现功能。 
**/
@Slf4j
@Api(tags = "oj-后台附件相关api")
@RestController
@RequestMapping("/oj/admin/attachment")
@RequiresAuthentication
public class AdminAttachmentController {

	/**
	 * 附件Service
	 *
	 * @see AttachmentService
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * utilService
	 *
	 * @see UtilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 上传附件
	 *
	 * @param file 文件
	 * @return {@code BaseRsp<LogoUploadRes> }
	 * @throws
	 * @see MultipartFile
	 * @see BaseRsp<LogoUploadRes>
	 */
	@ApiOperation("logo文件上传")
	@PostMapping("/image_upload")
	public BaseRsp<LogoUploadRes> uploadAttachment(@RequestParam("file") MultipartFile file) {
		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.LOGO.getCode(),
				CommonConstant.FALSE, file, true);

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		return new BaseRsp<>(new LogoUploadRes(viewPath));
	}

	/**
	 * 批量公共
	 *
	 * @param publicReq 公共请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ResourcePublicReq
	 * @see BaseRsp<Boolean>
	 */
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

