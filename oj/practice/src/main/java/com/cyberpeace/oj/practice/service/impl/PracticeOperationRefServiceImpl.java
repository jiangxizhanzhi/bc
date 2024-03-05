package com.cyberpeace.oj.practice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.PracticeRandomFlagMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.google.common.collect.Lists;
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

	@Autowired
	private PracticeRandomFlagMapper practiceRandomFlagMapper;

	@Autowired
	private PracticeOperationService practiceOperationService;

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

						// 随机flag
						if (operation.getDynamicFlag()
								.intValue() == PracticeOperation.DynamicFlag.RANDOM.getCode()
										.intValue()) {
							PracticeRandomFlag randomFlag = practiceRandomFlagMapper
									.selectByUserAndPractice(authContext.getUserId(),
											operation.getResourceId(),
											PracticeRandomFlag.FlagType.LESSON_PRACTICE.getCode()
													.intValue());
							if (null == randomFlag) {
								randomFlag = getRandomFlag(operation, authContext.getUserId());
								practiceRandomFlagMapper.insert(randomFlag);
							}

							operation.setAttachmentId(randomFlag.getAttachmentId());
						}

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

	private PracticeRandomFlag getRandomFlag(PracticeOperation operation, String userId) {

		PracticeRandomFlag randomFlag = new PracticeRandomFlag();

		// 随机选取对应的flag
		List<String> fileIds = Lists
				.newArrayList(operation.getAttachmentId().split(CommonConstant.CODE_COMMA));
		int index = MathUtils.randomRange(fileIds.size());

		// 数据组装
		randomFlag.setPracticeId(operation.getResourceId());
		randomFlag.setFlag(
				JsonUtil.toJsonObjectArray(operation.getAnswer(), String.class).get(index));
		randomFlag.setAttachmentId(fileIds.get(index));
		randomFlag.setUserId(userId);
		randomFlag.setType(PracticeRandomFlag.FlagType.LESSON_PRACTICE.getCode());

		return randomFlag;
	}

	@Override
	public PracticeOperation selectByPracticeId(String paperId, String practiceId) {
		PracticeOperationRef operationRef = practiceOperationRefMapper.selectByPracticeId(paperId,
				practiceId);
		PracticeOperation practiceOperation = JsonUtil.toJsonObject(operationRef.getSnapshot(),
				PracticeOperation.class);
		return practiceOperation;
	}

	@Override
	public void exportLessonPractice(String pathname, String paperId, Boolean disableImageExport) {

		List<PracticeOperationRef> operationRefList = practiceOperationRefMapper
				.selectByPaperId(paperId);
		List<PracticeOperation> practiceOperationList = StreamApiUtils.map(operationRefList,
				practiceTheoryRef -> {
					PracticeOperation practiceOperation = JsonUtil
							.toJsonObject(practiceTheoryRef.getSnapshot(), PracticeOperation.class);
					return practiceOperation;
				});

		practiceOperationService.exportPractice(pathname, practiceOperationList,
				disableImageExport);
	}
}
