
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.dao.mapper.SpaceRoundMechanismRuleMapper;
import com.cyberpeace.space.service.SpaceRoundMechanismRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceRoundMechanismRuleServiceImpl extends ServiceImpl<SpaceRoundMechanismRuleMapper, SpaceRoundMechanismRule> implements SpaceRoundMechanismRuleService {

	@Override
	public KnowledgeChart getRuleRoundMechanismChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_ROUND,
				SpaceKTypeEnum.RULE_RACE.getId(), 3);
		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}