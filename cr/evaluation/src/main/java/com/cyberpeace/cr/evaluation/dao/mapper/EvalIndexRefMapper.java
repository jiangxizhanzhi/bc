package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评估指数裁判Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalIndexRefMapper extends BaseMapper<EvalIndexRef> {
    /**
     * 查询结果指标
     *
     * @param recordId 记录id
     * @param level    水平
     * @return {@code List<EvalRecordResultIndexRsp> }
     */
    List<EvalRecordResultIndexRsp> selectResultIndexes(@Param("record_id")String recordId, @Param("level") Integer level);
}
