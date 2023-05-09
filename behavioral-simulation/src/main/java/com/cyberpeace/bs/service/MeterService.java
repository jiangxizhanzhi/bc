package com.cyberpeace.bs.service;

import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;

/**
 * 仪表辅助测试Service
 *
 * @author SN
 * @date 2023/05/08
 */
public interface MeterService {

	/**
	 * 开始单个行为
	 *
	 * @param req 请求体
	 */
	void startSingleBehavior(SingleBehaviorReq req);

	/**
	 * 停止单个行为
	 *
	 * @param req 请求体
	 */
	void stopSingleBehavior(SingleBehaviorReq req);

}
