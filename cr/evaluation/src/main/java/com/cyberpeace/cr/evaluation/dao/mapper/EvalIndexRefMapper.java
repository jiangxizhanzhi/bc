package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalIndexRefMapper extends BaseMapper<EvalIndexRef> {
    List<EvalRecordResultIndexRsp> selectResultIndexes(@Param("record_id")String recordId, @Param("level") Integer level);
}
