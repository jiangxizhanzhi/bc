package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

import java.io.IOException;
import java.util.List;

public interface ExportTrainMDService {

	void chooseTypeAndExportTrain (List<String> resourceIds, String path, Integer type) throws IOException;

	void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path);
}
