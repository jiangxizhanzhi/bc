package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.cyberpeace.cr.evaluation.service.context.EvalInstanceSlimContext;

import java.util.List;

public interface EvalTestService extends IService<EvalTest> {

	String createByTemplate(EvalTestAddReq evalTestAddReq);

	ListRsp<EvalTestListRsp> testList(EvalTestListReq evalTestListReq);

	EvalTest getEvalTest(String testId);

	Boolean deleteTest(String testId);

	void pause(String testInstanceId);

	void unPause(String testInstanceId);

	String getDashboardPermalink(String testId);

	Boolean validateExistTask(String testId, EvalTestInstance.Status status);

	String startScene(String testId);

	EvalTestStatisticsRsp testStatistics();

	ListRsp<EvalPersonTestRsp> personTestRspListRsp(PageReqBase pageReqBase);

	JSONObject getDeviceStatistics(String testId);

	JSONArray getDeviceList(String testId, Integer category, String name);

	List<EvalInstanceSlimContext> listInstance(EvalTest evalTest, Boolean isFinished);
}
