package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateRsp;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;

/**
 *
 */
public interface TeamTemplateService extends IService<TeamTemplate> {

	ListRsp<TeamTrainTemplateRsp> page(ListQuery listQuery);

	TeamTemplate create(TeamTrainTemplateContext teamTrainTemplateContext);

	TeamTemplate updateTeamTemplate(TeamTrainTemplateContext teamTrainTemplateContext,
			BusinessTemplate businessTemplate);

	TeamTrainTemplateRsp getTeamTemplate(String resourceId);
}
