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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 常见Controller
 *
 * @author SN
 * @date 2024/11/08
 */
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
		ZipUtil.unzipToDest(realPath, unzipPath);
		// 获取解压的文件
		mdFilePath = FileUtil.getOneFile(unzipPath, FileSunffixConstant.MD.getDesc());
		if (StringUtils.isBlank(mdFilePath)) {
			log.error("The md file could not be found in the archive. mdFilePath = [{}]", mdFilePath);
			throw new BusinessException(ErrorCode.CANT_FIND_MD_FILE);
		}
		// md文件内容
		File mdFile = new File(mdFilePath);
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
			ex.printStackTrace();
			throw new BusinessException("Sorry, unable to find git.properties ");
		}
		Map<String, String> gitMap = new HashMap<>();
		for (String name : properties.stringPropertyNames()) {
			gitMap.put(name, properties.getProperty(name));
		}

		return new BaseRsp<>(gitMap);
	}

}
