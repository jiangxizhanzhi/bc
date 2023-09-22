package com.cyberpeace.situation.service.impl;

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
import com.cyberpeace.situation.constant.ws.DefenseType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeReq;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.res.AttackMessageHistoryRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import com.cyberpeace.situation.dao.entity.AttackMessageHistory;
import com.cyberpeace.situation.dao.entity.DynamicScheme;
import com.cyberpeace.situation.dao.mapper.AttackMessageHistoryMapper;
import com.cyberpeace.situation.dao.mapper.AttackMessageMapper;
import com.cyberpeace.situation.dao.mapper.DynamicSchemeMapper;
import com.cyberpeace.situation.dao.mapper.SceneConfigMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.*;
import com.cyberpeace.situation.service.*;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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

	@Autowired
	private AttackMessageHistoryService attackMessageHistoryService;

	@Autowired
	private AttackMessageHistoryMapper attackMessageHistoryMapper;

	//允许上传的图片格式
	public static String PICTURE_TYPE = "PNG,JPG,JPEG,GIF,SVG";

	//允许上传的视频格式
	public static String VIDEO_TYPE = "MP4";

	//导出文件名称
	public static final String RESOURCE_FILE_NAME = "data.json";

	//字符串最小长度
	public Integer MIN_STR_LEN = 1;

	//名称最大长度
	public Integer MAX_NAME_STR_LEN = 255;

	//阶段已运行
	public Integer STAGE_HAS_BEEN_RUN = 0;

	//阶段正在运行
	public Integer STAGE_IS_RUNNING = 1;

	//动效已运行
	public Integer DYNAMIC_HAS_BEEN_RUN = 2;

	//动效正在运行
	public Integer DYNAMIC_IS_RUNNING = 3;

	//图片code
	public Integer PICTURE_CODE = 1;

	//视频code
	public Integer VIDEO_CODE = 2;

	@Override
	public String addDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq) {
		//将AddDynamicSchemeReq转成DynamicScheme
		DynamicScheme dynamicScheme = addDynamicSchemeReqToDynamicScheme(addDynamicSchemeReq);
		//设置资源id
		dynamicScheme.setResourceId(IDGeneratorUtil.getID());
		//插入数据库
		dynamicSchemeMapper.insert(dynamicScheme);
		return dynamicScheme.getResourceId();
	}

	@Override
	public DynamicSchemeRes getDynamicSchemeById(String resourceId) {
		//读取动效方案数据
		DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
		if (dynamicScheme == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		//DynamicScheme转DynamicSchemeRes
		DynamicSchemeRes dynamicSchemeRes = BeanUtils.copy(dynamicScheme, DynamicSchemeRes.class);
		//获取动效阶段数据
		List<DynamicStageBo> dynamicStageBoList = JsonUtil
				.toJsonObjectArray(dynamicScheme.getSchemeData(), DynamicStageBo.class);
		//动效阶段数据
		List<DynamicStage> dynamicStageResList = new ArrayList<>();
		for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
			DynamicStage dynamicStageRes = new DynamicStage();
			//设置动效阶段名称
			dynamicStageRes.setName(dynamicStageBo.getName());
			List<AttackMessageReq> attackMessageBoList = new ArrayList<>();
			if (dynamicStageBo.getAttackMessageIds().size() > 0) {
				//获取当前阶段动效列表
				List<AttackMessage> attackMessageList = attackMessageMapper
						.selectBatchIds(dynamicStageBo.getAttackMessageIds());
				//有动效没有找到
				if(attackMessageList.size() < dynamicStageBo.getAttackMessageIds().size()){
					throw new BusinessException(SituationErrorCode.CANNOT_FIND_DYNAMIC);
				}
				for (String attackMessageId : dynamicStageBo.getAttackMessageIds()){
					AttackMessage attackMessage = new AttackMessage();
					//获取当前动效id对应的动效数据
					for (AttackMessage message : attackMessageList){
						if(message.getResourceId().equals(attackMessageId)){
							attackMessage = message;
							break;
						}
					}
					//拷贝动效数据
					AttackMessageReq attackMessageBo = BeanUtils.copy(attackMessage,
							AttackMessageReq.class);
					if (attackMessage.getMediaAnimation() == null) {
						//普通动效设置多媒体数
						MediaAnimation mediaAnimation = new MediaAnimation();
						List<MediaAnimation> emptyMediaAnimation = new ArrayList<>();
						emptyMediaAnimation.add(mediaAnimation);
						attackMessageBo.setMediaAnimationList(emptyMediaAnimation);
					} else {
						//多媒体动效设置动效数据
						attackMessageBo.setMediaAnimationList(JsonUtil.toJsonObjectArray(
								attackMessage.getMediaAnimation(), MediaAnimation.class));
					}
					attackMessageBoList.add(attackMessageBo);
					//设置动效数据
					dynamicStageRes.setAttackMessageReqList(attackMessageBoList);
				}
			} else {
				//设置动效数据
				dynamicStageRes.setAttackMessageReqList(attackMessageBoList);
			}
			//
			dynamicStageResList.add(dynamicStageRes);
		}
		//设置动效方案数据
		dynamicSchemeRes.setSchemeData(dynamicStageResList);
		return dynamicSchemeRes;
	}

	@Override
	public DynamicSchemeReq updateDynamicScheme(DynamicSchemeReq dynamicSchemeReq) {
		//拷贝数据
		DynamicScheme dynamicScheme = BeanUtils.copy(dynamicSchemeReq, DynamicScheme.class);
		//获取防御等级
		Integer defenseLevel = dynamicSchemeReq.getDefenseLevel();
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
					//如果除重之后的set大小较小则判为有重复的动效描述
					if (description.size() < dynamicStage.getAttackMessageReqList().size()) {
						throw new BusinessException(
								SituationErrorCode.DUPLICATED_DYNAMIC_DESCRIPTION);
					}
					for (AttackMessageReq attackMessageReq : dynamicStage
							.getAttackMessageReqList()) {
						//设置动效防御等级
//						attackMessageReq.setDefenseLevel(defenseLevel);
						// 设置多媒体的名称
//						if (attackMessageReq.getType()
//								.equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
//							for (MediaAnimation mediaAnimation : attackMessageReq
//									.getMediaAnimationList()) {
//								mediaAnimation.setName(
//										FileUtil.getFileName(mediaAnimation.getUrl(), true));
//							}
//						}
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
				//批量进行新增动效操作
				attackMessageService.bathAddAttackMessage(addAttackMessageReqList);
				//批量进行修改动效操作
				attackMessageService.bathUpdateAttackMessage(updateAttackMessageReqList);

				for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
					DynamicStageBo dynamicStageBo = new DynamicStageBo();
					//设置名称
					dynamicStageBo.setName(dynamicStage.getName());
					//设置动效id
					dynamicStageBo.setAttackMessageIds(dynamicStage.getAttackMessageReqList()
							.stream().map(AttackMessageReq::getResourceId)
							.collect(Collectors.toList()));
					dynamicStageBoList.add(dynamicStageBo);
				}
				//设置动效方案数据
				dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageBoList));
				//更新动效数据
				dynamicSchemeMapper.updateById(dynamicScheme);
			}
		});
		return dynamicSchemeReq;
	}

	@Override
	public void deleteDynamicScheme(String resourceId) {
		List<String> attackMessageIds = new ArrayList<>();
		//根据资源编号获取动效方案数据
		DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
		//解析动效方案数据
		List<DynamicStageBo> dynamicStageBoList = JsonUtil
				.toJsonObjectArray(dynamicScheme.getSchemeData(), DynamicStageBo.class);
		//获取动效id列表
		for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
			attackMessageIds.addAll(dynamicStageBo.getAttackMessageIds());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (attackMessageIds.size() > 0) {
					//批量删除动效
					attackMessageMapper.deleteBatchIds(attackMessageIds);
				}
				dynamicSchemeMapper.deleteById(resourceId);
			}
		});
	}

	@Override
	public List<DynamicSchemeNameRes> getAllDynamicName(String sceneId) {
		//获取当前场景下的动效方案名称
		return dynamicSchemeMapper.getAllNamesBySceneId(sceneId);
	}

	@Override
	public void getLocalOption(OptionsRes optionsRes) {
		//获取动效类型
		optionsRes.setAttackTypeList(StreamApiUtils.map(Lists.newArrayList(AttackType.values()),
				attackType -> new WebAttackType(attackType.getKeyWord(), attackType.getMsg(),
						attackType.getActionType().getType(), attackType.getActionType().isSource(),
						attackType.getActionType().isDurationTime())));
		//获取线型
		optionsRes.setLineTypeList(StreamApiUtils.map(Lists.newArrayList(LineType.values()),
				lineType -> new LocalType(lineType.getCode(), lineType.getMsg())));
		//获取颜色
		optionsRes.setColorList(StreamApiUtils.map(Lists.newArrayList(Color.values()),
				color -> new LocalType(color.getCode(), color.getMsg())));
		//获取防御类型
		optionsRes.setDefenseList(StreamApiUtils.map(Lists.newArrayList(DefenseType.values()),
				defenseType -> new LocalType(defenseType.getMsg(), defenseType.getKeyWord())));
	}

	@Override
	public void getRemoteOption(OptionsRes optionsRes, String sceneId, Boolean isInstance) {

		if (StringUtils.isEmpty(sceneId)) {
			throw new BusinessException(SituationErrorCode.UNAVAILABLE_FOR_NON_SCENE_SITUATION);
		}

		SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(sceneId);
		List<AreaInfo> areaInfoList = Lists.newArrayList(situationTopo.getAreas().values());
		List<ElementInfo> elementInfoList = Lists
				.newArrayList(situationTopo.getElements().values());
		//返回区域数据
		List<ElementType> areaTypeList = StreamApiUtils.map(areaInfoList, areaInfo ->
				new ElementType(areaInfo.getAreaId(), areaInfo.getName()));
		Collections.sort(areaTypeList);
		optionsRes.setAreaList(areaTypeList);
		//返回节点数据
		List<ElementType> elementTypeList = StreamApiUtils.map(elementInfoList,
				elementInfo -> new ElementType(elementInfo.getElementId(), elementInfo.getName()));
		Collections.sort(elementTypeList);
		optionsRes.setElementList(elementTypeList);
	}

	public DynamicScheme
			addDynamicSchemeReqToDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq) {
		DynamicScheme dynamicScheme = BeanUtils.copy(addDynamicSchemeReq, DynamicScheme.class);
		List<DynamicStageBo> dynamicStageList = new ArrayList<>();
		//设置动效方案数据
		dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageList));
		return dynamicScheme;
	}

	/**
	 * 推送多媒体动效消息
	 */
	@Override
	public void pushMediaToRoom(AttackMessageReq attackMessageBo, String code, Boolean playback) {
		WsAttackEffectDynamic wsAttackEffectDynamic = new WsAttackEffectDynamic();
		//设置场景编号
		wsAttackEffectDynamic.setSceneId(attackMessageBo.getSceneId());
		//设置事件类别
		wsAttackEffectDynamic.setCategory("attack");
		//设置事件描述
		wsAttackEffectDynamic.setDescription(attackMessageBo.getDescription());
		//设置事件名称
		wsAttackEffectDynamic.setName("攻击效果");
		//设置事件类型
		wsAttackEffectDynamic.setType(ActionType.NONE.getCode());
		List<AttackEffect> attackEffectList = new ArrayList<>();
		for (MediaAnimation mediaAnimation : attackMessageBo.getMediaAnimationList()) {
			//拷贝类型
			AttackEffect attackEffect = BeanUtils.copy(mediaAnimation, AttackEffect.class);
			//设置节点id
			attackEffect.setElementId(mediaAnimation.getSourceId());

			// 判断是图片还是视频
			String fileSuffix = FileUtil.getFileSuffix(mediaAnimation.getUrl()).toLowerCase();
			//获取图片后缀
			List<String> pictureTypeList = Arrays
					.asList(PICTURE_TYPE.toLowerCase().split(CommonConstant.CODE_COMMA));
			//获取视频后缀
			List<String> videoTypeList = Arrays
					.asList(VIDEO_TYPE.toLowerCase().split(CommonConstant.CODE_COMMA));
			if (pictureTypeList.contains(fileSuffix)) {
				//设置多媒体动效类型为图片
				attackEffect.setMediaType(PICTURE_CODE);
			} else if (videoTypeList.contains(fileSuffix)) {
				//设置多媒体类型为视频
				attackEffect.setMediaType(VIDEO_CODE);
			} else {
				//不合法的文件类型
				throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
			}
			//设置多媒体路径
			attackEffect.setMediaUrl(mediaAnimation.getUrl());
			//设置线的颜色
			attackEffect.setLineStyle(new LineStyle("#FFFFFF", 3, "straight"));
			attackEffectList.add(attackEffect);
		}
		//设置动效列表
		wsAttackEffectDynamic.setEffectList(attackEffectList);
		//构造消息数据
		WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(
				MessageType.AttackEffect.getDesc(), wsAttackEffectDynamic);
		//推送动效数据
		situationHandlerService.pushDataToRoom(wsMessage, attackMessageBo.getSceneId());
		if(!playback){
			//如果不是回放则会增加一条动效历史
			attackMessageBo.setTargetId(null);
			attackMessageHistoryService.add(attackMessageBo);
		}

		Thread thread = new Thread(new Runnable() {

			@SneakyThrows
			@Override
			public void run() {
				// 持续时间结束后清除该多媒体动效
				Thread.sleep(attackMessageBo.getDurationTime() * 1000);
				// 判断当前是否有其他动效正在运行
				if (sceneConfigService.isCurrentScheme(attackMessageBo.getSceneId(), code)) {
					//清除多媒体动效
					clearMedia(attackMessageBo.getSceneId());
				}
			}
		});
		thread.start();

	}

	/**
	 * 推送普通动效数据至房间
	 * @param attackMessageReq 普通动效数据
	 * @param elementIds 当前场景节点id列表
	 * @param playback 是否为回放
	 */
	public void pushAttackToRoom(AttackMessageReq attackMessageReq, List<String> elementIds, Boolean playback) {
		//构造消息数据
		WsMultiNodeDynamic wsMultiNodeDynamic = BeanUtils.copy(attackMessageReq,
				WsMultiNodeDynamic.class);
		//设置消息类别
		wsMultiNodeDynamic.setCategory("attack");
		for (AttackType attackType : AttackType.values()) {
			if (attackType.getKeyWord().equals(attackMessageReq.getType())) {
				wsMultiNodeDynamic.setType(attackType.getActionType().getCode());
			}
		}
		if (wsMultiNodeDynamic.getType() == null) {
			wsMultiNodeDynamic.setType(ActionType.NONE.getCode());
		}
		wsMultiNodeDynamic.setName(null);
		//设置线的格式
		wsMultiNodeDynamic.setLineStyle(new LineStyle(attackMessageReq.getLineColor(),
				attackMessageReq.getLineWidth(), attackMessageReq.getLineType()));
		//设置持续时间
		wsMultiNodeDynamic.setDuration(attackMessageReq.getDurationTime());
		WsMessage<WsMultiNodeDynamic> wsMessage;

		if (elementIds.contains(attackMessageReq.getTargetId())) {
			//设置类型为点对点
			wsMessage = new WsMessage<>(MessageType.AttackEventEE.getDesc(), wsMultiNodeDynamic);
		} else {
			//设置类型为点对区域
			wsMessage = new WsMessage<>(MessageType.AttackEventEA.getDesc(), wsMultiNodeDynamic);
		}
		pushDataToRoom(wsMessage, attackMessageReq, playback);


		//推送攻击弹窗
		WsAttackInfo attackInfo = new WsAttackInfo();
		SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(attackMessageReq.getSceneId());
		Map<String, ElementInfo> elementInfoMap = situationTopo.getElements();
		attackInfo.setSourceId(attackMessageReq.getSourceId());
		attackInfo.setDesc(attackMessageReq.getDescription());
		attackInfo.setSourceName(elementInfoMap.get(attackMessageReq.getSourceId()).getName());
		attackInfo.setTargetName(elementInfoMap.get(attackMessageReq.getTargetId()).getName());
		attackInfo.setAttackName(AttackType.getEnumByKeyWord(attackMessageReq.getType(), AttackType.class).getMsg());
		attackInfo.setLeak(attackMessageReq.getLeak());
		attackInfo.setTool(attackMessageReq.getTool());
		//校验漏洞是否匹配
		List<String> leaks = elementInfoMap.get(attackMessageReq.getTargetId()).getLeakInfos().stream().map(LeakInfo::getName).collect(Collectors.toList());
		if (leaks.contains(attackMessageReq.getLeak())) {
			attackInfo.setTargetId(attackMessageReq.getTargetId());
			attackInfo.setResult(AttackType.getEnumByKeyWord(attackMessageReq.getType(), AttackType.class).getSuccessDesc());
		}
		WsMessage<WsAttackInfo> attackInfoWsMessage = new WsMessage<>(MessageType.AttackInfo.getDesc(), attackInfo);
		situationHandlerService.pushDataToRoom(attackInfoWsMessage, attackMessageReq.getSceneId());
	}

	@Override
	public void pushDynamicSchemeToRoom(DynamicSchemeReq dynamicSchemeReq, String stageName, String attackMessageDescription) {
		//获取场景编号
		String sceneId = dynamicSchemeReq.getSceneId();
		//更新场景code
		String code = sceneConfigService.updateSceneCode(sceneId);
		//获取节点id列表
		List<String> elementIds = getElementIds(sceneId, dynamicSchemeReq.getIsInstance());
		Integer defenseLevel = dynamicSchemeReq.getDefenseLevel();

		Thread thread = new Thread(new Runnable() {

			@SneakyThrows
			@Override
			public void run() {
				// 进程信息
				List<WsAttackSchedule> wsAttackScheduleList = new ArrayList<>();
				// 当前动效方案是否正常运行
				boolean state = true;
				// 当前动效是否已运行过
				boolean attackMessageStatue = true;
				for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
					if (!state) {
						break;
					}
					//进程消息中新增当前阶段信息
					WsAttackSchedule wsAttackScheduleStage = new WsAttackSchedule(
							dynamicStage.getName(), STAGE_IS_RUNNING);
					wsAttackScheduleList.add(wsAttackScheduleStage);
					WsAttackSchedule wsAttackMessageSchedule = new WsAttackSchedule("",
							DYNAMIC_IS_RUNNING);
					wsAttackScheduleList.add(wsAttackMessageSchedule);

					//判断当前阶段是否已运行
					if(attackMessageStatue && stageName != null && !dynamicStage.getName().equals(stageName)){
						//当前阶段已运行，设置当前阶段为已运行状态
						wsAttackScheduleStage.setStatus(STAGE_HAS_BEEN_RUN);
						wsAttackMessageSchedule.setStatus(DYNAMIC_HAS_BEEN_RUN);
						wsAttackMessageSchedule.setName("");
						//当前阶段已运行，直接进行下一次循环
						continue;
					}

					// 动效序号
					int number = 0;

					for (AttackMessageReq attackMessageReq : dynamicStage
							.getAttackMessageReqList()) {
						//判断当前动效是否已运行
						if(attackMessageStatue && attackMessageDescription != null && !attackMessageReq.getDescription().equals(attackMessageDescription)){
							number++;
							//当前动效已运行，直接进行下一次循环
							continue;
						}
						number ++;
						//当前动效未运行，表示找到继续的位置，之后运行无需再次判断
						attackMessageStatue = false;

						attackMessageReq.setSceneId(sceneId);
						attackMessageReq.setDefenseLevel(defenseLevel);
						// 查看当前是否有其他动效方案正在运行
						if (!sceneConfigService.isCurrentScheme(sceneId, code)) {
							// 有其他方案正在运行或当前场景动效方案被暂停，当前方案停止
							state = false;
							// 如果当前场景动效方案被暂停，保存当前动效方案数据和运行进度
							if(sceneConfigService.getCurrentScheme(sceneId) == null){
								DynamicSchemeProcess dynamicSchemeProcess = new DynamicSchemeProcess();
								//设置动效方案数据
								dynamicSchemeProcess.setDynamicSchemeReq(dynamicSchemeReq);
								//设置阶段数据
								dynamicSchemeProcess.setStageName(dynamicStage.getName());
								//设置动效描述
								dynamicSchemeProcess.setAttackMessageDescription(attackMessageReq.getDescription());
								//保存场景数据和进程信息
								sceneConfigService.setDynamicSchemeCache(sceneId, dynamicSchemeProcess);
							}
							break;
						}
						//设置阶段名称
						wsAttackMessageSchedule
								.setName(number + "." + attackMessageReq.getDescription());
						//推送进程数据
						pushAttackScheduleToRoom(wsAttackScheduleList, sceneId);
						sceneConfigService.updateScheduleById(sceneId,
								JSONObject.toJSONString(wsAttackScheduleList));

//						if (attackMessageReq.getType()
//								.equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
							//推送多媒体动效数据
//							pushMediaToRoom(attackMessageReq, code, false);
//						} else {
							//推送普通动效数据
						pushDynamicToRoom(attackMessageReq, elementIds, false);
//						}
						if (attackMessageReq.getIntervalTime() != null) {
							//进行等待
							Thread.sleep(attackMessageReq.getIntervalTime() * 1000);
						}

					}
					if (state && sceneConfigService.isCurrentScheme(sceneId, code)) {
						//设置阶段状态
						wsAttackScheduleStage.setStatus(STAGE_HAS_BEEN_RUN);
						//设置动效状态
						wsAttackMessageSchedule.setStatus(DYNAMIC_HAS_BEEN_RUN);
						//设置动效名称
						wsAttackMessageSchedule.setName("");
						//推送进程数据
						pushAttackScheduleToRoom(wsAttackScheduleList, sceneId);
						//设置动效方案进程信息
						sceneConfigService.updateScheduleById(sceneId,
								JSONObject.toJSONString(wsAttackScheduleList));
						//清除当前场景动效进程
						sceneConfigService.setDynamicSchemeCache(sceneId, null);
					}
				}

				if(state && sceneConfigService.isCurrentScheme(sceneId, code)){
					//清除当前场景进程数据
					sceneConfigService.updateScheduleById(sceneId,
							JSONObject.toJSONString(Lists.newArrayList()));
				}

			}
		});
		thread.start();

	}

	/**
	 * 校验动效方案是否重名
	 */
	@Override
	public void validDynamicSchemeName(AddDynamicSchemeReq addDynamicSchemeReq) {
		//校验名称长度
		validateNameLen(addDynamicSchemeReq.getName(),
				SituationErrorCode.INVALID_DYNAMIC_SCHEME_NAME);
		//根据方案名称获取动效方案
		DynamicScheme dynamicScheme = dynamicSchemeMapper
				.selectByName(addDynamicSchemeReq.getName(), addDynamicSchemeReq.getSceneId());
		//如果获取到动效则说明重名
		if (dynamicScheme != null) {
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
				throw new BusinessException(SituationErrorCode.EMPTY_STAGE_NAME);
			}
			validateNameLen(dynamicStage.getName(), SituationErrorCode.INVALID_STAGE_NAME);
			for (AttackMessageReq attackMessageReq : dynamicStage.getAttackMessageReqList()) {
				validateNameLen(attackMessageReq.getDescription(),
						SituationErrorCode.INVALID_DYNAMIC_DESCRIPTION);
				if ("defense".equals(attackMessageReq.getAttackOrDefense())) {
					if (StringUtils.isEmpty(attackMessageReq.getDefenseTargetId())) {
						throw new BusinessException(SituationErrorCode.EMPTY_DEFENSE_NODE,
								attackMessageReq.getDescription());
					}
				}else {
					if (StringUtils.isEmpty(attackMessageReq.getSourceId())) {
						throw new BusinessException(SituationErrorCode.EMPTY_SOURCE_NODE,
								attackMessageReq.getDescription());
					}
					if (StringUtils.isEmpty(attackMessageReq.getTargetId())) {
						throw new BusinessException(SituationErrorCode.EMPTY_TARGET_NODE,
								attackMessageReq.getDescription());
					}
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
//				if (attackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
//					for (MediaAnimation mediaAnimation : attackMessageReq.getMediaAnimationList()) {
//						if (!areaAndElementIds.contains(mediaAnimation.getSourceId())) {
//							throw new BusinessException(SituationErrorCode.SOME_NODES_NOT_EXIST);
//						}
//					}
//				} else {
					if (StringUtils.isNotEmpty(attackMessageReq.getTargetId())
							&& !areaAndElementIds.contains(attackMessageReq.getTargetId())) {
						throw new BusinessException(SituationErrorCode.SOME_NODES_NOT_EXIST);
					}
					if (StringUtils.isNotEmpty(attackMessageReq.getSourceId())
							&& !areaAndElementIds.contains(attackMessageReq.getSourceId())) {
						throw new BusinessException(SituationErrorCode.SOME_NODES_NOT_EXIST);
					}
//				}
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
//		int startTime = 0;
//		int endTime = -1;
//		for (DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()) {
//			for (AttackMessageReq attackMessageReq : dynamicStage.getAttackMessageReqList()) {
//				if (attackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())) {
//					if (startTime <= endTime) {
//						throw new BusinessException(
//								SituationErrorCode.MULTIMEDIA_MEDIA_ANIMATION_HAVE_TIME_OVERLAP);
//					}
//					endTime = startTime + attackMessageReq.getDurationTime();
//				}
//				startTime += attackMessageReq.getIntervalTime();
//			}
//		}
	}

	@Override
	public List<String> getElementIds(String sceneId, Boolean isInstance) {

		SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(sceneId);
		List<ElementInfo> elementInfoList = Lists
				.newArrayList(situationTopo.getElements().values());
		return elementInfoList.stream().map(ElementInfo::getElementId).collect(Collectors.toList());
	}

	@Override
	public void playbackPushDynamicSchemeToRoom(String sceneId, Date startTime, Date endTime){
		//获取动效历史
		List<AttackMessageHistory> attackMessageHistoryList = attackMessageHistoryMapper.getAttackHistoryByTime(sceneId, startTime, endTime);
		//更新场景编号
		String code = sceneConfigService.updateSceneCode(sceneId);
		//获取节点id列表
		List<String> elementIds = getElementIds(sceneId, false);

		Thread thread = new Thread(new Runnable() {

			@SneakyThrows
			@Override
			public void run() {
				for(AttackMessageHistory attackMessageHistory: attackMessageHistoryList){
					//如果当前场景有其他动效方案正在运行，回放终止
					if(!sceneConfigService.isCurrentScheme(sceneId, code)){
						break;
					}
					pushDynamicToRoom(BeanUtils.copy(attackMessageHistory, AttackMessageReq.class), elementIds, true);
					pushPlaybackScheduleToRoom(new AttackMessageHistoryRes(
							TimeUtil.format1(attackMessageHistory.getCreateTime()), attackMessageHistory.getDescription(), attackMessageHistory.getAttackOrDefense()),
							attackMessageHistory.getSceneId());
					if (attackMessageHistory.getIntervalTime() != null) {
						//进行等待
						Thread.sleep(attackMessageHistory.getIntervalTime() * 1000);
					}

				}
				if (sceneConfigService.isCurrentScheme(sceneId, code)){
					//回放结束
					pushPlaybackScheduleToRoom(new AttackMessageHistoryRes(), sceneId);
				}

			}
		});
		thread.start();

	}

	@Override
	public void pushDataToRealisticScene(String topoId, Integer step){
		WsRealisticSceneDynamic wsRealisticSceneDynamic = new WsRealisticSceneDynamic();
		wsRealisticSceneDynamic.setStep(step);
		situationHandlerService.pushDataToRealisticScene(topoId, wsRealisticSceneDynamic);
	}

	@Override
	public void pushDynamicToRoom(AttackMessageReq attackMessageBo, List<String> elementIds, Boolean playback){
		if ("defense".equals(attackMessageBo.getAttackOrDefense())) {
			pushDefenseToRoom(attackMessageBo, playback);
		} else {
			pushAttackToRoom(attackMessageBo, elementIds, playback);
		}
	}

	public void pushDefenseToRoom(AttackMessageReq attackMessageReq, Boolean playback) {
		WsDefenseDynamic wsDefenseDynamic = BeanUtils.copy(attackMessageReq, WsDefenseDynamic.class);
		wsDefenseDynamic.setDefenseType(
				DefenseType.getEnumByKeyWord(attackMessageReq.getDefenseType(), DefenseType.class)
						   .getDefenseActionType().getCode());
		wsDefenseDynamic.setDuration(attackMessageReq.getDurationTime());
		WsMessage<WsDefenseDynamic> wsMessage = new WsMessage<>(MessageType.DefenseDynamic.getDesc(), wsDefenseDynamic);
		pushDataToRoom(wsMessage, attackMessageReq, playback);


		//推送防御弹窗
		WsDefenseInfo wsDefenseInfo = new WsDefenseInfo();
		wsDefenseInfo.setTargetId(attackMessageReq.getDefenseTargetId());
		wsDefenseInfo.setDesc(attackMessageReq.getDescription());
		wsDefenseInfo.setDefenseName(DefenseType.getEnumByKeyWord(attackMessageReq.getDefenseType(), DefenseType.class).getMsg());
		wsDefenseInfo.setTool(attackMessageReq.getTool());
		wsDefenseInfo.setLeak(attackMessageReq.getLeak());
		wsDefenseInfo.setResult(DefenseType.getEnumByKeyWord(attackMessageReq.getDefenseType(), DefenseType.class).getSuccessDesc());
		WsMessage<WsDefenseInfo> defenseInfoWsMessage = new WsMessage<>(MessageType.DefenseInfo.getDesc(), wsDefenseInfo);
		situationHandlerService.pushDataToRoom(defenseInfoWsMessage, attackMessageReq.getSceneId());
	}

	private <T> void pushDataToRoom(T data, AttackMessageReq attackMessageReq, Boolean playback) {
		situationHandlerService.pushDataToRoom(data, attackMessageReq.getSceneId());
		if (attackMessageReq.getStep() != null) {
			WsRealisticSceneDynamic wsRealisticSceneDynamic = new WsRealisticSceneDynamic();
			wsRealisticSceneDynamic.setStep(attackMessageReq.getStep());
			situationHandlerService.pushDataToRealisticScene(attackMessageReq.getSceneId(), wsRealisticSceneDynamic);
		}
		if(!playback){
			//如果不是回放则会增加一条动效历史
			attackMessageHistoryService.add(attackMessageReq);
		}
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

	/**
	 * 推送回放进信息
	 * @param attackMessageHistoryRes 回放进程信息
	 * @param sceneId 场景id
	 */
	public void pushPlaybackScheduleToRoom(AttackMessageHistoryRes attackMessageHistoryRes,
			String sceneId){
		WsMessage<AttackMessageHistoryRes> wsMessage = new WsMessage<>(
				MessageType.PlaybackSchedule.getDesc(), attackMessageHistoryRes);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
	}

	public void validateLength(String target, Integer minLen, Integer maxLen, String errorCode) {
		if (target == null) {
			if (minLen == 0) {
				return;
			} else {
				throw new BusinessException(errorCode, minLen, maxLen);
			}
		}
		try {
			String targetStr = new String(target.getBytes("GBK"), "iso-8859-1");
			int len = targetStr.length();
			if (len < minLen || len > maxLen) {
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
