package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评估变量裁判Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface EvalVariableRefMapper extends BaseMapper<EvalVariableRef> {
    /**
     * 通过评估获取id
     *
     * @param recordId
     * @return
     * @throws
     * @see String
     * @see List<String>
     */
    List<String>  getVariableIdByRecordId(@Param("recordId") String recordId);

    /**
     * 通过评估获取测评
     *
     * @param recordId
     * @return
     * @throws
     * @see String
     * @see List<EvalVariableRef>
     */
    List<EvalVariableRef> getByRecordId(@Param("recordId") String recordId);


    /**
     * 获取变量id由记录id列表
     *
     * @param evalIndicatorIds 评估指示器id列表
     * @return {@code List<EvalVariableRef> }
     * @throws
     * @see List<String>
     * @see List<EvalVariableRef>
     */
    List<EvalVariableRef> getVariableIdByRecordIds(List<String> evalIndicatorIds);
}
