package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountBar;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventStatusCount;
import com.cyberpeace.cr.crbase.controller.rsp.EvalRoleEventActivity;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTaskLog;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTrainTask;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrEvalTaskLogMapper extends BaseMapper<CrEvalTaskLog> {

    void batchInsert(@Param("list") List<CrEvalTaskLog> list);

    List<EvalEventCountBar> getTaskByRoomIdAndType(@Param("evalTemplateId") String evalTemplateId,@Param("status") Integer status);

    List<EvalRoleEventActivity> getReleaseIdsByRoomIdAndUsers(@Param("evalTemplateId") String evalTemplateId, @Param("activeTimeList")List<Integer> activeTimeList);

}
