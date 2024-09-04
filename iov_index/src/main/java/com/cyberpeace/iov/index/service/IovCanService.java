package com.cyberpeace.iov.index.service;

import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;
import org.springframework.web.multipart.MultipartFile;

public interface IovCanService {

	void startFuzzing(FuzzingReq req);

	void stopFuzzing();

	void startSendMsg(MsgSendReq req);

	void stopSendMsg();

	void stopReplay();

	void startReplay(MultipartFile blf);
}
