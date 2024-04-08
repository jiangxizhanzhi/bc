package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalPersonTestRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestStatisticsRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;

public interface EvalTestService extends IService<EvalTest> {

	String createByTemplate(EvalTestAddReq evalTestAddReq);

	ListRsp<EvalTestListRsp> testList(EvalTestListReq evalTestListReq);

	EvalTest getEvalTest(String testId);

	Boolean validateExistTask(String testId, EvalTestInstance.Status status);

	String startScene(String testId);

	EvalTestStatisticsRsp testStatistics();

	ListRsp<EvalPersonTestRsp> personTestRspListRsp(PageReqBase pageReqBase);
}
