package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.template.TeamTemplateReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateOutput;

/**
 *
 */
public interface TeamTemplateService extends IService<TeamTemplate> {

    ListRsp<TeamTrainTemplateOutput> page(TeamTemplateReq listReq);
}
