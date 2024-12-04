package com.cyberpeace.oj.practice.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeRandomFlagMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 实践实操裁判Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see PracticeOperationRefService
 * @since 1.0.0
 */
@Service
@Slf4j
public class PracticeOperationRefServiceImpl extends ServiceImpl<PracticeOperationRefMapper, PracticeOperationRef> implements PracticeOperationRefService {

    /**
     * 实践实操裁判Mapper
     *
     * @see PracticeOperationRefMapper
     */
    @Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

    /**
     * 附件Mapper
     *
     * @see AttachmentMapper
     */
    @Autowired
	private AttachmentMapper attachmentMapper;

    /**
     * 远程拓扑Service
     *
     * @see RemoteTopoService
     */
    @Autowired
	private RemoteTopoService remoteTopoService;

    /**
     * utilService
     *
     * @see UtilService
     */
    @Autowired
	private UtilService utilService;

    /**
     * OJ当地场景Service
     *
     * @see OjLocalSceneService
     */
    @Autowired
	private OjLocalSceneService ojLocalSceneService;

    /**
     * 实践随机旗Mapper
     *
     * @see PracticeRandomFlagMapper
     */
    @Autowired
	private PracticeRandomFlagMapper practiceRandomFlagMapper;

    /**
     * 实践实操Service
     *
     * @see PracticeOperationService
     */
    @Autowired
	private PracticeOperationService practiceOperationService;

    /**
     * 查询由纸id
     *
     * @param lessonId    课id
     * @param paperId     纸id
     * @param viewAnswer  视图答案
     * @param simpleData  简单数据
     * @param authContext auth 上下文
     * @return {@code List<OperationLessonBo> }
     * @throws
     * @see String
     * @see String
     * @see Boolean
     * @see Boolean
     * @see AuthContext
     * @see List<OperationLessonBo>
     */
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
					operationLessonBo.setRefId(practiceOperationRef.getResourceId());
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

    /**
     * 获取随机旗
     *
     * @param operation 实操
     * @param userId    用户id
     * @return {@code PracticeRandomFlag }
     * @throws
     * @see PracticeOperation
     * @see String
     * @see PracticeRandomFlag
     */
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

    /**
     * 查询由实践id
     *
     * @param paperId    纸id
     * @param practiceId 实践id
     * @return {@code PracticeOperation }
     * @throws
     * @see String
     * @see String
     * @see PracticeOperation
     */
    @Override
	public PracticeOperation selectByPracticeId(String paperId, String practiceId) {
		PracticeOperationRef operationRef = practiceOperationRefMapper.selectByPracticeId(paperId,
				practiceId);
		return JsonUtil.toJsonObject(operationRef.getSnapshot(), PracticeOperation.class);
	}

    /**
     * 导出课程练习
     *
     * @param pathname           路径
     * @param paperId            纸id
     * @param disableImageExport 禁用图像导出
     * @throws
     * @see String
     * @see String
     * @see Boolean
     */
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

    /**
     * 查询课实践拓扑id列表
     *
     * @param paperIds 纸id列表
     * @return {@code List<String> }
     * @throws
     * @see List<String>
     * @see List<String>
     */
    @Override
	public List<String> selectLessonPracticeTopoIds(List<String> paperIds) {

		List<PracticeOperationRef> operationRefs = StreamApiUtils.flatMap(paperIds,
				paperId -> practiceOperationRefMapper.selectByPaperId(paperId));
		if (CollectionUtils.isEmpty(operationRefs)) {
			return Lists.newArrayList();
		}

		return StreamApiUtils.map(operationRefs, ref -> {
			PracticeOperation operation = JsonUtil.toJsonObject(ref.getSnapshot(),
					PracticeOperation.class);
			return StringUtils.isBlank(operation.getTopoId()) ? null : operation.getTopoId();
		});
	}

    /**
     * 实操由纸
     *
     * @param paperId  纸id
     * @param onlyTopo 只拓扑
     * @return {@code List<PracticeOperation> }
     * @throws
     * @see String
     * @see boolean
     * @see List<PracticeOperation>
     */
    @Override
	public List<PracticeOperation> operationByPaper(String paperId, boolean onlyTopo) {

		List<PracticeOperationRef> refList = practiceOperationRefMapper.selectByPaperId(paperId);
		List<PracticeOperation> operations = StreamApiUtils.map(refList,
				ref -> JsonUtil.toJsonObject(ref.getSnapshot(), PracticeOperation.class));

		if (!onlyTopo) {
			return operations;
		}
		return StreamApiUtils.filter(operations,
				operation -> StringUtils.isNotBlank(operation.getTopoId()));
	}

    /**
     * 实操由纸
     *
     * @param lessonId    课id
     * @param paperId     纸id
     * @param authContext auth 上下文
     * @return {@code List<OjLocalScene> }
     * @throws
     * @see String
     * @see String
     * @see AuthContext
     * @see List<OjLocalScene>
     */
    @Override
	public List<OjLocalScene> operationByPaper(String lessonId, String paperId,
			AuthContext authContext) {
		List<PracticeOperation> operations = this.operationByPaper(paperId, true);
		return StreamApiUtils.map(operations, operation -> {
			String businessId = lessonId + CommonConstant.CODE_COMMA + operation.getResourceId();
			return ojLocalSceneService.selectOjLocalScene(businessId, operation.getSceneShareMode(),
					authContext);
		});
	}
}
