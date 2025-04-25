
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.dao.mapper.SpaceTrafficDetectRuleMapper;
import com.cyberpeace.space.service.SpaceTrafficDetectRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceTrafficDetectRuleServiceImpl extends ServiceImpl<SpaceTrafficDetectRuleMapper, SpaceTrafficDetectRule> implements SpaceTrafficDetectRuleService

{

	@Override
	public KnowledgeChart getRuleNetworkChart() {

		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_NETWORK,
				SpaceKTypeEnum.RULE_THREAT_DEFENSE.getId(), 3);
		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}