package com.cyberpeace.situation.service;

import java.util.List;

import com.cyberpeace.situation.controller.req.EventMsgReq;
import com.cyberpeace.situation.controller.req.SituationTopoReq;
import com.cyberpeace.situation.service.context.ConfigContext;
import com.cyberpeace.situation.service.context.TopoParaContext;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
public interface SituationTopoService {

	ConfigContext getSituationTopoConfig(String resourceId);

	ConfigContext createSituationTopoConfig(SituationTopoReq situationTopoReq);

	ConfigContext updateSituationTopoConfig(SituationTopoReq situationTopoReq);

	Integer deleteSituationTopoConfig(List<String> idList);

	List<ConfigContext> getAllConfig();

	TopoParaContext getCurrentTopo(EventMsgReq eventReq);
}
