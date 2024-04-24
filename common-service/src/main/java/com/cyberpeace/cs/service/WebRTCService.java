package com.cyberpeace.cs.service;

import com.cyberpeace.cs.service.context.rtc.WebRTCReq;

public interface WebRTCService {
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
    String getPlayUrl(WebRTCReq req);
}
