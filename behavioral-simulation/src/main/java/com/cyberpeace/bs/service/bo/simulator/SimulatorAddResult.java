package com.cyberpeace.bs.service.bo.simulator;

import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.dao.entity.UserInstance;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.google.common.collect.Maps;

import lombok.Data;

/**
 * 仪表新增结果
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class SimulatorAddResult {

	/**
	 * 仪表列表
	 */
	private List<UserSimulator> simulatorList = Lists.newArrayList();

	/**
	 * 用户实例列表
	 */
	private List<UserInstance> userInstanceList = Lists.newArrayList();

	/**
	 * 用户实例映射关系
	 */
	private Map<String, List<UserInstance>> userInstanceMap = Maps.newHashMap();
}
