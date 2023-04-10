package com.cyberpeace.cr.redvsblue.service;

import com.cyberpeace.cr.redvsblue.controller.rsp.*;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;

import java.util.List;
import java.util.Map;

public interface RbSituationService {

	List<RbSituation> getAllRbSituation();

	List<RbSituationServer> getRbServerByResourceId(String resourceId);

	String getSceneId();

	Map<String, String> getNodeMap();

	String getMpSceneId();

	RbSituationScoreRsp getTeamScore(String roomId, Integer roleType);

	List<RbSituationMissionStatusRsp> getMissionStatus(String roomId, Integer roleType);

	List<RbSituationAdEventRsp> getAdEvent(String roomId, Integer roleType);

	RbSituationAssetStatusRsp getAssetStatus(String roomId, Integer roleType);

	List<RbSituationActionRsp> getTeamAction(String roomId, Integer roleType);

	List<RbSituationActionRsp> getPlatformAction(String roomId, Integer roleType);
}
