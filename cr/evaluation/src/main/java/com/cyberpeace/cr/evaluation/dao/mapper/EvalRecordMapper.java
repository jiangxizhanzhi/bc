package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EvalRecordMapper extends BaseMapper<EvalRecord> {
    Page<EvalRecordListRsp> pageList(@Param("page") Page page,
                                     @Param("task_id") String task_id,
                                     @Param("status") Integer status,
                                     @Param("pattern") String pattern);


    EvalRecord seletTemplateById(String roomId);
}
