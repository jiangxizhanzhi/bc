package com.cyberpeace.oj.practice.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.practice.dao.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
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

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Override
	public List<OperationLessonBo> selectByPaperId(String lessonId, String paperId,
			Boolean viewAnswer, Boolean simpleData, AuthContext authContext) {

		List<PracticeOperationRef> operationRefs = practiceOperationRefMapper
				.selectByPracticeIds(paperId, null);

		List<OperationLessonBo> operationLessonBos = StreamApiUtils.map(operationRefs,
				practiceOperationRef -> {
					PracticeOperation operation = JsonUtil.toJsonObject(
							practiceOperationRef.getSnapshot(), PracticeOperation.class);
					OperationLessonBo operationLessonBo = BeanUtils.copy(operation,
							OperationLessonBo.class);

					// 附件内容查询
					if (!simpleData && StringUtils.isNotBlank(operation.getAttachmentId())) {
						Attachment attachment = attachmentMapper
								.selectById(operation.getAttachmentId());

						operationLessonBo
								.setFilePath(utilService.handlerFileUrl(attachment.getFilePath()));
						operationLessonBo.setFileName(attachment.getFileName());
					}

					// 场景相关数据查询
					if (!simpleData && operation.getDynamicEnv()
							&& StringUtils.isNotBlank(operation.getTopoId())) {
						JSONObject topoData = remoteTopoService.getTopoById(operation.getTopoId());
						operationLessonBo.setSceneConfigData(topoData);

						String businessId = lessonId + CommonConstant.CODE_COMMA
								+ operationLessonBo.getResourceId();
						OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(
								businessId, operationLessonBo.getSceneShareMode(), authContext);
						if (null != ojLocalScene) {
							JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
							operationLessonBo.setSceneData(sceneData);
						}
					}

					// 是否展示答案
					if (!viewAnswer) {
						operationLessonBo.setAnswer(null);
					}

					// 题型和习题类型设置
					operationLessonBo
							.setPracticeType(PracticeCategory.PracticeType.OPERATION.getCode());
					operationLessonBo
							.setQuestionType(PracticeTheory.TheoryType.OPERATION.getCode());

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
