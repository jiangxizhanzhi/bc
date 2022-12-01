package com.cyberpeace.gateway.service;

import com.cyberpeace.gateway.service.bo.OfficeFileInfoBo;

public interface OfficeViewService {

	/**
	 * 获取讲义文件信息
	 * @param lessonId
	 * @return
	 */
	OfficeFileInfoBo lessonHandoutInfo(String lessonId);

    OfficeFileInfoBo assessMaterialHandoutInfo(String materialId);

    OfficeFileInfoBo assessModelHandoutInfo(String modelId);
}
