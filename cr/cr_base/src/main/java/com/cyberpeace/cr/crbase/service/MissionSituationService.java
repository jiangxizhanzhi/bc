package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.MissionSituation;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.resource.IndustrySituationConfig;

public interface MissionSituationService extends IService<MissionSituation> {

    /**
     * 批量create
     *
     * @param situationConfig
     * @param missionTemplateList
     */
    void batchCreate(IndustrySituationConfig situationConfig,
			List<MissionTemplate> missionTemplateList);

	/**
	 * 获取批量byid列表
	 *
	 * @param situationIds situationid列表
	 * @return {@code List<MissionSituation>}
	 */
	List<MissionSituation> getBatchByIds(List<String> situationIds);
}
