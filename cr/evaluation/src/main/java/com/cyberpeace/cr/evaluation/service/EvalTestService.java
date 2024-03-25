package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;

public interface EvalTestService extends IService<EvalTest> {

	String createByTemplate(EvalTestAddReq evalTestAddReq);

	ListRsp<EvalTestListRsp> testList(EvalTestListReq evalTestListReq);

}
