package com.cyberpeace.oj.ojbase.service.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
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

@Service
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
}
