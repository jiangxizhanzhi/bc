package com.cyberpeace.oj.practice.service.impl;

import java.util.List;

import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.dao.mapper.AttachmentMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PracticeOperationRefServiceImpl extends ServiceImpl<PracticeOperationRefMapper, PracticeOperationRef> implements PracticeOperationRefService {

	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	public List<OperationLessonBo> selectByPaperId(String paperId, Boolean viewAnswer) {

		List<PracticeOperationRef> operationRefs = practiceOperationRefMapper
				.selectByPracticeIds(paperId, null);

		List<OperationLessonBo> operationLessonBos = StreamApiUtils.simpleMap(operationRefs,
				practiceOperationRef -> {
					PracticeOperation operation = JsonUtil.toJsonObject(
							practiceOperationRef.getSnapshot(), PracticeOperation.class);
					OperationLessonBo operationLessonBo = BeanUtils.copy(operation,
							OperationLessonBo.class);

					// 附件内容查询
					if (StringUtils.isNotBlank(operation.getAttachmentId())) {
						Attachment attachment = attachmentMapper
								.selectById(operation.getAttachmentId());
						operationLessonBo.setFilePath(attachment.getFilePath());
						operationLessonBo.setFileName(attachment.getFileName());
					}

					if (StringUtils.isNotBlank(operation.getTopoId())) {
						// TODO 场景信息查询
					}

					// 是否展示答案
					if (!viewAnswer) {
						operationLessonBo.setAnswer(null);
					}

					return operationLessonBo;
				});

		return operationLessonBos;
	}

	@Override
	public PracticeOperation selectByPracticeId(String paperId, String practiceId) {
		PracticeOperationRef operationRef = practiceOperationRefMapper.selectByPracticeId(paperId,
				practiceId);
		PracticeOperation practiceOperation = JsonUtil.toJsonObject(operationRef.getSnapshot(),
				PracticeOperation.class);
		return practiceOperation;
	}
}
