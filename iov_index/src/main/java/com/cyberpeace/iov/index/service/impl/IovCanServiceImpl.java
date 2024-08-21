package com.cyberpeace.iov.index.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.iov.index.service.IovCanService;
import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;
import com.cyberpeace.iov.util.IovHttpClientUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IovCanServiceImpl implements IovCanService {

	private static final String START_FUZZING_URL = "/local/test/fuzzing/start";

	private static final String STOP_FUZZING_URL = "/local/test/fuzzing/stop";

	private static final String START_REPLAY_URL = "/local/test/replay/start";

	private static final String STOP_REPLAY_URL = "/local/test/replay/stop";

	private static final String START_SEND_MSG_URL = "/local/test/msg_send/start";

	private static final String STOP_SEND_MSG_URL = "/local/test/msg_send/stop";

	@Override
	public void startFuzzing(FuzzingReq req) {
		IovHttpClientUtil.postJson(START_FUZZING_URL, req, JSONObject.class);
	}

	@Override
	public void stopFuzzing() {
		IovHttpClientUtil.postJson(STOP_FUZZING_URL, null, JSONObject.class);
	}

	@Override
	public void startSendMsg(MsgSendReq req) {
		IovHttpClientUtil.postJson(START_SEND_MSG_URL, req, JSONObject.class);
	}

	@Override
	public void stopSendMsg() {
		IovHttpClientUtil.postJson(STOP_SEND_MSG_URL, null, JSONObject.class);
	}

	@Override
	public void stopReplay() {
		IovHttpClientUtil.postJson(STOP_REPLAY_URL, null, JSONObject.class);
	}
}
