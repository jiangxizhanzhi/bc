package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.evaluation.controller.bo.EvalTaskBo;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTaskSubmit;
import com.cyberpeace.cr.evaluation.service.bo.EvalMissionEvaluationBo;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
@Mapper
@Repository
public interface CrEvalTaskSubmitMapper extends BaseMapper<CrEvalTaskSubmit> {

    void batchInsert(@Param("list") List<CrEvalTaskSubmit> lists);

    IPage<MissionEvaluationContext> listRbCrTaskSubmit(Page page,@Param("listQuery") EvalMissionEvaluationBo listQuery);

    List<EvalTaskBo> getByRoomId(@Param("template_id") String roomId);
}
