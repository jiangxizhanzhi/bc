package com.cyberpeace.bs.service.bo.simulator;

import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.dao.entity.UserInstance;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class SimulatorAddResult {

	private List<UserSimulator> simulatorList = Lists.newArrayList();

	private List<UserInstance> userInstanceList = Lists.newArrayList();

	private Map<String, List<UserInstance>> userInstanceMap = Maps.newHashMap();
}
