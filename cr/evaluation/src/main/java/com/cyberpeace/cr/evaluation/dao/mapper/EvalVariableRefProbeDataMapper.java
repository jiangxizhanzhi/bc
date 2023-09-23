package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;

@Mapper
@Repository
public interface EvalVariableRefProbeDataMapper extends BaseMapper<EvalVariableRefProbeData> {
    List<EvalVariableRefProbeDataContext> selectVariableProbeDataResult(@Param("variable_id") String variableId);

	Page<EvalVariableDataPageRsp> selectVariableProbeData(
			@Param("page") Page<EvalVariableDataPageRsp> page,
			@Param("variable_id") String variableId);
}
