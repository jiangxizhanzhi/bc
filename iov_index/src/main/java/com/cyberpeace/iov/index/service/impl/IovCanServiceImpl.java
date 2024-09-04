package com.cyberpeace.iov.index.service.impl;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.iov.index.service.IovCanService;
import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;
import com.cyberpeace.iov.util.IovHttpClientUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class IovCanServiceImpl implements IovCanService {

	private static  String START_FUZZING_URL;

	private static  String STOP_FUZZING_URL;

	private static  String START_REPLAY_URL;

	private static  String STOP_REPLAY_URL;

	private static String START_SEND_MSG_URL;

	private static String STOP_SEND_MSG_URL;

	private static String DOWNLOAD_PATH;

	// 每个车的通道不一样，后续根据车型来配置
	private static final String MSG_CHN = "1";


	@PostConstruct
	public void init() {
		String KA_HOST = SystemPropUtil.getProp("KA_HOST");
		// 控安测试地址 http://180.167.177.61:18080
		if (StringUtils.isBlank(KA_HOST) || !KA_HOST.startsWith("http")) {
			log.error("KA_HOST [{}] not starts with http!", KA_HOST);
			throw new BusinessException("KA_HOST not set");
		}
		START_FUZZING_URL = KA_HOST + ConfigUtil.getContextProperty("ka.fuzzing.start");
		STOP_FUZZING_URL = KA_HOST + ConfigUtil.getContextProperty("ka.fuzzing.stop");
		START_REPLAY_URL = KA_HOST + ConfigUtil.getContextProperty("ka.replay.start");
		STOP_REPLAY_URL = KA_HOST + ConfigUtil.getContextProperty("ka.replay.stop");
		START_SEND_MSG_URL = KA_HOST + ConfigUtil.getContextProperty("ka.msg.send.start");
		STOP_SEND_MSG_URL = KA_HOST + ConfigUtil.getContextProperty("ka.mgs.send.stop");
		DOWNLOAD_PATH = ConfigUtil.getContextProperty("cr.local.file.prefix") + "blf";


		log.info("ka host is =[{}]", KA_HOST);
	}

	@Override
	public void startFuzzing(FuzzingReq req) {
		req.setMsgChn(MSG_CHN);
		IovHttpClientUtil.postJson(START_FUZZING_URL, req, JSONObject.class);
	}

	@Override
	public void stopFuzzing() {
		String fileName = getDownloadPath();
		IovHttpClientUtil.rawPostFile(STOP_FUZZING_URL, fileName);
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
		IovHttpClientUtil.postJson(START_SEND_MSG_URL, req, JSONObject.class);
	}

	@Override
	public void stopSendMsg() {
		String fileName = getDownloadPath();
		IovHttpClientUtil.rawPostFile(STOP_SEND_MSG_URL, fileName);
	}

	@Override
	public void stopReplay() {
		String fileName = getDownloadPath();
		IovHttpClientUtil.rawPostFile(STOP_REPLAY_URL, fileName);
	}

	@Override
	public void startReplay(MultipartFile blFile) {
		final String originalFilename = blFile.getOriginalFilename();
		if (originalFilename == null || !originalFilename.endsWith(".blf")) {
			throw new BusinessException("文件格式错误，请上传.blf文件");
		}
		if (blFile.isEmpty() || blFile.getSize() > 1024 * 1024 * 200) {
			throw new BusinessException("文件大小不能超过200M");
		}

		IovHttpClientUtil.rawPostUploadFile(START_REPLAY_URL, blFile, JSONObject.class);
	}

}
