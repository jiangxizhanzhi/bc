package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.mapper.CrConstantsConfigMapper;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrConstantsServiceImpl implements CrConstantsService {

	@Autowired
	private CrConstantsConfigMapper crConstantsConfigMapper;

	private volatile Map<Integer, List<CrConstantsConfig>> cache = Maps.newHashMap();

	@Override
	public List<CrConstantsConfig> getByType(Integer type) {
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
			List<CrConstantsConfig> configs = crConstantsConfigMapper.getAll();
			for (CrConstantsConfig config : configs) {
				Integer type = config.getType();
				if (!cache.containsKey(type)) {
					cache.put(type, Lists.newArrayList());
				}
				cache.get(type).add(config);
			}
		}
	}

	@Override
	public Boolean validateType(Integer type, Integer code) {
		if (cache.isEmpty()) {
			init();
		}
		if (cache.containsKey(type)) {

			List<Integer> codeList = cache.get(type).stream()
					.map(crConstantsConfig -> crConstantsConfig.getCode())
					.collect(Collectors.toList());

			if (codeList.contains(code)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String geCnNameByTypeAndCode(Integer type, Integer code) {
		if (type == null || code == null) {
			return "";
		}
		if (cache.isEmpty()) {
			init();
		}
		if (cache.containsKey(type)) {
			for (CrConstantsConfig crConstantsConfig : cache.get(type)) {
				if (code.equals(crConstantsConfig.getCode())) {
					return crConstantsConfig.getCnName();
				}
			}
		}
		return "";
	}

	@Override
	public List<CrConstantsConfig> getLables() {
		return crConstantsConfigMapper.selectLables();
	}
}
