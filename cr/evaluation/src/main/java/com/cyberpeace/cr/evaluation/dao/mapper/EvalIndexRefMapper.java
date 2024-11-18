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
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
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
     * @throws
     * @see String
     * @see Integer
     * @see List<EvalRecordResultIndexRsp>
     */
    List<EvalRecordResultIndexRsp> selectResultIndexes(@Param("record_id")String recordId, @Param("level") Integer level);
}
