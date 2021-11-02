package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.template.TeamTemplateReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateAddReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateRsp;

/**
 *
 */
public interface TeamTemplateService extends IService<TeamTemplate> {

	ListRsp<TeamTrainTemplateRsp> page(TeamTemplateReq listReq);

	TeamTemplate create(TeamTrainTemplateAddReq input);

	TeamTemplate updateTeamTemplate(TeamTrainTemplateModifyReq input,
			BusinessTemplate businessTemplate);

	TeamTrainTemplateRsp getTeamTemplate(String resource_id);
}
