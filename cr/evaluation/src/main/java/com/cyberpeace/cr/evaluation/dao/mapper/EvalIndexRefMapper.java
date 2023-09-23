package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;

@Mapper
@Repository
public interface EvalIndexRefMapper extends BaseMapper<EvalIndexRef> {
    List<EvalRecordResultIndexRsp> selectResultIndexes(@Param("record_id")String recordId, @Param("level") Integer level);
}
