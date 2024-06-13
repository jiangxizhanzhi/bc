package com.cyberpeace.cr.redvsblue.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;

public interface RbSituationService {

	List<RbSituation> getAllRbSituation();

	List<RbSituationServer> getRbServerByResourceId(String resourceId);

	String getSceneId();

	Map<String, String> getNodeMap();

	String getMpSceneId();

	void reSetId();
}
