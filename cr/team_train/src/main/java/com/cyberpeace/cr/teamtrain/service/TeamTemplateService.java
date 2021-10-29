package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.template.TeamTemplateReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateAddInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateOutput;

/**
 *
 */
public interface TeamTemplateService extends IService<TeamTemplate> {

    ListRsp<TeamTrainTemplateOutput> page(TeamTemplateReq listReq);

    TeamTemplate create(TeamTrainTemplateAddInput input);

    TeamTemplate updateTeamTemplate(TeamTrainTemplateModifyInput input, BusinessTemplate businessTemplate);

    TeamTrainTemplateOutput getTeamTemplate(String resource_id);
}
