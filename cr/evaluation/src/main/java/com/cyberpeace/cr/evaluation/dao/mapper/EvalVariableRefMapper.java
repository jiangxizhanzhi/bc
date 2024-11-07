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
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface EvalVariableRefMapper extends BaseMapper<EvalVariableRef> {
    /**
     * 通过评估获取id
     *
     * @param recordId
     * @return
     */
    List<String>  getVariableIdByRecordId(@Param("recordId") String recordId);

    /**
     * 通过评估获取测评
     *
     * @param recordId
     * @return
     */
    List<EvalVariableRef> getByRecordId(@Param("recordId") String recordId);


    /**
     * 获取变量id由记录id列表
     *
     * @param evalIndicatorIds 评估指示器id列表
     * @return {@code List<EvalVariableRef> }
     */
    List<EvalVariableRef> getVariableIdByRecordIds(List<String> evalIndicatorIds);
}
