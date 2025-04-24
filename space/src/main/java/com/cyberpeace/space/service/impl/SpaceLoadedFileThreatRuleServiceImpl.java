
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.dao.mapper.SpaceLoadedFileThreatRuleMapper;
import com.cyberpeace.space.service.SpaceLoadedFileThreatRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceLoadedFileThreatRuleServiceImpl extends ServiceImpl<SpaceLoadedFileThreatRuleMapper, SpaceLoadedFileThreatRule> implements SpaceLoadedFileThreatRuleService {

	@Override
	public KnowledgeChart geRuleVirusChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_VIRUS,
				SpaceKTypeEnum.RULE_THREAT_DEFENSE.getId());
		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}