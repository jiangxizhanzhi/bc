
package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.dao.mapper.SpaceAptFeatureMapper;
import com.cyberpeace.space.service.SpaceAptFeatureService;
import com.cyberpeace.space.service.context.index.KnowledgeChart;
import com.cyberpeace.space.service.context.index.KnowledgeChartCategory;
import com.cyberpeace.space.service.context.index.KnowledgeChartItem;
import com.cyberpeace.space.service.context.index.KnowledgeChartLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 空间容易特征Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceAptFeatureService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceAptFeatureServiceImpl extends ServiceImpl<SpaceAptFeatureMapper, SpaceAptFeature> implements SpaceAptFeatureService

{

    /**
     * 获取统治容易组织图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    @Override
	public KnowledgeChart getRuleAptOrgChart() {

		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_APT_ORG,
				SpaceKTypeEnum.RULE_BASE.getId(), 3);
        List<SpaceAptFeature> aptOrgList = this.list();
        baseItem.setValue(aptOrgList.size());
		chart.getData().add(baseItem);
		chart.getCategories().add(new KnowledgeChartCategory(SpaceKTypeEnum.RULE_APT_ORG));

        for (SpaceAptFeature spaceAptFeature : aptOrgList) {
            KnowledgeChartItem item = new KnowledgeChartItem(4);
            item.setId(spaceAptFeature.getResourceId());
            item.setName(spaceAptFeature.getName());
            item.setValue(spaceAptFeature.getFrequency());
            item.setCategory(baseItem.getId());
            item.setType(SpaceKTypeEnum.RULE_APT_ORG.getType());
            chart.getData().add(item);
            KnowledgeChartLink link = new KnowledgeChartLink(baseItem.getId(),
					spaceAptFeature.getResourceId());
            chart.getLinks().add(link);
        }
        return chart;
	}
}