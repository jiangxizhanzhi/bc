package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.impl.RemoteTerminalServiceImpl;
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
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.*;
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

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private SituationTimeService situationTimeService;

	@Autowired
	private SituationTimeMapper situationTimeMapper;

	@Autowired
	private RemoteTerminalServiceImpl remoteTerminalService;

	@Autowired
	private SituationHandlerServiceImpl situationHandlerServiceImpl;

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
		List<WebAttackType> webAttackTypes = new ArrayList<>();
		List<WebAttackType> map = StreamApiUtils.map(Lists.newArrayList(AttackType.values()),
				attackType -> new WebAttackType(attackType.getKeyWord(), attackType.getMsg(),
						attackType.getActionType().getType(), attackType.getActionType().isSource(),
						attackType.getActionType().isDurationTime(), attackType.getLeak(), attackType.getTool()));
		webAttackTypes.addAll(map);
		// 特殊选择处理
		webAttackTypes.add(new WebAttackType("ddos", "拒绝服务", "element", true, true, "僵尸网络内攻击者可以控制受害机发送大量数据包，形成拒绝服务攻击", "LOIC"));
		webAttackTypes.add(new WebAttackType("exploit", "漏洞利用", "element", true, true, "CVE-2021-44228+脏牛提权，Apache Log4j远程代码执行漏洞", "Exp"));
		webAttackTypes.add(new WebAttackType("host-attack", "主机工具", "element", true, true, "对主机进行攻击", "LOIC"));
		webAttackTypes.add(new WebAttackType("lateral-movement", "横向移动", "element", true, true, "MES应用服务器对统一安全管理平台发起横向移动", "LOIC"));
		webAttackTypes.add(new WebAttackType("leveraging-remote-services", "远程服务", "element", true, true, "OwnCloud主机对MES应用服务器发起利用远程服务", "LOIC"));
		webAttackTypes.add(new WebAttackType("network-scanning", "网络扫描", "element", true, true, "外部攻击机对WEB服务器发起网络扫描", "LOIC"));
		webAttackTypes.add(new WebAttackType("trojan", "木马攻击", "element", true, true, "统一安全管理平台对工程师站发起木马攻击", "LOIC"));

		optionsRes.setAttackTypeList(webAttackTypes);
		//获取线型
		optionsRes.setLineTypeList(StreamApiUtils.map(Lists.newArrayList(LineType.values()),
				lineType -> new LocalType(lineType.getCode(), lineType.getMsg())));
		//获取颜色
		optionsRes.setColorList(StreamApiUtils.map(Lists.newArrayList(Color.values()),
				color -> new LocalType(color.getCode(), color.getMsg())));
		//获取防御类型
		optionsRes.setDefenseList(StreamApiUtils.map(Lists.newArrayList(DefenseType.values()),
				defenseType -> new WebDefenseType(defenseType.getMsg(), defenseType.getKeyWord(),
						defenseType.getTool())));
		//获取工具列表
		List<String> attackTools = Arrays.stream(AttackType.values())
										 .map(AttackType::getTool)
										 .collect(Collectors.toList());
		List<String> defenseTools = Arrays.stream(DefenseType.values())
										  .map(DefenseType::getTool)
										  .collect(Collectors.toList());
		attackTools.addAll(defenseTools);
		attackTools = new ArrayList<>(new HashSet<>(attackTools));
		Collections.sort(attackTools);
		optionsRes.setTools(attackTools);

		List<String> leaks = Arrays.stream(AttackType.values())
								   .map(AttackType::getLeak)
								   .distinct()
								   .collect(Collectors.toList());
		Collections.sort(leaks);
		optionsRes.setLeaks(leaks);
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
//		List<String> leaks = remoteTopoService.getAllLeak().toJavaList(String.class);
//		Collections.sort(leaks);
//		optionsRes.setLeaks(leaks);
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
	 * 推送普通动效数据至房间
	 * @param attackMessageReq 普通动效数据
	 * @param elementIds 当前场景节点id列表
	 * @param playback 是否为回放
	 * @param isMultiterm 是否多数据推送
	 */
	public void pushAttackToRoom(AttackMessageReq attackMessageReq, List<String> elementIds, Boolean playback, Boolean isMultiterm) {
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

		wsMultiNodeDynamic.setIsSucceed(0);

		WsMessage<WsMultiNodeDynamic> wsMessage;

		WsCvss wsCvss = new WsCvss();
		wsCvss.setNodeId(attackMessageReq.getTargetId());
		wsCvss.setFlag(1);
		wsCvss.setCvss(0.0);

		// 多任务推送判断该节点是否有加固记录
		List<Defense> defenses = dynamicSchemeMapper.queryDefenseByNodeId(attackMessageReq.getSceneId(), attackMessageReq.getTargetId(), 1);
		// 判断当前任务是否有工具和漏洞信息
		if (attackMessageReq.getLeak() != null && attackMessageReq.getTool() != null) {
			// 有信息查询工具信息
			List<ToolVuln> toolVuln = dynamicSchemeMapper.toolVulnById(attackMessageReq.getTool(), attackMessageReq.getLeak(), 0);
			// 查询漏洞信息
			List<Vuln> vulnList = dynamicSchemeMapper.queryLeak();
			Map<String, Double> cvssMap = vulnList.stream().collect(Collectors.toMap(Vuln::getResourceId, Vuln::getCvss));
			if (isMultiterm) {
				boolean isDefen = false;
				for (Defense defens : defenses) {
					if (attackMessageReq.getTargetId().equals(defens.getNodeId()) && attackMessageReq.getLeak().equals(defens.getVulnId())) {
						// 防御记录中有该节点上该漏洞的防御记录，进攻失败
						wsMultiNodeDynamic.setIsSucceed(0);
						isDefen = true;
						break;
					}
				}
				if (!isDefen && toolVuln != null && toolVuln.size() != 0) {
					// 没有该节点的防御记录并且工具信息中有改漏洞攻击成功
					wsMultiNodeDynamic.setIsSucceed(1);

					Defense defense = new Defense();
					defense.setResourceId(IDGeneratorUtil.getID());
					defense.setSceneId(attackMessageReq.getSceneId());
					defense.setNodeId(attackMessageReq.getTargetId());
					defense.setVulnId(attackMessageReq.getLeak());
					defense.setDeacription(toolVuln.get(0).getDescribe());
					defense.setType(0);
					defense.setCreateTime(new Date());
					// 记录防御成功数据
					dynamicSchemeMapper.addDefense(defense);

					wsCvss.setFlag(3);
					wsCvss.setCvss(cvssMap.get(attackMessageReq.getLeak()));
				} else {
					wsMultiNodeDynamic.setIsSucceed(0);

					// 处理动作后的节点状态
					Integer nodeStatus = situationHandlerService.getNodeStatus(attackMessageReq.getSceneId(), attackMessageReq.getTargetId(), null);
					wsCvss.setFlag(nodeStatus);
					wsCvss.setCvss(cvssMap.get(attackMessageReq.getLeak()));
				}
			} else {
				if (toolVuln != null) {
					// 查到工具信息，攻击成功
					wsMultiNodeDynamic.setIsSucceed(1);

					wsCvss.setFlag(3);
					wsCvss.setCvss(cvssMap.get(attackMessageReq.getLeak()));
				} else {
					// 查到工具信息，防御成功
					wsMultiNodeDynamic.setIsSucceed(0);
				}
			}
		} else {
			wsMultiNodeDynamic.setIsSucceed(0);
		}

		if (elementIds.contains(attackMessageReq.getTargetId())) {
			//设置类型为点对点
			wsMessage = new WsMessage<>(MessageType.AttackEventEE.getDesc(), wsMultiNodeDynamic);
		} else {
			//设置类型为点对区域
			wsMessage = new WsMessage<>(MessageType.AttackEventEA.getDesc(), wsMultiNodeDynamic);
		}

		String imageUuid = remoteTerminalService.queryImageId(attackMessageReq.getTargetId());
		if (imageUuid != null) {
			// 获取设备信息
			DeviceInfo deviceInfo = remoteTerminalService.getById(imageUuid);
			if (deviceInfo != null && deviceInfo.getVulns() != null && deviceInfo.getVulns().size() != 0) {
				List<String> vulns = deviceInfo.getVulns();
				List<Vuln> leaks = dynamicSchemeMapper.queryLeakByIds(vulns);
				Double cvssAll = 0.0;
				Double cvssBll = 0.0;
				List<Vuln> we = BeanUtils.deepCopyList(leaks, Vuln.class);
				for (Vuln vuln : leaks) {
					cvssAll = cvssAll + vuln.getCvss();
					for (Defense defens : defenses) {
						if (vuln.getResourceId().equals(defens.getVulnId())) {
							we.remove(vuln);
//							cvssBll = cvssBll + vuln.getCvss();
							break;
						}
					}
				}
				for (Vuln vuln : we) {
					cvssBll = cvssBll + vuln.getCvss();
				}
				Double cvss = cvssBll/cvssAll*10;
				String str = String.format("%.2f", cvss);
				wsCvss.setCvss(Double.parseDouble(str));
			}
		}

		WsMessage<WsCvss> wsCvssWsMessage = new WsMessage<>(MessageType.Cvss.getDesc(), wsCvss);
		pushDataToRoom(wsCvssWsMessage, attackMessageReq, playback, null, false);

		//推送攻击弹窗
		WsAttackInfo attackInfo = new WsAttackInfo();
		SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(attackMessageReq.getSceneId());
		Map<String, ElementInfo> elementInfoMap = situationTopo.getElements();
		attackInfo.setSourceId(attackMessageReq.getSourceId());
		attackInfo.setDesc(attackMessageReq.getDescription());
		attackInfo.setSourceName(elementInfoMap.get(attackMessageReq.getSourceId()).getName());
		attackInfo.setTargetName(elementInfoMap.get(attackMessageReq.getTargetId()).getName());
		AttackType attackType = AttackType.getEnumByKeyWord(attackMessageReq.getType(), AttackType.class);
		if (attackType != null) {
			attackInfo.setAttackName(attackType.getMsg());
		}
		if (attackMessageReq.getTool() != null) {
			ToolVuln toolVuln = dynamicSchemeMapper.queryToolById(attackMessageReq.getTool());
			if (toolVuln != null) {
				attackInfo.setTool(toolVuln.getToolName());
			}
		}

		if (attackMessageReq.getLeak() != null) {
			Vuln vuln = dynamicSchemeMapper.queryById(attackMessageReq.getLeak());
			if (vuln != null) {
				attackInfo.setLeak(vuln.getName());
			}
		}
		attackInfo.setDuration(attackMessageReq.getDurationTime());
		//校验漏洞是否匹配
		List<String> leaks = elementInfoMap.get(attackMessageReq.getTargetId()).getLeakInfos().stream().map(LeakInfo::getName).collect(Collectors.toList());
		if (leaks.contains(attackMessageReq.getLeak())) {
			attackInfo.setTargetId(attackMessageReq.getTargetId());
			attackType = AttackType.getEnumByKeyWord(attackMessageReq.getType(), AttackType.class);
			if (attackType != null) {
				attackInfo.setResult(attackType.getSuccessDesc());
			}

			pushDataToRoom(wsMessage, attackMessageReq, playback, true, true);
		} else {
			pushDataToRoom(wsMessage, attackMessageReq, playback, false, true);
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
						pushDynamicToRoom(attackMessageReq, elementIds, false, true);
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
				// 清空当前场景攻防记录
				dynamicSchemeMapper.deleteDefenseBySceneId(sceneId);
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

		// 清空当前场景防御记录
		dynamicSchemeMapper.deleteDefenseBySceneId(sceneId);
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
					pushDynamicToRoom(BeanUtils.copy(attackMessageHistory, AttackMessageReq.class), elementIds, true, false);
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
	public void pushDynamicToRoom(AttackMessageReq attackMessageBo, List<String> elementIds, Boolean playback, Boolean isMultiterm){
		situationTimeService.addRunTime(attackMessageBo.getSceneId(), attackMessageBo.getDurationTime());
		if ("defense".equals(attackMessageBo.getAttackOrDefense())) {
			// 防御动效推送
			pushDefenseToRoom(attackMessageBo, playback, isMultiterm);
		} else {
			// 攻击动效推送
			pushAttackToRoom(attackMessageBo, elementIds, playback, isMultiterm);
		}
	}

	@Override
	public void executeInstruction(Instruction instruction, String topoId) {
		situationTimeService.valid(topoId);
		SituationTime situationTime = situationTimeMapper.selectByTopoId(topoId);
		situationTimeService.handleSituationTime(situationTime);
		if (situationTime.getStatus().equals(SituationTime.Status.FINISH.getCode())) {
			throw new BusinessException(SituationErrorCode.THIS_SCENE_HAS_ENDED);
		}
		if (instruction.getCode().equals(Instruction.InstructionType.START.getCode())) {
			situationTime.setStatus(SituationTime.Status.RUNNING.getCode());
			situationTime.setStartTime(new Date());
			if (instruction.getCombatTime() != null) {
				situationTime.setCombatTime(instruction.getCombatTime());
			}
			if (instruction.getAstronomicalTime() != null) {
				situationTime.setAstronomicalTime(instruction.getAstronomicalTime());
			}
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.FINISH.getCode())) {
			situationTime.setStatus(SituationTime.Status.FINISH.getCode());
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.PAUSE.getCode())) {
			situationTime.setStatus(SituationTime.Status.PAUSE.getCode());
			situationTimeMapper.updateById(situationTime);
			sceneConfigService.clearSceneCode(topoId);
		} else if (instruction.getCode().equals(Instruction.InstructionType.CONTINUE.getCode())) {
			DynamicSchemeProcess dynamicSchemeProcess = sceneConfigService.getDynamicSchemeCache(topoId);
			if (dynamicSchemeProcess != null) {
				pushDynamicSchemeToRoom(dynamicSchemeProcess.getDynamicSchemeReq(),
						dynamicSchemeProcess.getStageName(), dynamicSchemeProcess.getAttackMessageDescription());
			}
			situationTime.setStatus(SituationTime.Status.RUNNING.getCode());
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.RATE.getCode())) {
			situationTime.setRate(instruction.getRate());
			situationTimeMapper.updateById(situationTime);
		} else if (instruction.getCode().equals(Instruction.InstructionType.ADJUST.getCode())) {
			if (instruction.getAstronomicalTime() != null) {
				situationTime.setAstronomicalTime(instruction.getAstronomicalTime());
			}
			if (instruction.getCombatTime() != null) {
				situationTime.setCombatTime(instruction.getCombatTime());
			}
			situationTimeMapper.updateById(situationTime);
		}
		WsMessage<SituationTime> wsMessage = new WsMessage<>(MessageType.SituationTime.getDesc(), situationTime);
		situationHandlerService.pushDataToRoom(wsMessage, topoId);
	}

	@Override
	public List<Vuln> queryLeak(String application, String nodeId) {
		if (nodeId != null) {
			String imageUuid = remoteTerminalService.queryImageId(nodeId);
			if (imageUuid == null) {
				return null;
			}
			String name = null;
			// 判断设备是否为常用靶标
			if (imageUuid.equals("5dfe83ac-4689-478d-8257-89bb118ef105")) {
				name = "外部网络";
			} else if (imageUuid.equals("1e64d97a-f0b3-4704-be21-8121a9b3ef8f")) {
				name = "NPC";
			} else if (imageUuid.equals("0e62a73f-a757-402e-8567-77a16b2e7835")) {
				name = "路由器";
			} else if (imageUuid.equals("66e6244c-90d1-48d6-a615-0762af79e4f2")) {
				name = "交换机";
			} else if (imageUuid.equals("854f7c24-060f-4dbd-b771-68eab207e071")) {
				name = "路由器";
			} else if (imageUuid.equals("c12b7b53-e232-40f4-b7c3-d3dfac8e1b31")) {
				name = "防火墙";
			}

			if (name != null) {
				List<Vuln> leaks = dynamicSchemeMapper.queryLeak();
				List<Vuln> leakList = new ArrayList<>();
				for (Vuln leak : leaks) {
					if (leak.getApplication().toLowerCase().contains(name.toLowerCase())) {
						leakList.add(leak);
					}
				}
				return leakList;
			}

			// 获取设备信息
			DeviceInfo deviceInfo = remoteTerminalService.getById(imageUuid);
			if (deviceInfo == null || deviceInfo.getVulns() == null || deviceInfo.getVulns().size() == 0) {
				return null;
			}
			List<String> vulns = deviceInfo.getVulns();
			List<Vuln> leaks = dynamicSchemeMapper.queryLeakByIds(vulns);
			return leaks;
		} else {
			List<Vuln> leaks = dynamicSchemeMapper.queryLeak();

			if (application != null) {
				List<Vuln> leakList = new ArrayList<>();
				for (Vuln leak : leaks) {
					if (application.toLowerCase().contains(leak.getApplication().toLowerCase())) {
						leakList.add(leak);
					}
				}
				return leakList;
			}

			return leaks;
		}
	}

	@Override
	public List<ToolVuln> queryTool(String vulnId, Integer toolType) {
		List<ToolVuln> toolVulns = dynamicSchemeMapper.queryToolByVulnId(vulnId, toolType);
		return toolVulns;
	}

	@Override
	public VulnTop queryVulnTop(String sceneId, String nodeId, String elementId) {
		// 获取设备信息
		DeviceInfo deviceInfo = new DeviceInfo();
		String name = null;
		if (nodeId.equals("5dfe83ac-4689-478d-8257-89bb118ef105")) {
			name = "外部网络";
		} else if (nodeId.equals("1e64d97a-f0b3-4704-be21-8121a9b3ef8f")) {
			name = "NPC";
		} else if (nodeId.equals("0e62a73f-a757-402e-8567-77a16b2e7835")) {
			name = "路由器";
		} else if (nodeId.equals("66e6244c-90d1-48d6-a615-0762af79e4f2")) {
			name = "交换机";
		} else if (nodeId.equals("854f7c24-060f-4dbd-b771-68eab207e071")) {
			name = "路由器";
		} else if (nodeId.equals("c12b7b53-e232-40f4-b7c3-d3dfac8e1b31")) {
			name = "防火墙";
		}

		if (name != null) {
			List<Vuln> leaks = dynamicSchemeMapper.queryLeak();
			List<String> leakList = new ArrayList<>();
			for (Vuln leak : leaks) {
				if (leak.getApplication().toLowerCase().contains(name.toLowerCase())) {
					leakList.add(leak.getResourceId());
				}
			}
			deviceInfo.setVulns(leakList);
		} else {
			deviceInfo = remoteTerminalService.getById(nodeId);
		}

		VulnTop vulnTop = new VulnTop();
		vulnTop.setNodeId(nodeId);
		vulnTop.setCvss(0.0);
		List<VulnTop.Vulns> vulnsList = new ArrayList<>();
		vulnTop.setVulnList(vulnsList);
		List<VulnTop.Defenses> defensesList = new ArrayList<>();
		vulnTop.setDefenseList(defensesList);

		if (deviceInfo != null && deviceInfo.getVulns() != null) {
			List<String> vulns = deviceInfo.getVulns();
			List<Vuln> vulnList = dynamicSchemeMapper.queryVulnTop(vulns);

			// 获取该节点下防御成功记录
			List<Defense> defenses = dynamicSchemeMapper.queryDefenseByNodeId(sceneId, elementId, 1);
			vulnTop.setCvss(0.0);
			List<Vuln> we = BeanUtils.deepCopyList(vulnList, Vuln.class);
			Double cvssAll = 0.0;
			Double cvssBll = 0.0;
			// 填入漏洞信息
			for (Vuln vuln : vulnList) {
				VulnTop.Vulns v = new VulnTop.Vulns();
				v.setVulnId(vuln.getResourceId());
				v.setVulnNmae(vuln.getName());
				v.setVulnCvss(vuln.getCvss());
				if (vuln.getType() == 4) {
					v.setVulnLevl("严重");
				} else if (vuln.getType() == 3) {
					v.setVulnLevl("高危");
				} else if (vuln.getType() == 2) {
					v.setVulnLevl("中危");
				} else if (vuln.getType() == 1) {
					v.setVulnLevl("低危");
				} else {
					v.setVulnLevl("正常");
				}
				v.setVulnNumber(vuln.getNumber());
				vulnsList.add(v);

				cvssAll = cvssAll + vuln.getCvss();
				for (Defense defens : defenses) {
					if (vuln.getResourceId().equals(defens.getVulnId())) {
						we.remove(vuln);
//						cvssBll = cvssBll + vuln.getCvss();
						break;
					}
				}
			}
			for (Vuln vuln : we) {
				cvssBll = cvssBll + vuln.getCvss();
			}

			List<String> collect = defenses.stream().map(Defense::getVulnId).collect(Collectors.toList());
			if (collect != null && collect.size() != 0) {
				List<Vuln> bugs = dynamicSchemeMapper.queryVulnTop(collect);
				Map<String, String> nameMap = bugs.stream().collect(Collectors.toMap(Vuln::getResourceId, Vuln::getName));
				// 填入防御信息
				for (Defense defens : defenses) {
					VulnTop.Defenses d = new VulnTop.Defenses();
					d.setDefenseId(defens.getResourceId());
					d.setDefenseVulnName(nameMap.get(defens.getVulnId()));
					d.setDescription(defens.getDeacription());
					defensesList.add(d);
				}
			}

			Double cvss = cvssBll/cvssAll*10;
			String str = String.format("%.2f", cvss);
			vulnTop.setCvss(Double.parseDouble(str));
			vulnTop.setVulnList(vulnsList);
			vulnTop.setDefenseList(defensesList);

			if (vulnsList.size() > 5) {
				vulnTop.setVulnList(vulnsList.subList(0, 5));
			}

			if (defensesList.size() > 5) {
				vulnTop.setDefenseList(defensesList.subList(0, 5));
			}
			return vulnTop;
		}
		return vulnTop;
	}

	public void pushDefenseToRoom(AttackMessageReq attackMessageReq, Boolean playback, Boolean isMultiterm) {
		WsDefenseDynamic wsDefenseDynamic = BeanUtils.copy(attackMessageReq, WsDefenseDynamic.class);
		DefenseType defenseType = DefenseType.getEnumByKeyWord(attackMessageReq.getDefenseType(), DefenseType.class);
		if (defenseType != null) {
			wsDefenseDynamic.setDefenseType(defenseType.getDefenseActionType().getCode());
		}
		wsDefenseDynamic.setDuration(attackMessageReq.getDurationTime());

		WsCvss wsCvss = new WsCvss();
		wsCvss.setNodeId(attackMessageReq.getDefenseTargetId());
		wsCvss.setFlag(1);
		wsCvss.setCvss(0.0);

		// 判断当前任务是否有工具和漏洞信息
		if (attackMessageReq.getLeak() != null && attackMessageReq.getTool() != null) {
			// 有信息查询工具信息
			List<ToolVuln> toolVuln = dynamicSchemeMapper.toolVulnById(attackMessageReq.getTool(), attackMessageReq.getLeak(), 1);
			if (toolVuln != null && toolVuln.size() != 0) {
				// 查到工具信息，防御成功
				wsDefenseDynamic.setIsSucceed(1);
				wsCvss.setFlag(2);
				// 判断是否为多方案推送，多方案推送记录防御成功记录
				if (isMultiterm) {

					// 0、构造记录
					Defense defense = new Defense();
					defense.setResourceId(IDGeneratorUtil.getID());
					defense.setSceneId(attackMessageReq.getSceneId());
					defense.setNodeId(attackMessageReq.getDefenseTargetId());
					defense.setVulnId(attackMessageReq.getLeak());
					defense.setDeacription(toolVuln.get(0).getDescribe());
					defense.setType(1);
					defense.setCreateTime(new Date());

					// 处理动作后的节点状态
					Integer nodeStatus = situationHandlerService.getNodeStatus(attackMessageReq.getSceneId(), attackMessageReq.getDefenseTargetId(), defense);
					wsCvss.setFlag(nodeStatus);

					// 记录防御成功数据
					dynamicSchemeMapper.addDefense(defense);
				}
			} else {
				wsDefenseDynamic.setIsSucceed(0);
			}
		} else {
			wsDefenseDynamic.setIsSucceed(0);
		}

		WsMessage<WsDefenseDynamic> wsMessage = new WsMessage<>(MessageType.DefenseDynamic.getDesc(), wsDefenseDynamic);
		// 推送防御动效
		pushDataToRoom(wsMessage, attackMessageReq, playback, null, true);

		List<Defense> defenses = dynamicSchemeMapper.queryDefenseByNodeId(attackMessageReq.getSceneId(), attackMessageReq.getDefenseTargetId(), 1);
		String imageUuid = remoteTerminalService.queryImageId(attackMessageReq.getDefenseTargetId());
		if (imageUuid != null) {
			VulnTop vulnTop = queryVulnTop(attackMessageReq.getSceneId(), imageUuid, attackMessageReq.getDefenseTargetId());
			wsCvss.setCvss(vulnTop.getCvss());
		}
		WsMessage<WsCvss> wsCvssWsMessage = new WsMessage<>(MessageType.Cvss.getDesc(), wsCvss);
		pushDataToRoom(wsCvssWsMessage, attackMessageReq, playback, null, false);

		//推送防御弹窗
		WsDefenseInfo wsDefenseInfo = new WsDefenseInfo();
		wsDefenseInfo.setTargetId(attackMessageReq.getDefenseTargetId());
		wsDefenseInfo.setDesc(attackMessageReq.getDescription());
		defenseType = DefenseType.getEnumByKeyWord(attackMessageReq.getDefenseType(), DefenseType.class);
		if (defenseType != null) {
			wsDefenseInfo.setDefenseName(defenseType.getMsg());
		}
		if (attackMessageReq.getTool() != null) {
			ToolVuln toolVuln = dynamicSchemeMapper.queryToolById(attackMessageReq.getTool());
			if (toolVuln != null) {
				wsDefenseInfo.setTool(toolVuln.getToolName());
			}
		}

		if (attackMessageReq.getLeak() != null) {
			Vuln vuln = dynamicSchemeMapper.queryById(attackMessageReq.getLeak());
			if (vuln != null) {
				wsDefenseInfo.setLeak(vuln.getName());
			}
		}
		defenseType = DefenseType.getEnumByKeyWord(attackMessageReq.getDefenseType(), DefenseType.class);
		if (defenseType != null) {
			wsDefenseInfo.setResult(defenseType.getSuccessDesc());
		}
		wsDefenseInfo.setDuration(attackMessageReq.getDurationTime());
		WsMessage<WsDefenseInfo> defenseInfoWsMessage = new WsMessage<>(MessageType.DefenseInfo.getDesc(), wsDefenseInfo);
		situationHandlerService.pushDataToRoom(defenseInfoWsMessage, attackMessageReq.getSceneId());
	}

	private <T> void pushDataToRoom(T data, AttackMessageReq attackMessageReq, Boolean playback, Boolean attackSuccess, Boolean addData) {
		situationHandlerService.pushDataToRoom(data, attackMessageReq.getSceneId());
		if (attackMessageReq.getStep() != null) {
			WsRealisticSceneDynamic wsRealisticSceneDynamic = new WsRealisticSceneDynamic();
			wsRealisticSceneDynamic.setStep(attackMessageReq.getStep());
			situationHandlerService.pushDataToRealisticScene(attackMessageReq.getSceneId(), wsRealisticSceneDynamic);
		}
		if(Boolean.FALSE.equals(playback) && Boolean.TRUE.equals(addData)){
			//如果不是回放则会增加一条动效历史
			attackMessageHistoryService.add(attackMessageReq, attackSuccess);
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
