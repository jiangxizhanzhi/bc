package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvalVariableRefProbeDataMapper extends BaseMapper<EvalVariableRefProbeData> {
    List<EvalVariableRefProbeDataContext> selectVariableProbeDataResult(@Param("variable_id") String variableId);
}
