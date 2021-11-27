package com.cyberpeace.oj.ojbase.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.dao.mapper.AttachmentMapper;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

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
			List<AttachmentBo> attachmentBos = StreamApiUtils.simpleMap(attachments,
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

		if (file.isEmpty()) {
			throw new InvalidParamException("请选择文件上传！");
		}

		String filePath = EnumUtils.getEnumDescByCode(type, Attachment.AttachmentType.class);
		if (StringUtils.isBlank(filePath)) {
			throw new InvalidParamException("不支持此类型文件上传！");
		}

		File uploadFile = new File(filePath + file.getOriginalFilename());
		if (uploadFile.exists()) {
			uploadFile = new File(filePath + reFileName(file.getOriginalFilename()));
			log.error("Upload file exist! upload_file_path=[{}]", uploadFile.getAbsolutePath());
			Attachment attachment = attachmentMapper.selectByPath(uploadFile.getAbsolutePath());
			AttachmentBo attachmentBo = BeanUtils.copy(attachment, AttachmentBo.class);
			return attachmentBo;
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
			AttachmentBo attachmentBo = create(uploadFile.getName(), uploadFile.getAbsolutePath());
			return attachmentBo;
		}

		return null;
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
}
