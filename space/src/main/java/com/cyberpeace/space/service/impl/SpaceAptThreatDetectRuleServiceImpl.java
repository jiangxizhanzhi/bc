
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.dao.mapper.SpaceAptThreatDetectRuleMapper;
import com.cyberpeace.space.service.SpaceAptThreatDetectRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceAptThreatDetectRuleServiceImpl extends ServiceImpl<SpaceAptThreatDetectRuleMapper, SpaceAptThreatDetectRule> implements SpaceAptThreatDetectRuleService

{

	@Override
	public KnowledgeChart getRuleAptThreatChart() {

		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_APT_DETECT,
				SpaceKTypeEnum.RULE_THREAT_DEFENSE.getId());
		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}