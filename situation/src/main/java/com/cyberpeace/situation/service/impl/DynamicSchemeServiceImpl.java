package com.cyberpeace.situation.service.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeReq;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import com.cyberpeace.situation.dao.entity.DynamicScheme;
import com.cyberpeace.situation.dao.mapper.AttackMessageMapper;
import com.cyberpeace.situation.dao.mapper.DynamicSchemeMapper;
import com.cyberpeace.situation.dao.mapper.SceneConfigMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.WsAttackEffectDynamic;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import com.cyberpeace.situation.service.*;
import com.google.common.collect.Lists;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DynamicSchemeServiceImpl extends ServiceImpl<DynamicSchemeMapper, DynamicScheme> implements DynamicSchemeService {

	@Autowired
	private AttackMessageMapper attackMessageMapper;

	@Autowired
	private DynamicSchemeMapper dynamicSchemeMapper;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private AttackMessageService attackMessageService;

	@Autowired
	private SceneConfigMapper sceneConfigMapper;

	@Autowired
	private SceneConfigService sceneConfigService;

	public static String PICTURE_TYPE = "PNG,JPG,JPEG,GIF,SVG";

	public static String VIDEO_TYPE = "MP4";

	public static final String RESOURCE_FILE_NAME = "data.json";

	public Integer MIN_STR_LEN = 1;

	public Integer MAX_NAME_STR_LEN = 255;

	public Integer STAGE_HAS_BEEN_RUN = 0;

	public Integer STAGE_IS_RUNNING = 1;

	public Integer DYNAMIC_HAS_BEEN_RUN = 2;

	public Integer DYNAMIC_IS_RUNNING = 3;

	public Integer PICTURE_CODE = 1;

	public Integer VIDEO_CODE = 2;

	@Override
	public String addDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq) {
		DynamicScheme dynamicScheme = addDynamicSchemeReqToDynamicScheme(addDynamicSchemeReq);
		dynamicScheme.setResourceId(IDGeneratorUtil.getID());
		dynamicSchemeMapper.insert(dynamicScheme);
		return dynamicScheme.getResourceId();
	}

	@Override
	public DynamicSchemeRes getDynamicSchemeById(String resourceId) {
		DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
		if (dynamicScheme == null) {
			log.error("get dynamic scheme by id ({}) error, dynamic scheme not found", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		DynamicSchemeRes dynamicSchemeRes = BeanUtils.copy(dynamicScheme, DynamicSchemeRes.class);
		List<DynamicStageBo> dynamicStageBoList = JsonUtil
				.toJsonObjectArray(dynamicScheme.getSchemeData(), DynamicStageBo.class);
		List<DynamicStage> dynamicStageResList = new ArrayList<>();
		for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
			DynamicStage dynamicStageRes = new DynamicStage();
			dynamicStageRes.setName(dynamicStageBo.getName());
			List<AttackMessageReq> attackMessageBoList = new ArrayList<>();
			if (dynamicStageBo.getAttackMessageIds().size() > 0) {
				List<AttackMessage> attackMessageList = attackMessageMapper
						.selectBatchIds(dynamicStageBo.getAttackMessageIds());
				if (attackMessageList.size() < dynamicStageBo.getAttackMessageIds().size()) {
					log.error(
							"Messages of dynamic scheme do not match the data in the database, scheme id: {}",
							resourceId);
					throw new BusinessException(SituationErrorCode.CANNOT_FIND_DYNAMIC);
				}
				for (String attackMessageId : dynamicStageBo.getAttackMessageIds()) {
					AttackMessage attackMessage = new AttackMessage();
					for (AttackMessage message : attackMessageList) {
						if (message.getResourceId().equals(attackMessageId)) {
							attackMessage = message;
							break;
						}
					}
					AttackMessageReq attackMessageBo = BeanUtils.copy(attackMessage,
							AttackMessageReq.class);
					if (attackMessage.getMediaAnimation() == null) {
						MediaAnimation mediaAnimation = new MediaAnimation();
						List<MediaAnimation> emptyMediaAnimation = new ArrayList<>();
						emptyMediaAnimation.add(mediaAnimation);
						attackMessageBo.setMediaAnimationList(emptyMediaAnimation);
					} else {
						attackMessageBo.setMediaAnimationList(JsonUtil.toJsonObjectArray(
								attackMessage.getMediaAnimation(), MediaAnimation.class));
					}
					attackMessageBoList.add(attackMessageBo);
					dynamicStageRes.setAttackMessageReqList(attackMessageBoList);
				}
			} else {
				dynamicStageRes.setAttackMessageReqList(attackMessageBoList);
			}
			dynamicStageResList.add(dynamicStageRes);
		}
		dynamicSchemeRes.setSchemeData(dynamicStageResList);
		return dynamicSchemeRes;
	}

	@Override
	public DynamicSchemeReq updateDynamicScheme(DynamicSchemeReq dynamicSchemeReq) {
		DynamicScheme dynamicScheme = BeanUtils.copy(dynamicSchemeReq, DynamicScheme.class);
		List<DynamicStageBo> dynamicStageBoList = new ArrayList<>();
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<AttackMessageReq> updateAttackMessageReqList = new ArrayList<>();
				List<AttackMessageReq> addAttackMessageReqList = new ArrayList<>();
				for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
					// 判断同一阶段内动效描述是否重复
					HashSet<String> description = new HashSet<>(dynamicStage
							.getAttackMessageReqList().stream()
							.map(AttackMessageReq::getDescription).collect(Collectors.toList()));
					if (description.size() < dynamicStage.getAttackMessageReqList().size()) {
						log.error("description of dynamic stage is duplicated, stage name: {}",
								dynamicStage.getName());
						throw new BusinessException(
								SituationErrorCode.DUPLICATED_DYNAMIC_DESCRIPTION);
					}
					for (AttackMessageReq attackMessageReq : dynamicStage
							.getAttackMessageReqList()) {
						// 设置多媒体的名称
						if (attackMessageReq.getType()
								.equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
							for (MediaAnimation mediaAnimation : attackMessageReq
									.getMediaAnimationList()) {
								mediaAnimation.setName(
										FileUtil.getFileName(mediaAnimation.getUrl(), true));
							}
						}
						if (attackMessageReq.getResourceId() == null
								|| attackMessageReq.getResourceId().equals("")) {
							// 数据库中不存在该动效，进行新增
							attackMessageReq.setSceneId(dynamicSchemeReq.getSceneId());
							addAttackMessageReqList.add(attackMessageReq);
						} else {
							// 数据库中存在该动效，进行更新
							updateAttackMessageReqList.add(attackMessageReq);
						}
					}
				}

				attackMessageService.bathAddAttackMessage(addAttackMessageReqList);
				attackMessageService.bathUpdateAttackMessage(updateAttackMessageReqList);

				for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
					DynamicStageBo dynamicStageBo = new DynamicStageBo();
					dynamicStageBo.setName(dynamicStage.getName());
					dynamicStageBo.setAttackMessageIds(dynamicStage.getAttackMessageReqList()
							.stream().map(AttackMessageReq::getResourceId)
							.collect(Collectors.toList()));
					dynamicStageBoList.add(dynamicStageBo);
				}

				dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageBoList));
				dynamicSchemeMapper.updateById(dynamicScheme);
			}
		});
		return dynamicSchemeReq;
	}

	@Override
	public void deleteDynamicScheme(String resourceId) {
		List<String> attackMessageIds = new ArrayList<>();
		DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
		List<DynamicStageBo> dynamicStageBoList = JsonUtil
				.toJsonObjectArray(dynamicScheme.getSchemeData(), DynamicStageBo.class);
		for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
			attackMessageIds.addAll(dynamicStageBo.getAttackMessageIds());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (attackMessageIds.size() > 0) {
					attackMessageMapper.deleteBatchIds(attackMessageIds);
				}
				dynamicSchemeMapper.deleteById(resourceId);
			}
		});
	}

	@Override
	public List<DynamicSchemeNameRes> getAllDynamicName(String sceneId) {
		return dynamicSchemeMapper.getAllNamesBySceneId(sceneId);
	}

	@Override
	public void getLocalOption(OptionsRes optionsRes) {
		optionsRes.setAttackTypeList(StreamApiUtils.map(Lists.newArrayList(AttackType.values()),
				attackType -> new WebAttackType(attackType.getKeyWord(), attackType.getMsg(),
						attackType.getActionType().getType(), attackType.getActionType().isSource(),
						attackType.getActionType().isDurationTime())));
		optionsRes.setLineTypeList(StreamApiUtils.map(Lists.newArrayList(LineType.values()),
				lineType -> new LocalType(lineType.getCode(), lineType.getMsg())));
		optionsRes.setColorList(StreamApiUtils.map(Lists.newArrayList(Color.values()),
				color -> new LocalType(color.getCode(), color.getMsg())));
	}

	@Override
	public void getRemoteOption(OptionsRes optionsRes, String sceneId, Boolean isInstance) {

		if (StringUtils.isEmpty(sceneId)) {
			log.error("sceneId is null");
			throw new BusinessException(SituationErrorCode.UNAVAILABLE_FOR_NON_SCENE_SITUATION);
		}

		SceneData sceneData;
		if (isInstance != null && !isInstance) {
			sceneData = topoHandlerService.getSceneData(null, sceneId);
		} else {
			sceneData = topoHandlerService.getSceneData(sceneId, null);
		}
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
		List<AreaInfo> areaInfoList = Lists.newArrayList(situationTopo.getAreas().values());
		List<ElementInfo> elementInfoList = Lists
				.newArrayList(situationTopo.getElements().values());
		optionsRes.setAreaList(
				StreamApiUtils.map(areaInfoList, areaInfo -> new ElementType(areaInfo.getAreaId(),
						"area-" + areaInfo.getAreaId().substring(0, 8))));
		optionsRes.setElementList(StreamApiUtils.map(elementInfoList,
				elementInfo -> new ElementType(elementInfo.getElementId(), elementInfo.getName())));
	}

	public DynamicScheme
			addDynamicSchemeReqToDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq) {
		DynamicScheme dynamicScheme = BeanUtils.copy(addDynamicSchemeReq, DynamicScheme.class);
		List<DynamicStageBo> dynamicStageList = new ArrayList<>();
		dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageList));
		return dynamicScheme;
	}

	/**
	 * 推送多媒体动效消息
	 */
	@Override
	public void pushMediaToRoom(AttackMessageReq attackMessageBo, String code) {
		WsAttackEffectDynamic wsAttackEffectDynamic = new WsAttackEffectDynamic();
		wsAttackEffectDynamic.setSceneId(attackMessageBo.getSceneId());
		wsAttackEffectDynamic.setCategory("attack");
		wsAttackEffectDynamic.setDescription(attackMessageBo.getDescription());
		wsAttackEffectDynamic.setName("攻击效果");
		wsAttackEffectDynamic.setType(ActionType.NONE.getCode());
		List<AttackEffect> attackEffectList = new ArrayList<>();
		for (MediaAnimation mediaAnimation : attackMessageBo.getMediaAnimationList()) {
			AttackEffect attackEffect = BeanUtils.copy(mediaAnimation, AttackEffect.class);
			attackEffect.setElementId(mediaAnimation.getSourceId());

			// 判断是图片还是视频
			String fileSuffix = FileUtil.getFileSuffix(mediaAnimation.getUrl()).toLowerCase();
			List<String> pictureTypeList = Arrays
					.asList(PICTURE_TYPE.toLowerCase().split(CommonConstant.CODE_COMMA));
			List<String> videoTypeList = Arrays
					.asList(VIDEO_TYPE.toLowerCase().split(CommonConstant.CODE_COMMA));
			if (pictureTypeList.contains(fileSuffix)) {
				attackEffect.setMediaType(PICTURE_CODE);
			} else if (videoTypeList.contains(fileSuffix)) {
				attackEffect.setMediaType(VIDEO_CODE);
			} else {
				log.error("media type error, fileSuffix:{}", fileSuffix);
				throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
			}

			attackEffect.setMediaUrl(mediaAnimation.getUrl());
			attackEffect.setLineStyle(new LineStyle("#FFFFFF", 3, "straight"));
			attackEffectList.add(attackEffect);
		}
		wsAttackEffectDynamic.setEffectList(attackEffectList);

		WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(
				MessageType.AttackEffect.getDesc(), wsAttackEffectDynamic);
		situationHandlerService.pushDataToRoom(wsMessage, attackMessageBo.getSceneId());

		Thread thread = new Thread(new Runnable() {

			@SneakyThrows
			@Override
			public void run() {
				// 持续时间结束后清除该多媒体动效
				Thread.sleep(attackMessageBo.getDurationTime() * 1000);
				// 判断当前是否有其他动效正在运行
				if (sceneConfigService.isCurrentScheme(attackMessageBo.getSceneId(), code)) {
					clearMedia(attackMessageBo.getSceneId());
				}
			}
		});
		thread.start();

	}

	/**
	 * 推送其他消息
	 */
	@Override
	public void pushAttackToRoom(AttackMessageReq attackMessageBo, List<String> elementIds) {
		WsMultiNodeDynamic wsMultiNodeDynamic = BeanUtils.copy(attackMessageBo,
				WsMultiNodeDynamic.class);
		wsMultiNodeDynamic.setCategory("attack");
		for (AttackType attackType : AttackType.values()) {
			if (attackType.getKeyWord().equals(attackMessageBo.getType())) {
				wsMultiNodeDynamic.setType(attackType.getActionType().getCode());
			}
		}
		if (wsMultiNodeDynamic.getType() == null) {
			wsMultiNodeDynamic.setType(ActionType.NONE.getCode());
		}
		wsMultiNodeDynamic.setName(null);
		wsMultiNodeDynamic.setLineStyle(new LineStyle(attackMessageBo.getLineColor(),
				attackMessageBo.getLineWidth(), attackMessageBo.getLineType()));
		wsMultiNodeDynamic.setDuration(attackMessageBo.getDurationTime());
		WsMessage<WsMultiNodeDynamic> wsMessage;

		String type = attackMessageBo.getType();
		Boolean isSingleNode = type.equals(AttackType.HIGHLIGHT_AREA.getKeyWord())
				|| type.equals(AttackType.HIGHLIGHT_ELEMENT.getKeyWord());

		if (elementIds.contains(attackMessageBo.getTargetId())) {
			wsMessage = new WsMessage<>(MessageType.AttackEventEE.getDesc(), wsMultiNodeDynamic);
		} else {
			wsMessage = new WsMessage<>(MessageType.AttackEventEA.getDesc(), wsMultiNodeDynamic);
		}
		if (isSingleNode) {
			wsMessage = new WsMessage<>(MessageType.SingleNodeDynamic.getDesc(),
					wsMultiNodeDynamic);
		}
		situationHandlerService.pushDataToRoom(wsMessage, wsMultiNodeDynamic.getSceneId());
	}

	@Override
	public void pushDynamicSchemeToRoom(DynamicSchemeReq dynamicSchemeReq) {

		String sceneId = dynamicSchemeReq.getSceneId();
		String code = sceneConfigService.updateSceneCode(sceneId);
		List<String> elementIds = getElementIds(sceneId, dynamicSchemeReq.getIsInstance());

		Thread thread = new Thread(new Runnable() {

			@SneakyThrows
			@Override
			public void run() {
				// 进程信息
				List<WsAttackSchedule> wsAttackScheduleList = new ArrayList<>();
				// 当前动效方案是否正常运行
				boolean state = true;
				for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
					if (!state) {
						break;
					}

					WsAttackSchedule wsAttackScheduleStage = new WsAttackSchedule(
							dynamicStage.getName(), STAGE_IS_RUNNING);
					wsAttackScheduleList.add(wsAttackScheduleStage);
					WsAttackSchedule wsAttackMessageSchedule = new WsAttackSchedule("",
							DYNAMIC_IS_RUNNING);
					wsAttackScheduleList.add(wsAttackMessageSchedule);
					// 动效序号
					int number = 1;

					for (AttackMessageReq attackMessageReq : dynamicStage
							.getAttackMessageReqList()) {
						attackMessageReq.setSceneId(sceneId);
						// 查看当前是否有其他动效方案正在运行
						if (!sceneConfigService.isCurrentScheme(sceneId, code)) {
							// 有其他方案正在运行，当前方案停止
							state = false;
							break;
						}

						wsAttackMessageSchedule
								.setName(number++ + "." + attackMessageReq.getDescription());
						pushAttackScheduleToRoom(wsAttackScheduleList, sceneId);
						sceneConfigService.updateScheduleById(sceneId,
								JSONObject.toJSONString(wsAttackScheduleList));

						if (attackMessageReq.getType()
								.equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
							pushMediaToRoom(attackMessageReq, code);
						} else {
							pushAttackToRoom(attackMessageReq, elementIds);
						}
						if (attackMessageReq.getIntervalTime() != null) {
							Thread.sleep(attackMessageReq.getIntervalTime() * 1000);
						}

					}
					if (state && sceneConfigService.isCurrentScheme(sceneId, code)) {
						wsAttackScheduleStage.setStatus(STAGE_HAS_BEEN_RUN);
						wsAttackMessageSchedule.setStatus(DYNAMIC_HAS_BEEN_RUN);
						wsAttackMessageSchedule.setName("");
						pushAttackScheduleToRoom(wsAttackScheduleList, sceneId);
						sceneConfigService.updateScheduleById(sceneId,
								JSONObject.toJSONString(wsAttackScheduleList));
					}
				}

				sceneConfigService.updateScheduleById(sceneId,
						JSONObject.toJSONString(Lists.newArrayList()));

			}
		});
		thread.start();

	}

	/**
	 * 校验动效方案是否重名
	 */
	@Override
	public void validDynamicSchemeName(AddDynamicSchemeReq addDynamicSchemeReq) {
		validateNameLen(addDynamicSchemeReq.getName(),
				SituationErrorCode.INVALID_DYNAMIC_SCHEME_NAME);
		DynamicScheme dynamicScheme = dynamicSchemeMapper
				.selectByName(addDynamicSchemeReq.getName(), addDynamicSchemeReq.getSceneId());
		if (dynamicScheme != null) {
			log.error("dynamic scheme name is duplicated, name:{}", addDynamicSchemeReq.getName());
			throw new BusinessException(SituationErrorCode.DUPLICATED_DYNAMIC_SCHEME_NAME);

		}
	}

	@Override
	public List<WsAttackSchedule> getSchedule(String sceneId) {

		if (sceneId == null) {
			return new ArrayList<>();
		}

		String schedule = sceneConfigService.getScheduleBySceneId(sceneId);

		if (schedule == null) {
			return new ArrayList<>();
		}
		return JsonUtil.toJsonObjectArray(schedule, WsAttackSchedule.class);
	}

	@Override
	public void clearSceneData(String sceneId) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				sceneConfigMapper.deleteBySceneId(sceneId);
				dynamicSchemeMapper.deleteBySceneId(sceneId);
				attackMessageMapper.deleteBySceneId(sceneId);
			}
		});
		log.info(String.format("Clear situation scene config, scene id is %s", sceneId));
	}

	/**
	 * 清除之前的多媒体动效
	 */
	@Override
	public void clearMedia(String sceneId) {
		WsAttackEffectDynamic wsAttackEffectDynamicBlank = new WsAttackEffectDynamic();
		wsAttackEffectDynamicBlank.setName("攻击效果");
		wsAttackEffectDynamicBlank.setType(ActionType.NONE.getCode());
		wsAttackEffectDynamicBlank.setCategory("attack");
		wsAttackEffectDynamicBlank.setEffectList(new ArrayList<>());
		WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(
				MessageType.AttackEffect.getDesc(), wsAttackEffectDynamicBlank);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
	}

	/**
	 * 校验阶段
	 */
	@Override
	public void validStage(DynamicSchemeReq dynamicSchemeReq) {
		for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
			if (dynamicStage.getName() == null || dynamicStage.getName().equals("")) {
				log.error("dynamic stage name is empty, dynamic name is {}",
						dynamicSchemeReq.getName());
				throw new BusinessException(SituationErrorCode.EMPTY_STAGE_NAME);
			}
			validateNameLen(dynamicStage.getName(), SituationErrorCode.INVALID_STAGE_NAME);
			for (AttackMessageReq attackMessageReq : dynamicStage.getAttackMessageReqList()) {
				validateNameLen(attackMessageReq.getDescription(),
						SituationErrorCode.INVALID_DYNAMIC_DESCRIPTION);

				String type = attackMessageReq.getType();

				Boolean isSingleNode = type.equals(AttackType.HIGHLIGHT_AREA.getKeyWord())
						|| type.equals(AttackType.HIGHLIGHT_ELEMENT.getKeyWord());

				if (!(isSingleNode || attackMessageReq.getType()
						.equals(AttackType.MEDIA_ANIMATION.getKeyWord()))
						&& attackMessageReq.getSourceId().equals("")) {
					log.error("dynamic source node is empty, dynamic name is {}",
							dynamicSchemeReq.getName());
					throw new BusinessException(SituationErrorCode.EMPTY_SOURCE_NODE,
							attackMessageReq.getDescription());
				}
			}

		}

	}

	/**
	 * 清除场景进程信息
	 *
	 * @param sceneId
	 */
	@Override
	public void clearRoomSchedule(String sceneId) {
		List<WsAttackSchedule> wsAttackScheduleList = new ArrayList<>();
		pushAttackScheduleToRoom(wsAttackScheduleList, sceneId);
	}

	@Override
	public String makeExportFile(String resourceId, String tempDir) throws IOException {
		// 1. make temp dir
		FileUtil.mkdirs(tempDir);
		DynamicSchemeRes dynamicSchemeRes = getDynamicSchemeById(resourceId);
		for (DynamicStage dynamicStage : dynamicSchemeRes.getSchemeData()) {
			for (AttackMessageReq attackMessageReq : dynamicStage.getAttackMessageReqList()) {
				attackMessageReq.setResourceId(null);
			}
		}
		String data = JSONObject.toJSONString(dynamicSchemeRes);
		// 4. generate data.json
		String jsonPath = tempDir + File.separator + RESOURCE_FILE_NAME;
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonPath),
				StandardCharsets.UTF_8);
		FileUtil.writeString(data, osw);
		// 5. generate zip file, return zip file path
		return ZipUtil.toZip(tempDir);
	}

	@Override
	public String importDynamicScheme(String unZipPath, String sceneId, Boolean isInstance) {
		String dataJson = FileUtil
				.getContent(new File(unZipPath + File.separator + RESOURCE_FILE_NAME));
		DynamicSchemeReq dynamicSchemeReq = JsonUtil.toJsonObject(dataJson, DynamicSchemeReq.class);
		validDynamicSchemeName(new AddDynamicSchemeReq(sceneId, dynamicSchemeReq.getName()));
		List<DynamicStageBo> dynamicStageBoList = new ArrayList<>();

		// 获取该场景下的所有区域和节点id
		SceneData sceneData;
		if (isInstance != null && !isInstance) {
			sceneData = topoHandlerService.getSceneData(null, sceneId);
		} else {
			sceneData = topoHandlerService.getSceneData(sceneId, null);
		}
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
		List<AreaInfo> areaInfoList = Lists.newArrayList(situationTopo.getAreas().values());
		List<ElementInfo> elementInfoList = Lists
				.newArrayList(situationTopo.getElements().values());
		List<String> areaAndElementIds = new ArrayList<>();
		areaAndElementIds.addAll(
				areaInfoList.stream().map(AreaInfo::getAreaId).collect(Collectors.toList()));
		areaAndElementIds.addAll(elementInfoList.stream().map(ElementInfo::getElementId)
				.collect(Collectors.toList()));

		// 判断动效方案中涉及到的节点该场景中是否都存在
		for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
			for (AttackMessageReq attackMessageReq : dynamicStage.getAttackMessageReqList()) {
				if (attackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
					for (MediaAnimation mediaAnimation : attackMessageReq.getMediaAnimationList()) {
						if (!areaAndElementIds.contains(mediaAnimation.getSourceId())) {
							log.error("some nodes not exist, dynamic name is {}",
									dynamicSchemeReq.getName());
							throw new BusinessException(SituationErrorCode.SOME_NODES_NOT_EXIST);
						}
					}
				} else {
					if (StringUtils.isNotEmpty(attackMessageReq.getTargetId())
							&& !areaAndElementIds.contains(attackMessageReq.getTargetId())) {
						log.error("some nodes not exist, dynamic name is {}",
								dynamicSchemeReq.getName());
						throw new BusinessException(SituationErrorCode.SOME_NODES_NOT_EXIST);
					}
					if (StringUtils.isNotEmpty(attackMessageReq.getSourceId())
							&& !areaAndElementIds.contains(attackMessageReq.getSourceId())) {
						log.error("some nodes not exist, dynamic name is {}",
								dynamicSchemeReq.getName());
						throw new BusinessException(SituationErrorCode.SOME_NODES_NOT_EXIST);
					}
				}
			}
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<AttackMessageReq> attackMessageReqList = new ArrayList<>();
				for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {

					HashSet<String> description = dynamicStage.getAttackMessageReqList().stream()
							.map(AttackMessageReq::getDescription)
							.collect(Collectors.toCollection(HashSet::new));
					if (description.size() < dynamicStage.getAttackMessageReqList().size()) {
						log.error("dynamic scheme name is duplicated, dynamic name is {}",
								dynamicSchemeReq.getName());
						throw new BusinessException(
								SituationErrorCode.DUPLICATED_DYNAMIC_SCHEME_NAME);
					}

					// 保存动效
					for (AttackMessageReq attackMessageReq : dynamicStage
							.getAttackMessageReqList()) {
						attackMessageReq.setSceneId(sceneId);
						attackMessageReqList.add(attackMessageReq);
					}

				}

				attackMessageService.bathAddAttackMessage(attackMessageReqList);

				for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
					DynamicStageBo dynamicStageBo = new DynamicStageBo();
					dynamicStageBo.setName(dynamicStage.getName());
					dynamicStageBo.setAttackMessageIds(dynamicStage.getAttackMessageReqList()
							.stream().map(AttackMessageReq::getResourceId)
							.collect(Collectors.toList()));
					dynamicStageBoList.add(dynamicStageBo);
				}

				DynamicScheme dynamicScheme = BeanUtils.copy(dynamicSchemeReq, DynamicScheme.class);
				dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageBoList));
				dynamicScheme.setSceneId(sceneId);
				dynamicScheme.setResourceId(IDGeneratorUtil.getID());
				dynamicSchemeMapper.insert(dynamicScheme);
				dynamicSchemeReq.setResourceId(dynamicScheme.getResourceId());
			}
		});
		return dynamicSchemeReq.getResourceId();
	}

	/**
	 * 验证多媒体动效存在时间是否重叠
	 *
	 * @param dynamicSchemeReq
	 */
	@Override
	public void validMedia(DynamicSchemeReq dynamicSchemeReq) {
		int startTime = 0;
		int endTime = -1;
		for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
			for (AttackMessageReq attackMessageReq : dynamicStage.getAttackMessageReqList()) {
				if (attackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
					if (startTime <= endTime) {
						log.error("media animation have time overlap, dynamic name is {}",
								dynamicSchemeReq.getName());
						throw new BusinessException(
								SituationErrorCode.MULTIMEDIA_MEDIA_ANIMATION_HAVE_TIME_OVERLAP);
					}
					endTime = startTime + attackMessageReq.getDurationTime();
				}
				startTime += attackMessageReq.getIntervalTime();
			}
		}
	}

	@Override
	public List<String> getElementIds(String sceneId, Boolean isInstance) {

		SceneData sceneData;
		if (isInstance != null && !isInstance) {
			sceneData = topoHandlerService.getSceneData(null, sceneId);
		} else {
			sceneData = topoHandlerService.getSceneData(sceneId, null);
		}
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
		List<ElementInfo> elementInfoList = Lists
				.newArrayList(situationTopo.getElements().values());
		return elementInfoList.stream().map(ElementInfo::getElementId).collect(Collectors.toList());
	}

	/**
	 * 推送进程消息
	 */
	public void pushAttackScheduleToRoom(List<WsAttackSchedule> wsAttackScheduleList,
			String sceneId) {
		WsMessage<List<WsAttackSchedule>> wsMessage = new WsMessage<>(
				MessageType.AttackSchedule.getDesc(), wsAttackScheduleList);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
	}

	public void validateLength(String target, Integer minLen, Integer maxLen, String errorCode) {
		if (target == null) {
			if (minLen == 0) {
				return;
			} else {
				log.error("length of name is null, minLen is {}, maxLen is {}", minLen, maxLen);
				throw new BusinessException(errorCode, minLen, maxLen);
			}
		}
		try {
			String targetStr = new String(target.getBytes("GBK"), "iso-8859-1");
			int len = targetStr.length();
			if (len < minLen || len > maxLen) {
				log.error("length of name is {}, minLen is {}, maxLen is {}", len, minLen, maxLen);
				throw new BusinessException(errorCode, minLen, maxLen);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void validateNameLen(String target, String errorCode) {
		validateLength(target, MIN_STR_LEN, MAX_NAME_STR_LEN, errorCode);
	}

}
