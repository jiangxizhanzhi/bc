package com.cyberpeace.cr.evaluation.dao.mapper.rb;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTaskSubmit;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
public interface CrEvalTaskSubmitMapper extends BaseMapper<CrEvalTaskSubmit> {

    IPage<MissionEvaluationContext> listRbCrTaskSubmit(@Param("page") Page page,
                                                       @Param("listQuery") MissionEvaluationQuery listQuery);
}
