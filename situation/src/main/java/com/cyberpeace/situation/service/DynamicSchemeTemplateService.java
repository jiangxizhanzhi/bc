package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AddDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.req.UpdateDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.res.OptionTemplateRes;
import com.cyberpeace.situation.model.SimpleDynamicSchemeTemplate;

import java.util.List;

public interface DynamicSchemeTemplateService {

	String addDynamicSchemeTemplate(AddDynamicSchemeTemplateReq addDynamicSchemeTemplateReq);

	void bashDeleteDynamicSchemeTemplate(List<String> resourceIds);

	void updateDynamicSchemeTemplate(UpdateDynamicSchemeTemplateReq updateDynamicSchemeTemplateReq);

	UpdateDynamicSchemeTemplateReq getDynamicSchemeTemplate(String resourceId);

	List<SimpleDynamicSchemeTemplate> getListDynamicSchemeTemplate();

	OptionTemplateRes getOptions();

}
