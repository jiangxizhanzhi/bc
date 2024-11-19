package com.cyberpeace.gateway.service;

import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;

/**
 * Office视图Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface OfficeViewService {

	/**
	 * 获取讲义文件信息
	 *
	 * @param lessonId
	 * @return
	 * @throws
	 * @see String
	 * @see OfficeFileInfoBo
	 */
	OfficeFileInfoBo lessonHandoutInfo(String lessonId);

}
