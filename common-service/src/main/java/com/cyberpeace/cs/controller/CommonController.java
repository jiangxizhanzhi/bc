package com.cyberpeace.cs.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ExtractBase;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.constant.UploadImagePermission;
import com.cyberpeace.cs.controller.req.AdMdUploadReq;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 常见Controller
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 1. **类概述** 
 * - 类名为 `CommonController`，位于包 `com.cyberpeace.cs.controller` 下。 
 * - 注解 `@RestController` 表明这是一个 RESTful 接口控制器。 
 * - 注解 `@RequestMapping("/common")` 指定该控制器的基础路径为 `/common`。 
 * - 注解 `@RequiresAuthentication` 表示该接口需要用户认证。 
 * #### 2. **主要功能** 
 * 该类提供了多个用于文件上传、解析及版本信息查询的功能接口。以下是主要功能模块： 
 * ##### a. **图片上传** 
 * - 方法：`imageUpload` 
 * - 功能：处理图片文件上传。 
 * - 流程： 
 * - 校验文件类型和后缀。 
 * - 校验文件名长度（最大100字符）。 
 * - 调用 `attachmentService.uploadFile` 进行文件上传。 
 * - 返回包含文件路径的 `BaseRsp` 对象。 
 * ##### b. **附件上传** 
 * - 方法：`attachmentUpload` 
 * - 功能：处理普通附件上传。 
 * - 流程： 
 * - 校验文件名长度。 
 * - 调用 `attachmentService.uploadDest` 进行文件上传。 
 * - 返回包含文件路径的 `BaseRsp` 对象。 
 * ##### c. **Markdown 文件上传** 
 * - 方法：`mdUpload` 
 * - 功能：处理 Markdown 文件上传。 
 * - 流程： 
 * - 校验文件名长度。 
 * - 调用 `attachmentService.uploadDest` 进行文件上传。 
 * - 返回包含文件路径的 `BaseRsp` 对象。 
 * ##### d. **Markdown 文件解析** 
 * - 方法：`parseMDFile` 
 * - 功能：解析上传的 Markdown 文件。 
 * - 流程： 
 * - 校验文件类型是否为 ZIP。 
 * - 校验文件名长度。 
 * - 将文件上传至服务器并解压。 
 * - 查找解压后的 Markdown 文件。 
 * - 校验 Markdown 文件大小。 
 * - 使用工具解析 Markdown 文件内容并返回结果。 
 * ##### e. **验证文件名长度** 
 * - 方法：`validateFileNameLen` 
 * - 功能：校验文件名长度是否超过 100 字符。 
 * ##### f. **获取 Git 版本信息** 
 * - 方法：`getGitVersion` 
 * - 功能：读取 `git.properties` 文件中的版本信息。 
 * - 流程： 
 * - 从类加载器中加载 `git.properties` 文件。 
 * - 将文件内容加载到 `Properties` 对象中。 
 * - 返回包含版本信息的 `BaseRsp` 对象。 
 * #### 3. **依赖服务** 
 * - `AttachmentService`：用于文件上传操作。 
 * - `UtilService`：提供文件路径处理等辅助功能。 
 * - `EnumUtils`：用于枚举类型的转换。 
 * - `FileUtil`、`ZipUtil`、`MdUtil`：分别用于文件、ZIP 文件和 Markdown 文件的操作。 
 * #### 4. **注解与框架** 
 * - 使用了 Spring Boot 的注解，如 `@RestController`、`@RequestMapping`、`@PostMapping`、`@GetMapping`。 
 * - 使用了 Swagger 注解 `@Api` 和 `@ApiOperation` 来描述接口功能。 
 * - 使用了 Lombok 的 `@Slf4j` 提供日志记录功能。 
 * #### 5. **异常处理** 
 * - 在多个地方抛出 `BusinessException` 或 `UnauthorizedException`，用于处理业务逻辑错误。 
 * #### 6. **初始化** 
 * - 使用 `@PostConstruct` 注解的 `init` 方法初始化常量 `MD_UPLOAD_NAME_LEN`。 
 * #### 7. **总结** 
 * 该类是一个典型的文件管理控制器，提供了多种文件上传、解析和版本信息查询的功能。代码结构清晰，功能模块化，使用了现代 Java 技术栈（Spring Boot、Lombok、Swagger）。 
**/
@Api(tags = "公共服务接口")
@RestController
@RequestMapping("/common")
@RequiresAuthentication
@Slf4j
public class CommonController {

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

	/**
	 * Markdown上传名称莱恩
	 */
	private String MD_UPLOAD_NAME_LEN;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		MD_UPLOAD_NAME_LEN = "100";
	}

	/**
	 * 图片上传
	 *
	 * @param fileCommonUploadReq 文件常见上传请求体
	 * @return {@code BaseRsp<AttachmentBo> }
	 */
	@ApiOperation("图片上传")
	@PostMapping("/image_upload")
	public BaseRsp<AttachmentBo> imageUpload(FileCommonUploadReq fileCommonUploadReq) {
		String fileName = fileCommonUploadReq.getFile().getOriginalFilename();
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
			filePermission.validateFileSuffix(fileName);

		} else {
			FileUtil.validateFileSuffix(fileName, FileTypeContant.PICTURE_TYPE);
		}

		validateFileNameLen(fileName);

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadFile(
				AttachmentPath.UPLOAD_IMAGE.getCode(), true, fileCommonUploadReq.getFile(), true);

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp(attachmentBo);
	}

	/**
	 * 附件上传
	 *
	 * @param fileCommonUploadReq 文件常见上传请求体
	 * @return {@code BaseRsp<AttachmentBo> }
	 */
	@ApiOperation("附件上传")
	@PostMapping("/attachment_upload")
	public BaseRsp<AttachmentBo> attachmentUpload(FileCommonUploadReq fileCommonUploadReq) {

		// 获取需要校验的数据
//		UploadAttachmentPermission filePermission = EnumUtils
//				.getEnumByCode(fileCommonUploadReq.getType(), UploadAttachmentPermission.class);
//		if (null == filePermission) {
//			log.error("Type not found! type=[{}]", fileCommonUploadReq.getType());
//			throw new UnauthorizedException();
//		}

		// 校验权限和文件
//		filePermission.validatePermission(false);
		String fileName = fileCommonUploadReq.getFile().getOriginalFilename();
//		filePermission.validateFileSuffix(fileName);

		validateFileNameLen(fileName);
		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadDest(
				AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), true, fileCommonUploadReq.getFile());

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp(attachmentBo);
	}

	/**
	 * 竞赛md统一上传接口
	 * 去掉权限校验
	 *
	 * @param adMdUploadReq 文件常见上传请求体
	 * @return {@code BaseRsp<AttachmentBo>}
	 */
	@ApiOperation("竞赛md统一上传接口")
	@PostMapping("/ad_upload")
	public BaseRsp<AttachmentBo> mdUpload(AdMdUploadReq adMdUploadReq) {

		// 校验权限和文件
		String fileName = adMdUploadReq.getFile().getOriginalFilename();

		validateFileNameLen(fileName);
		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadDest(
				AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), true, adMdUploadReq.getFile());

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp(attachmentBo);
	}

	/**
	 * 解析 mdfile
	 *
	 * @param md Markdown
	 * @return {@code BaseRsp<ExtractBase> }
	 */
	@ApiOperation("解析md压缩包")
	@PostMapping("/parse/md")
	public BaseRsp<ExtractBase> parseMDFile(@RequestParam("md") MultipartFile md){
		String realContent = null;
		String suffix = FileUtil.getFileSuffix(md.getOriginalFilename());
		suffix = suffix.toLowerCase();
		if (!suffix.equals(FileSunffixConstant.ZIP.getDesc()) ){
			throw new BusinessException(ErrorCode.NOT_SUPPORT_FILE_TYPE, FileSunffixConstant.ZIP.getDesc());
		}

		validateFileNameLen(md.getOriginalFilename());

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
		Boolean b = ZipUtil.unzipToDest(realPath, unzipPath);
		// 获取解压的文件
		mdFilePath = FileUtil.getOneFile(unzipPath, FileSunffixConstant.MD.getDesc());
		if (StringUtils.isBlank(mdFilePath)) {
			log.error("The md file could not be found in the archive. mdFilePath = [{}]", mdFilePath);
			throw new BusinessException(ErrorCode.CANT_FIND_MD_FILE);
		}
		// md文件内容
		// 安全验证路径
		Path safeBasePath = Paths.get(unzipPath).normalize().toAbsolutePath();
		Path resolvedPath = safeBasePath.resolve(mdFilePath).normalize();
		// 验证解析后的路径是否仍在安全目录内
		if (!resolvedPath.startsWith(safeBasePath)) {
			log.error("潜在路径遍历攻击: 原始路径={}, 尝试访问={}", unzipPath, mdFilePath);
			throw new BusinessException(ErrorCode.INVALID_FILE_PATH);
		}
		// 使用安全路径访问文件
		File mdFile = resolvedPath.toFile();
		if (mdFile.length() > Long.parseLong(ConfigUtil.getContextProperty("md.max.size"))) {
			log.error("The markdown file in the archive cannot be larger than [{}] m! mdFile.length = [{}]",ConfigUtil.getContextProperty("md.max.size"), mdFile.length());
			throw new BusinessException(ErrorCode.MD_SIZE_TOO_LARGE, Long.parseLong(ConfigUtil.getContextProperty("md.max.size"))/1048576);
		}
		String content = MdUtil.parseMarkdown(mdFilePath);
		realContent = utilService.replaceMarkdownFile(content);
		extractBase.setContent(realContent);
		extractBase.setIsExtract(Boolean.TRUE);
		return new BaseRsp<>(extractBase);
	}

	/**
	 * 验证文件名称莱恩
	 *
	 * @param fileName 文件名称
	 */
	private void validateFileNameLen(String fileName) {
		int fileNameLen = fileName == null ? 0 : fileName.length();
		if (fileNameLen > 100) {
			log.error("upload fileName length cannot be larger than [{}] ! name.length = [{}]",
					MD_UPLOAD_NAME_LEN, fileNameLen);
			throw new BusinessException(ErrorCode.FILE_NAME_UPPER_LIMIT,
					Integer.parseInt(MD_UPLOAD_NAME_LEN));
		}
	}

	/**
	 * 获取git版本
	 *
	 * @return {@code BaseRsp<Map<String, String>> }
	 */
	@ApiOperation("获取git版本信息")
	@GetMapping("/version")
	public BaseRsp<Map<String, String>> getGitVersion()  {

		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("git.properties")) {
			if (input == null) {
				throw new BusinessException("Sorry, unable to find git.properties, input is null");
			}
			properties.load(input);
		} catch (IOException ex) {
			log.error("读取git.properties文件失败", ex);
			throw new BusinessException("Sorry, unable to find git.properties ");
		}
		Map<String, String> gitMap = new HashMap<>();
		for (String name : properties.stringPropertyNames()) {
			gitMap.put(name, properties.getProperty(name));
		}

		return new BaseRsp<>(gitMap);
	}

}

