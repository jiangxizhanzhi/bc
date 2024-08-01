package com.cyberpeace.cs.controller;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ExtractBase;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.constant.FileTypeContant;
import com.cyberpeace.cs.constant.UploadAttachmentPermission;
import com.cyberpeace.cs.constant.UploadImagePermission;
import com.cyberpeace.cs.controller.req.AdMdUploadReq;
import com.cyberpeace.cs.controller.req.FileCommonUploadReq;
import com.cyberpeace.cs.controller.res.CodeNameRsp;
import com.cyberpeace.cs.dao.entity.ConstantsConfig;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.ConstantsService;
import com.cyberpeace.cs.service.DictInfoService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "公共服务接口")
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private ConstantsService constantsService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private DictInfoService dictInfoService;

	private String MD_UPLOAD_NAME_LEN;

	@PostConstruct
	public void init() {
		MD_UPLOAD_NAME_LEN = ConfigUtil.getContextProperty("md.upload.name.len.max");
	}

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
//			filePermission.validatePermission(false);
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

		return new BaseRsp<>(attachmentBo);
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
//		filePermission.validatePermission(false);
		String fileName = fileCommonUploadReq.getFile().getOriginalFilename();
		filePermission.validateFileSuffix(fileName);

		validateFileNameLen(fileName);
		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadDest(
				AttachmentPath.UPLOAD_ATTACHMENT.getDesc(), true, fileCommonUploadReq.getFile());

		String viewPath = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewPath);

		return new BaseRsp<>(attachmentBo);
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

		return new BaseRsp<>(attachmentBo);
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
		long size = Long.parseLong(ConfigUtil.getContextProperty("md.max.size"));
		if (mdFile.length() > size) {
			log.error(
					"The markdown file in the archive cannot be larger than [{}] m! mdFile.length = [{}]",
					size, mdFile.length());
			throw new BusinessException(ErrorCode.MD_SIZE_TOO_LARGE, size / 1048576);
		}
		String content = MdUtil.parseMarkdown(mdFilePath);
		realContent = utilService.replaceMarkdownFile(content);
		extractBase.setContent(realContent);
		extractBase.setIsExtract(Boolean.TRUE);
		return new BaseRsp<>(extractBase);
	}

	@ApiOperation("获取常量配置")
	@GetMapping("/constants_config")
	public BaseRsp<Object> getConstantsConfig(@ApiParam(name = "type", value = "类型") Integer type) {
		List<ConstantsConfig> constants = constantsService.getByType(type);
		if (!dictInfoService.isOperationOpen()) {
			if (ConstantsConfig.ConstantsType.COLLECTION_TYPE.getCode().equals(type)) {
				constants = constants.stream().filter(c -> c.getCode() != 2)
						.collect(Collectors.toList());
			}
			if (type == 40) {
				constants = constants.stream().filter(c -> c.getCode() != 3)
						.collect(Collectors.toList());
			}
		}
		List<CodeNameRsp> rsp = StreamApiUtils.map(constants,
				constant -> new CodeNameRsp(constant.getCode(), constant.selectI18nName()));
		return new BaseRsp<>(rsp);
	}

	private void validateFileNameLen(String fileName) {
		int fileNameLen = fileName == null ? 0 : fileName.length();
		if (fileNameLen > Integer.parseInt(MD_UPLOAD_NAME_LEN)) {
			log.error("upload fileName length cannot be larger than [{}] ! name.length = [{}]",
					MD_UPLOAD_NAME_LEN, fileNameLen);
			throw new BusinessException(ErrorCode.FILE_NAME_UPPER_LIMIT,
					Integer.parseInt(MD_UPLOAD_NAME_LEN));
		}
	}

}
