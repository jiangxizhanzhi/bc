package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefProbeDataMapper;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefProbeDataService;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EvalVariableRefProbeDataServiceImpl extends ServiceImpl<EvalVariableRefProbeDataMapper, EvalVariableRefProbeData> implements EvalVariableRefProbeDataService {

    @Override
    public List<EvalVariableRefProbeDataContext> variableProbeDataResult(String variableId) {
        return this.getBaseMapper().selectVariableProbeDataResult(variableId);
    }

    @Override
    public ListRsp<EvalVariableDataPageRsp> variableProbeData(Page page, String variableId) {
        return PageUtil.getListResult(this.getBaseMapper().selectVariableProbeData(page, variableId));
    }
}
