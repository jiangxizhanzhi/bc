package com.cyberpeace.oj.ojbase.service.impl;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.dao.entity.Indicator;
import com.cyberpeace.oj.ojbase.dao.entity.IndicatorMetrics;
import com.cyberpeace.oj.ojbase.dao.mapper.IndicatorMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.IndicatorMetricsMapper;
import com.cyberpeace.oj.ojbase.service.IndicatorService;
import com.cyberpeace.oj.ojbase.service.bo.IndicatorBo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class IndicatorServiceImpl implements IndicatorService {

	@Autowired
	private IndicatorMapper indicatorMapper;

	@Autowired
	private IndicatorMetricsMapper metricsMapper;

	@Override
	public List<IndicatorBo> getIndicators(Integer type) {

		List<Indicator> indicators = indicatorMapper.selectList(null);
		List<IndicatorMetrics> metricsList = metricsMapper.selectList(null);
		Map<Integer, List<Integer>> parentMap = Maps.newHashMap();
		for (IndicatorMetrics metrics : metricsList) {
			parentMap.putIfAbsent(metrics.getIndicatorId(), Lists.newArrayList());
			parentMap.get(metrics.getIndicatorId()).add(metrics.getParentId());
		}
		Map<Integer, IndicatorBo> indicatorMap = Maps.newLinkedHashMap();
		IndicatorBo first = new IndicatorBo();
		indicatorMap.put(0, first);
		for (Indicator indicator : indicators) {
			if (!Objects.equals(indicator.getIndicatorType(), type)) {
				continue;
			}
			IndicatorBo indicatorBo = new IndicatorBo();
			indicatorBo.setId(indicator.getId());
			indicatorBo.setName(indicator.getName());
			indicatorMap.put(indicator.getId(), indicatorBo);
		}
		indicatorMap.forEach((id, indicatorBo) -> {
			List<Integer> parentIds = parentMap.get(id);
			if (parentIds != null) {
				for (Integer parentId : parentIds) {
					if (Objects.equals(parentId, id)){
						continue;
					}
					IndicatorBo parent = indicatorMap.get(parentId);
					if (parent != null) {
						parent.getChildren().add(indicatorBo);
					}
				}
			}
		});
		// 防止无限循环
		for (IndicatorBo child : first.getChildren()) {
			for (IndicatorBo indicatorBo : child.getChildren()) {
				for (IndicatorBo bo : indicatorBo.getChildren()) {
					if (!bo.getChildren().isEmpty()){
						bo.setChildren(Lists.newArrayList());
					}
				}
			}
		}
		return first.getChildren();
	}

	@Override
	public List<Indicator> selectIndicators(String attackIdJson) {
		List<Integer> ids = JsonUtil.toJsonObjectArray(attackIdJson, Integer.class);
		if (ids.isEmpty()) {
			return Lists.newArrayList();
		}
		List<Indicator> indicators = indicatorMapper.selectBatchIds(ids);
		Map<Integer, Indicator> indicatorMap = StreamApiUtils.toMap(indicators, Indicator::getId,
				indicator -> indicator);
		List<Indicator> res = Lists.newArrayList();
		for (Integer id : ids) {
			if (indicatorMap.containsKey(id)) {
				res.add(indicatorMap.get(id));
			}
		}
		return res;
	}
}
