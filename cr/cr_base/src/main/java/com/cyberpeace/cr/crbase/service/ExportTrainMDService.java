package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

import java.util.List;

public interface ExportTrainMDService {

	void chooseType (List<String> resourceIds, String path, Integer type);

	void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path);
}
