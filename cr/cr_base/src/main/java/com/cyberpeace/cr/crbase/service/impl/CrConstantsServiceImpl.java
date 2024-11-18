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

/**
 * 靶场常数Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see CrConstantsService
 * @since 1.0.0
 */
@Service
@Slf4j
public class CrConstantsServiceImpl implements CrConstantsService {

	/**
	 * 靶场常数配置Mapper
	 *
	 * @see CrConstantsConfigMapper
	 */
	@Autowired
	private CrConstantsConfigMapper crConstantsConfigMapper;

	/**
	 * 缓存
	 *
	 * @see Map<Integer, List<CrConstantsConfig>>
	 */
	private volatile Map<Integer, List<CrConstantsConfig>> cache = Maps.newHashMap();

	/**
	 * 获取由类型
	 *
	 * @param type 类型
	 * @return {@code List<CrConstantsConfig> }
	 * @throws
	 * @see Integer
	 * @see List<CrConstantsConfig>
	 */
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

	/**
	 * 初始化
	 *
	 * @throws
	 */
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

	/**
	 * 验证类型
	 *
	 * @param type 类型
	 * @param code 代号
	 * @return {@code Boolean }
	 * @throws
	 * @see Integer
	 * @see Integer
	 * @see Boolean
	 */
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

	/**
	 * 通用 电气中文名称由类型和代号
	 *
	 * @param type 类型
	 * @param code 代号
	 * @return {@code String }
	 * @throws
	 * @see Integer
	 * @see Integer
	 * @see String
	 */
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
}
