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

/**
 * 评估变量裁判探针数据Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see EvalVariableRefProbeDataService
 * @since 1.0.0
 */
@Slf4j
@Service
public class EvalVariableRefProbeDataServiceImpl extends ServiceImpl<EvalVariableRefProbeDataMapper, EvalVariableRefProbeData> implements EvalVariableRefProbeDataService {

    /**
     * 可变探测数据结果
     *
     * @param variableId 变量id
     * @return {@code List<EvalVariableRefProbeDataContext> }
     * @throws
     * @see String
     * @see List<EvalVariableRefProbeDataContext>
     */
    @Override
    public List<EvalVariableRefProbeDataContext> variableProbeDataResult(String variableId) {
        return this.getBaseMapper().selectVariableProbeDataResult(variableId);
    }

    /**
     * 可变探针数据
     *
     * @param page       分页
     * @param variableId 变量id
     * @return {@code ListRsp<EvalVariableDataPageRsp> }
     * @throws
     * @see Page
     * @see String
     * @see ListRsp<EvalVariableDataPageRsp>
     */
    @Override
    public ListRsp<EvalVariableDataPageRsp> variableProbeData(Page page, String variableId) {
        return PageUtil.getListResult(this.getBaseMapper().selectVariableProbeData(page, variableId));
    }
}
