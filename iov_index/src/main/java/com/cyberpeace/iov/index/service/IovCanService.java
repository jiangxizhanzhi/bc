package com.cyberpeace.iov.index.service;

import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;

public interface IovCanService {

	void startFuzzing(FuzzingReq req);

	void stopFuzzing();

	void startSendMsg(MsgSendReq req);

	void stopSendMsg();

	void stopReplay();

}
