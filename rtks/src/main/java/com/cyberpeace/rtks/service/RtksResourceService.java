package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.service.bo.resource.RtksResourceReq;

public interface RtksResourceService {

	/**负载
	 * 加载
	 *
	 * @param req 请求体
	 */
	void load(RtksResourceReq req);
}
