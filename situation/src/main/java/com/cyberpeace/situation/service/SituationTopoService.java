package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.SituationTopoReq;
import com.cyberpeace.situation.dao.entity.SituationTopo;

import java.util.List;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
public interface SituationTopoService {

    SituationTopo getSituationTopoConfig(String resourceId);

    SituationTopo createSituationTopoConfig(SituationTopoReq situationTopoReq);

    SituationTopo updateSituationTopoConfig(SituationTopoReq situationTopoReq);

    Integer deleteSituationTopoConfig(List<String> idList);
}
