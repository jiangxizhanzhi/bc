package com.cyberpeace.cr.evaluation.service;

import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationServerRsp;

import java.util.List;

public interface EvalSituationService {

	List<EvalSituationListRsp> getAllEvalSituation();

	List<EvalSituationServerRsp> getEvalServerByResourceId(String resourceId);

	void pushFlow(String resourceId);

}
