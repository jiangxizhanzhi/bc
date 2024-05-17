package com.cyberpeace.cs.service;

import com.cyberpeace.cs.service.context.rtc.LiveStatus;
import com.cyberpeace.cs.service.context.rtc.SrsCallbackReq;
import com.cyberpeace.cs.service.context.rtc.WebRTCReq;

public interface LiveService {
    /**
     * 获取推流链接
     *
     * @param req 请求体
     * @return {@code String}
     */
    String getPublishUrl(WebRTCReq req);

    /**
     * 获取拉流链接
     *
     * @param req 请求体
     * @return {@code String}
     */
	LiveStatus getLive(WebRTCReq req);


	/**
	 * 处理回调
	 * @param req 请求体
	 */
	void handleCallback(SrsCallbackReq req);
}
