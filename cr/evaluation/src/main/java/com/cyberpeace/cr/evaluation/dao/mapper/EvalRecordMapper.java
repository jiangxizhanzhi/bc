package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.service.context.TestRecordContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalRecordMapper extends BaseMapper<EvalRecord> {
    Page<EvalRecordListRsp> pageList(@Param("page") Page<EvalRecordListRsp> page,
                                     @Param("task_id") String taskId,
                                     @Param("status") Integer status,
                                     @Param("pattern") String pattern);

    List<TestRecordContext> getTestRecordByIds(@Param("ids") List<String> ids);
}
