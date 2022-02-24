package com.cyberpeace.integratation.service.impl;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.integratation.service.RemoteSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {

	private String ATTACK_COUNT;

	@Autowired
	private MpEnvService mpEnvService;

	@PostConstruct
	public void init() {
		ATTACK_COUNT = mpEnvService.getMpHost() + ConfigUtil
				.getContextProperty("mp.situation.attack.count");
	}
}
