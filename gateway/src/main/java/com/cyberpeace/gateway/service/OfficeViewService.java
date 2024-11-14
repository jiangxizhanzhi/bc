package com.cyberpeace.gateway.service;

import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;

/**
 * Office视图Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface OfficeViewService {

	/**
	 * 获取讲义文件信息
	 *
	 * @param lessonId
	 * @return
	 */
	OfficeFileInfoBo lessonHandoutInfo(String lessonId);

}
