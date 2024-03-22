package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.interactive.InteractiveSceneReq;
import com.cyberpeace.scene.service.bo.interactive.UserLinkRsp;

public interface InteractivePlatformService {

	void startScene(InteractiveSceneReq req);

	void stopScene(InteractiveSceneReq req);

	UserLinkRsp getUserLink(Integer scenario);

    void checkLink(Integer scenario);

    void sendMail(String content);

	void sendDecryptMail();
}
