package com.cyberpeace.oj.ojbase.service.impl;

import java.util.List;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
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
	public List<AttachmentBo> selectByIds(String attachmentIds) {
		if (StringUtils.isNotBlank(attachmentIds)) {
			String[] attachmentArr = StringUtils.split(attachmentIds, CommonConstant.CODE_COMMA);

			List<Attachment> attachments = attachmentMapper
					.selectBatchIds(Lists.newArrayList(attachmentArr));
			List<AttachmentBo> attachmentBos = StreamApiUtils.simpleMap(attachments,
					attachment -> BeanUtils.copy(attachment, AttachmentBo.class));
			return attachmentBos;
		}
		return Lists.newArrayList();
	}
}
