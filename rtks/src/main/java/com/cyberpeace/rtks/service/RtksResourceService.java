package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.service.bo.resource.RtksResourceReq;

/**
 * RTKS资源Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface RtksResourceService {

	/**
	 * 负载
	 * 加载
	 *
	 * @param req 请求体
	 * @throws
	 * @see RtksResourceReq
	 */
	void load(RtksResourceReq req);
}
