package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评估变量裁判探针数据Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
public interface EvalVariableRefProbeDataMapper extends BaseMapper<EvalVariableRefProbeData> {
    /**
     * 查询变量探针数据结果
     *
     * @param variableId 变量id
     * @return {@code List<EvalVariableRefProbeDataContext> }
     */
    List<EvalVariableRefProbeDataContext> selectVariableProbeDataResult(@Param("variable_id") String variableId);

    /**
     * 查询变量探针数据
     *
     * @param page       分页
     * @param variableId 变量id
     * @return {@code Page<EvalVariableDataPageRsp> }
     */
    Page<EvalVariableDataPageRsp> selectVariableProbeData(@Param("page") Page page, @Param("variable_id") String variableId);
}
