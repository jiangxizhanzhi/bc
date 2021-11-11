package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.model.MissionScheme;

public interface MissionSchemeService {

	String create(MissionScheme missionScheme);

	MissionScheme getById(String missionSchemeId);
}
