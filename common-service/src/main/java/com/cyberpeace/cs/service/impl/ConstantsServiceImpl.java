package com.cyberpeace.cs.service.impl;

import com.cyberpeace.cs.dao.entity.ConstantsConfig;
import com.cyberpeace.cs.dao.mapper.ConstantsConfigMapper;
import com.cyberpeace.cs.service.ConstantsService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConstantsServiceImpl implements ConstantsService {

	@Autowired
	private ConstantsConfigMapper constantsConfigMapper;

	private volatile Map<Integer, List<ConstantsConfig>> cache = Maps.newHashMap();

	@Override
	public List<ConstantsConfig> getByType(Integer type) {
		if (cache.isEmpty()) {
			init();
		}
		if (cache.containsKey(type)) {
			return cache.get(type);
		} else {
			return Lists.newArrayList();
		}
	}

	private synchronized void init() {
		if (cache.isEmpty()) {
			List<ConstantsConfig> configs = constantsConfigMapper.getAll();
			for (ConstantsConfig config : configs) {
				Integer type = config.getType();
				if (!cache.containsKey(type)) {
					cache.put(type, Lists.newArrayList());
				}
				cache.get(type).add(config);
			}
		}
	}

}
