package com.cyberpeace.iov.index.service;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;

public interface IovCanService {

	void startFuzzing(FuzzingReq req);

	void stopFuzzing();

	void startSendMsg(MsgSendReq req);

	JSONArray stopSendMsg();

	void stopReplay();

	void startReplay(MultipartFile blf);

	void startSniff();

	void stopSniff();

}
