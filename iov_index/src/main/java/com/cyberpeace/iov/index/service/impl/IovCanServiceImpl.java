package com.cyberpeace.iov.index.service.impl;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.integratation.service.RemotePythonService;
import com.cyberpeace.iov.dao.mapper.LastRawPacketMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.iov.constant.IovConstants;
import com.cyberpeace.iov.index.service.IovCanService;
import com.cyberpeace.iov.index.service.IovSituationService;
import com.cyberpeace.iov.index.service.bo.can.CanMsg;
import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.IovSituationFlashEffect;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;
import com.cyberpeace.iov.util.IovHttpClientUtil;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IovCanServiceImpl implements IovCanService {

	private static  String START_FUZZING_URL;

	private static  String STOP_FUZZING_URL;

	private static  String START_REPLAY_URL;

	private static  String STOP_REPLAY_URL;

	private static String START_SEND_MSG_URL;

	private static String STOP_SEND_MSG_URL;

	private static String START_SNIFF_URL;

	private static String STOP_SNIFF_URL;

	private static String DOWNLOAD_PATH;

	// 每个车的通道不一样，后续根据车型来配置
	private static final String MSG_CHN = "1";

	@Autowired
	private IovSituationService iovSituationService;

	@Autowired
	private RemotePythonService remotePythonService;
	
	@Autowired
	private LastRawPacketMapper lastRawPacketMapper;


	@PostConstruct
	public void init() {
		String KA_HOST = SystemPropUtil.getProp("KA_HOST");
		String KA_CAN_PORT = SystemPropUtil.getProp("KA_CAN_PORT");
		// 控安测试地址 http://180.167.177.61:18080
		if (StringUtils.isBlank(KA_HOST) || !KA_HOST.startsWith("http")) {
			log.error("KA_HOST [{}] not starts with http!", KA_HOST);
			throw new BusinessException("KA_HOST not set");
		}
		if (StringUtils.isBlank(KA_CAN_PORT)) {
			log.error("KA_CAN_PORT [{}] not set!", KA_CAN_PORT);
			throw new BusinessException("KA_CAN_PORT not set");
		}
		String KA_CAN_HOST = KA_HOST + ":" + KA_CAN_PORT;
		START_FUZZING_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.fuzzing.start");
		STOP_FUZZING_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.fuzzing.stop");
		START_REPLAY_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.replay.start");
		STOP_REPLAY_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.replay.stop");
		START_SEND_MSG_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.msg.send.start");
		STOP_SEND_MSG_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.mgs.send.stop");
		START_SNIFF_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.sniff.start");
		STOP_SNIFF_URL = KA_CAN_HOST + ConfigUtil.getContextProperty("ka.sniff.stop");
		DOWNLOAD_PATH = ConfigUtil.getContextProperty("cr.local.file.prefix") + "blf";


		log.info("ka host is =[{}]", KA_CAN_HOST);
	}

	@Override
	public void startFuzzing(FuzzingReq req) {
		req.setMsgChn(MSG_CHN);
		IovHttpClientUtil.postJson(START_FUZZING_URL, req);
	}

	@Override
	public void stopFuzzing() {
		String fileName = getDownloadPath();
		IovHttpClientUtil.rawPostFile(STOP_FUZZING_URL, null, fileName);
	}

	private String getDownloadPath() {
		final UserInfo userContext = UserInfoContext.getUserContext();
		final String userId = userContext.getUserId();
		final String formatter = DateUtil.generateTimeNamedFileName();
        return DOWNLOAD_PATH + "/" + userId + "/" +formatter + ".blf";
	}

	@Override
	public void startSendMsg(MsgSendReq req) {
		req.getMsgs().forEach(msg -> msg.setMsgChn(MSG_CHN));
		IovHttpClientUtil.postJson(START_SEND_MSG_URL, req);
		this.sendFlashEffect(req);
	}

	@Override
	public JSONArray stopSendMsg() {
		String fileName = getDownloadPath();
		IovHttpClientUtil.rawPostFile(STOP_SEND_MSG_URL,null, fileName);
		JSONArray rsp = new JSONArray();
		try {
			rsp = remotePythonService.getBlfParseResult(fileName);
			if (!rsp.isEmpty()) {
				lastRawPacketMapper.updatePacket(IovConstants.PacketType.CAN, rsp.toJSONString());
			}
		} catch (Exception e) {
			log.error("get blf parse result error", e);
		}
		// 暂时用固定的数据
//		if (rsp.isEmpty()) {
//			return remotePythonService.getBlfParseResult(
//					"/cpms/blf/7e2b02b9ca194aa1a661398dc13c8e44/20241224_091902.blf");
//		}
		return rsp;
	}

	@Override
	public void stopReplay() {
		String fileName = getDownloadPath();
		IovHttpClientUtil.rawPostFile(STOP_REPLAY_URL,null, fileName);
	}

	@Override
	public void startReplay(MultipartFile blFile) {
		final String originalFilename = blFile.getOriginalFilename();
		if (originalFilename == null || !originalFilename.endsWith(".blf")) {
			throw new BusinessException(ErrorCode.INVALID_VEHICLE_FRAME_ID, "文件格式错误，请上传.blf文件");
		}
		if (blFile.isEmpty() || blFile.getSize() > 1024 * 1024 * 200) {
			throw new BusinessException(ErrorCode.INVALID_VEHICLE_FRAME_ID, "文件大小不能为空或者不能超过200M");
		}

		IovHttpClientUtil.rawPostUploadFile(START_REPLAY_URL, blFile);
	}

	@Override
	public void startSniff() {
		IovHttpClientUtil.postJson(START_SNIFF_URL, null);
	}

	@Override
	public void stopSniff() {

	}

	/**
	 * 发送闪动效果
	 *
	 * @param req 请求体
	 * @throws
	 * @see MsgSendReq
	 */
	private void sendFlashEffect(MsgSendReq req) {
		for (CanMsg msg : req.getMsgs()) {
			JSONObject object = new JSONObject();
			object.put(JsonKeyConstant.TYPE, IovConstants.IovWsType.FLASH);
			IovSituationFlashEffect effect = new IovSituationFlashEffect();
			if (IovConstants.CanData.ROTATE_SCREEN.equals(msg.getMsgData())
					|| IovConstants.CanData.SWITCH_APP.equals(msg.getMsgData())) {
				effect.setEcu(Lists.newArrayList(IovConstants.IovEcu.VIDEO_SCREEN));
			} else if (IovConstants.CanData.OPEN_DOOR.equals(msg.getMsgData())) {
				effect.setEcu(IovConstants.IovEcu.DOOR);
			} else {
				continue;
			}
			object.put(JsonKeyConstant.DATA, effect);
			iovSituationService.pushSituationWsMsg(object);
		}
	}

}
