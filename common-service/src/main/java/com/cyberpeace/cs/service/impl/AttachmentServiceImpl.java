package com.cyberpeace.cs.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	public List<AttachmentBo> selectByIds(String attachmentIds, Boolean isWeb) {
		if (StringUtils.isNotBlank(attachmentIds)) {
			String[] attachmentArr = StringUtils.split(attachmentIds, CommonConstant.CODE_COMMA);

			List<Attachment> attachments = attachmentMapper
					.selectBatchIds(Lists.newArrayList(attachmentArr), isWeb);
			List<AttachmentBo> attachmentBos = StreamApiUtils.map(attachments,
					attachment -> BeanUtils.copy(attachment, AttachmentBo.class));
			return attachmentBos;
		}
		return Lists.newArrayList();
	}

	@Override
	public Integer deleteDifferenceSet(String oldAttachmentIds, String attachmentIds) {
		if (StringUtils.isBlank(oldAttachmentIds)) {
			return CommonConstant.ZERO;
		}
		List<Serializable> oldIdList = Arrays.asList(StringUtils.split(oldAttachmentIds),
				CommonConstant.CODE_COMMA);

		if (StringUtils.isNotBlank(attachmentIds)) {
			List<String> newIds = Arrays
					.asList(StringUtils.split(attachmentIds, CommonConstant.CODE_COMMA));
			oldIdList.removeAll(newIds);
		}

		if (CollectionUtils.isNotEmpty(oldIdList)) {
			int row = attachmentMapper.deleteBatchIds(oldIdList);
			return row;
		}
		return CommonConstant.ZERO;
	}

	@Override
	public AttachmentBo create(String filename, String filePath) {
		Attachment attachment = Attachment.builder().resourceId(IDGeneratorUtil.getID())
				.fileName(filename).filePath(filePath).isPublic(CommonConstant.TRUE).build();
		int insert = attachmentMapper.insert(attachment);
		if (insert > 0) {
			return BeanUtils.copy(attachment, AttachmentBo.class);
		}
		return null;
	}

	@Override
	public AttachmentBo uploadFile(Integer type, Boolean insert, MultipartFile file) {

		String filePath = EnumUtils.getEnumDescByCode(type, AttachmentPath.class);
		if (StringUtils.isBlank(filePath)) {
			throw new InvalidParamException("不支持此类型文件上传！");
		}

		return uploadFile(filePath, file, insert);
	}

	@Override
	public AttachmentBo uploadFile(Integer type, Boolean insert, MultipartFile file,
			String resourceId) {

		String filePath = EnumUtils.getEnumDescByCode(type, AttachmentPath.class);

		if (StringUtils.isBlank(filePath)) {
			throw new InvalidParamException("不支持此类型文件上传！");
		}

		String finalPath = String.format(filePath, resourceId);
		return uploadFile(finalPath, file, insert);
	}

	/**
	 * 文件上传操作
	 * @param filePath 要上传的文件目录
	 * @param file 上传的文件
	 * @param insert 
	 * @return
	 */
	private AttachmentBo uploadFile(String filePath, MultipartFile file, Boolean insert) {

		if (file.isEmpty()) {
			throw new InvalidParamException("请选择文件上传！");
		}

		File uploadFile = new File(filePath + file.getOriginalFilename());

		if (uploadFile.exists()) {
			uploadFile = new File(filePath + reFileName(file.getOriginalFilename()));
		}

		if (!uploadFile.getParentFile().exists()) {
			uploadFile.getParentFile().mkdirs();
		}

		try {
			file.transferTo(uploadFile);
		} catch (IOException e) {
			log.error("Upload file error, upload_file_path=[{}]. catch error:",
					uploadFile.getAbsolutePath(), e);
			throw new InvalidParamException("文件上传失败！");
		}

		if (null != insert && insert) {
			AttachmentBo attachmentBo = create(uploadFile.getName(), uploadFile.getAbsolutePath());
			return attachmentBo;
		}

		return AttachmentBo.builder().filePath(uploadFile.getAbsolutePath())
				.fileName(uploadFile.getName()).build();
	}

	/**
	 * 修改文件名称
	 * @param fileName
	 * @return
	 */
	public String reFileName(String fileName) {
		String name = fileName.substring(0, fileName.lastIndexOf("."));
		String afterFix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return name + IDGeneratorUtil.getID() + CommonConstant.PERIOD + afterFix;
	}

	@Override
	public void deleteAttachment(String attachmentId) {
		Attachment attachment = attachmentMapper.selectById(attachmentId);
		if (null != attachment) {
			attachmentMapper.deleteById(attachmentId);
			File file = new File(attachment.getFilePath());
			if (file.exists()) {
				file.delete();
			}
		}
	}

	@Override
	public String parseMarkdown(String markdownPath) {

		String currentDir = new File(markdownPath).getParentFile().getAbsolutePath();

		String content = FileUtil.getContent(markdownPath);

		List<String> matchStrList = LocalStringUtil.getMatchStr(content,
				PatternConstant.MARKDOWN_IMAGE);

		for (String matchStr : matchStrList) {
			String imagePath = LocalStringUtil.getFirstMatchStr(matchStr,
					PatternConstant.MARKDOWN_IMAGE_URL);
			File replaceFile = new File(currentDir, imagePath);
			if (replaceFile.exists()) {
				content = content.replace(imagePath, replaceFile.getAbsolutePath());
			}
		}

		return content;
	}

	@Override
	public List<String> unzipFile(String filePath, String distPath, String fileType) {

		ZipUtil.unzipToDest(filePath, distPath);

		return Arrays.stream(new File(distPath).listFiles()).filter(
				file -> StringUtils.isBlank(fileType) ? true : file.getName().endsWith(fileType))
				.map(File::getAbsolutePath).collect(Collectors.toList());
	}
}
