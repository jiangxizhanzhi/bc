package com.cyberpeace.scene.service;

import com.cyberpeace.scene.service.bo.interactive.QQRes;
import com.cyberpeace.scene.service.bo.interactive.UserLinkRsp;

import java.util.List;

public interface InteractivePlatformService {

	void startScene(Integer scenario);

	void stopScene(Integer scenario);

	UserLinkRsp getUserLink(Integer scenario);

    void checkLink(Integer scenario);

    void sendMail(String content);

	void deleteMail();

	void sendDecryptMail();

	void snapShotAll();

	/**
	 * 获取qq号和头像
	 *
	 * @return {@code List<QQRes>}
	 */
	List<QQRes> getQQRes();

	/**
	 * 通过快照恢复场景
	 * @param scenario
	 */
	void recover(Integer scenario);

	/**
	 * 清理全部已启动的资源
	 */
	void clearAll();

}
