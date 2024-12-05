package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationServerRsp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EvalSituationService {

	List<EvalSituationListRsp> getAllEvalSituation();

	List<EvalSituationServerRsp> getEvalServerByResourceId(String resourceId);

	void pushFlow(String resourceId);

	void pushLoadResult(String resourceId, MultipartFile file);

    void basicPublish(JSONObject msgObject);
}
