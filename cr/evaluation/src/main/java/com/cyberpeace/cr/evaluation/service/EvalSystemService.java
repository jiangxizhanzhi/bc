package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalSystemTestLoadReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSystemTestReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemAllRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import java.util.List;

/**
 *
 */
public interface EvalSystemService extends IService<EvalSystem> {

	/**批量删除评估体系
	 * @param ids
	 * @return
	 */
	Boolean batchDelete(List<String> ids);

	/**分页查询评估体系
	 * @param listQuery
	 * @return
	 */
	IPage<EvalSystem> page(EvalListQuery listQuery);

	EvalSystemRsp calWithThirdIndex(List<IndexContext> thirdIndexes, String systemId, Boolean isTest);

	EvalSystemRsp convertEvalSystemToEvalSystemRsp(EvalSystem evalSystem, Boolean isTest);

	EvalSystemRsp convertEvalSystemToEvalSystemRsp(EvalSystem evalSystem);

    EvalSystemAllRsp testWithStr(EvalSystemTestReq evalSystemTestReq);
}
