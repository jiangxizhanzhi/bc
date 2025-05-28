
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.dao.mapper.SpaceViolationJudgeRuleMapper;
import com.cyberpeace.space.service.SpaceViolationJudgeRuleService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间违反法官统治Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceViolationJudgeRuleService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceViolationJudgeRuleServiceImpl extends ServiceImpl<SpaceViolationJudgeRuleMapper, SpaceViolationJudgeRule> implements SpaceViolationJudgeRuleService

{

	/**
	 * 获取统治违反法官图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	@Override
	public KnowledgeChart getRuleViolationJudgeChart() {

		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_VIOLATION,
				SpaceKTypeEnum.RULE_RACE.getId(), 3);

		int cnt = this.count();
		baseItem.setValue(cnt);
		chart.getData().add(baseItem);
		return chart;
	}
}