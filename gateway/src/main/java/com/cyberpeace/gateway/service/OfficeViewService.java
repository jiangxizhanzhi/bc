package com.cyberpeace.gateway.service;

import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;

/**
 * 办公室视图Service
 *
 * @author SN
 * @date 2024/11/08
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
