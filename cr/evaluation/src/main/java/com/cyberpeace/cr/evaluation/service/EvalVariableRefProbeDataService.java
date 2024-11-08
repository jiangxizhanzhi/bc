package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;

import java.util.List;

/**
 * 评估变量裁判探针数据Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalVariableRefProbeDataService extends IService<EvalVariableRefProbeData> {
    /**
     * 获取单次评估关联变量项探针数据
     *
     * @param variableId 变量项id
     * @return 单次评估关联变量项探针数据
     */
    List<EvalVariableRefProbeDataContext> variableProbeDataResult(String variableId);

    /**
     * 获取变量项关联探针原始数据
     *
     * @param page       分页信息
     * @param variableId 变量项id
     * @return 探针采集原始数据
     */
    ListRsp<EvalVariableDataPageRsp> variableProbeData(Page page, String variableId);
}
