package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.dao.entity.AssessFileData;

/**
 *
 */
public interface AssessFileDataService extends IService<AssessFileData> {

	void parseModelFile(String resourceId, Integer interfaceType, String attachmentUrl);

	String getDataBaseTableByRelatedId(String resourceId);

	void parseDataFile(String resourceId, Integer inputType, String inputData) throws Exception;

}
