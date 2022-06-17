package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;

import java.util.List;

public interface EvalVariableRefProbeDataService extends IService<EvalVariableRefProbeData> {
    List<EvalVariableRefProbeDataContext> variableProbeDataResult(String variableId);

    ListRsp<EvalVariableDataPageRsp> variableProbeData(Page page, String variableId);
}
