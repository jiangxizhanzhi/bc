package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;

public interface MissionSchemeService {

	String create(MissionStageScheme missionStageScheme);

	MissionStageScheme getById(String missionSchemeId);
}
