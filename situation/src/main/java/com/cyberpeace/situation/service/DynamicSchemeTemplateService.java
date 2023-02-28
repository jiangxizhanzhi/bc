package com.cyberpeace.situation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
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

	ListRsp<SimpleDynamicSchemeTemplate> getListDynamicSchemeTemplate(PageReqBase pageReqBase);

	OptionTemplateRes getOptions();

}
